package com.example.digicoreassessment.payloads.requests;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String accountName;
    private String accountPassword;
    private Double initialDeposit;
}
