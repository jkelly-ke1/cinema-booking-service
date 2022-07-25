package com.cinemabooking.util;

public class UserErrorResponse {

    private String responseMessage;
    private long timestamp;

    public UserErrorResponse(String responseMessage, long timestamp) {
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
