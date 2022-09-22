package com.shreyas.blog.payload;

import java.util.Date;

public class ErrorDetail {
    private Date timestamp;
    private String details;
    private String message;

    public ErrorDetail(Date timestamp, String details, String message) {
        this.timestamp = timestamp;
        this.details = details;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    public String getMessage() {
        return message;
    }
}
