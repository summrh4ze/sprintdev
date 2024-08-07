package com.example.sprintdev.dto;

public class ApprovalVoteRequest {
    private String message;
    private String size;

    public ApprovalVoteRequest() {};
    public ApprovalVoteRequest(String message, String size) {
        this.message = message;
        this.size = size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
