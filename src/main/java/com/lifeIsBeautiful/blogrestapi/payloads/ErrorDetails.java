package com.lifeIsBeautiful.blogrestapi.payloads;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Date;

@Schema(
        description = "ErrorDetails Model Information",
        name = "ErrorDetailsDto Model"
)
public class ErrorDetails {
    private Date localDateTime;
    private String message;
    private String path;

    public ErrorDetails(Date localDateTime, String message, String path) {
        this.localDateTime = localDateTime;
        this.message = message;
        this.path = path;
    }

    public Date getLocalDateTime() {
        return localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}