package com.example.digicoreassessment.payloads.response;

import lombok.Data;

@Data
public class ApiResponse {
    private int responseCode;
    private boolean success;
    private String message;
}
