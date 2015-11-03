package com.apigee.analytics;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import javax.ws.rs.core.UriBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RootController {

    private final String requestUri = "https://api.enterprise.apigee.com/v1/organizations/bugaboo/environments/prod/stats/ax_edge_is_apigee_fault,ax_edge_execution_fault_code,ax_execution_fault_policy_name,ax_execution_fault_flow_name?_optimized=js&accuracy=100&limit=14400&select=sum(message_count)&sort=DESC&sortby=sum(message_count)&timeRange=10%2F28%2F2015+03:00:00~10%2F29%2F2015+03:00:00&timeUnit=hour&tsAscending=true&tzo=330";

    @RequestMapping(value = "/stats", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ErrorMessageCount>> listMessage(){
        List<ErrorMessageCount> emcList = null;
        try {
            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            client.addFilter(new HTTPBasicAuthFilter("analytics+readonly@apigee.com","TYnekYuxBEShD4"));

            WebResource service = client.resource(UriBuilder.fromUri(requestUri).build());
            String response     = service.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);

            JsonParser parser   = JsonParserFactory.getJsonParser();

            HashMap<String,Object> map = (HashMap<String,Object>)parser.parseMap(response);
            emcList = new ArrayList<ErrorMessageCount>();

            HashMap<String,Object> responseData = (HashMap<String,Object>) map.get("Response");

            //get timestamp values
            List<Long> timeunit = (List<Long>)responseData.get("TimeUnit");

            List<HashMap<String,Object>> datapoints = (List<HashMap<String,Object>>) ((Map<String,Object>)responseData.get("stats")).get("data");

            for(HashMap<String,Object> datapoint: datapoints){
                List<String> values = (List<String>) ((Map<String,Object>) datapoint.get("identifier")).get("values");
                List<Double> metric = (List) ((HashMap) ((List)datapoint.get("metric")).get(0)).get("values");

                for(int i=0;i<metric.size();i++){
                    ErrorMessageCount emc = new ErrorMessageCount();
                    emc.setIsApigeeFault(values.get(0));
                    emc.setEdgeExecutionFaultCode(values.get(1));
                    emc.setEdgeExecutionPolicyName(values.get(2));
                    emc.setEdgeExecutionPolicyFlowName(values.get(3));

                    Date date = new Date(timeunit.get(i));
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String dateFormatted = formatter.format(date);

                    emc.setTimeInstance(dateFormatted);
                    emc.setMessageCount(metric.get(i));
                    emcList.add(emc);
                }
            }

        }catch (Exception ex){

        }
        return new ResponseEntity<List<ErrorMessageCount>>(emcList, HttpStatus.OK);
    }

}
