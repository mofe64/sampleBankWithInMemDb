package com.example.digicoreassessment.payloads.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "account Number is required")
    private String accountNumber;
    @NotBlank(message = "account Password is required")
    private String accountPassword;
}
