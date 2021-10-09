package com.example.digicoreassessment.payloads.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccountDetailsRequest {
    @NotBlank(message = "account password cannot be blank")
    private String accountPassword;
}
