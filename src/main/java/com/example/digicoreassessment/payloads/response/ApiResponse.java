package com.example.digicoreassessment.payloads.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse {
    private int responseCode;
    private boolean success;
    private String message;

    public ApiResponse() {
    }

    public ApiResponse(int responseCode, boolean success, String message) {
        this.responseCode = responseCode;
        this.success = success;
        this.message = message;
    }
}
