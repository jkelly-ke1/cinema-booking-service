package com.cinemabooking.util;

public class EmployeeErrorResponse {

    private String responseMessage;
    private long timestamp;

    public EmployeeErrorResponse(String responseMessage, long timestamp) {
        this.responseMessage = responseMessage;
        this.timestamp = timestamp;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
