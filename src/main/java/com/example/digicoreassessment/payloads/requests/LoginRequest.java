package com.example.digicoreassessment.payloads.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String accountNumber;
    private String accountPassword;
}
