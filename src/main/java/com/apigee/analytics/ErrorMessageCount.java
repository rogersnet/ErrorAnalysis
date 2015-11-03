package com.apigee.analytics;

import java.util.Date;

public class ErrorMessageCount {
    private String timeInstance;
    private String isApigeeFault;
    private String edgeExecutionFaultCode;
    private String edgeExecutionPolicyName;
    private String edgeExecutionPolicyFlowName;
    private Double messageCount;

    public String getTimeInstance() {
        return timeInstance;
    }

    public void setTimeInstance(String timeInstance) {
        this.timeInstance = timeInstance;
    }

    public String getIsApigeeFault() {
        return isApigeeFault;
    }

    public void setIsApigeeFault(String isApigeeFault) {
        this.isApigeeFault = isApigeeFault;
    }

    public String getEdgeExecutionFaultCode() {
        return edgeExecutionFaultCode;
    }

    public void setEdgeExecutionFaultCode(String edgeExecutionFaultCode) {
        this.edgeExecutionFaultCode = edgeExecutionFaultCode;
    }

    public String getEdgeExecutionPolicyName() {
        return edgeExecutionPolicyName;
    }

    public void setEdgeExecutionPolicyName(String edgeExecutionPolicyName) {
        this.edgeExecutionPolicyName = edgeExecutionPolicyName;
    }

    public String getEdgeExecutionPolicyFlowName() {
        return edgeExecutionPolicyFlowName;
    }

    public void setEdgeExecutionPolicyFlowName(String edgeExecutionPolicyFlowName) {
        this.edgeExecutionPolicyFlowName = edgeExecutionPolicyFlowName;
    }

    public Double getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Double messageCount) {
        this.messageCount = messageCount;
    }
}
