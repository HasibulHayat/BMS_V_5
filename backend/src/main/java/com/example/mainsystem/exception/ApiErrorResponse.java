package com.example.mainsystem.exception;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private Map<String, String> errors;
    private String path;
    private String helpUrl;
    private String userMessage;
    private String cause;
    private String exception;
    private String method;
    private String requestId;
    private String errorCode;
}
