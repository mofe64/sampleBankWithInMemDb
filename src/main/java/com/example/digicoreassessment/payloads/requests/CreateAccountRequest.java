package com.example.digicoreassessment.payloads.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateAccountRequest {
    @NotBlank(message = "account name cannot be blank")
    @NotNull(message = "account name cannot be null")
    private String accountName;
    private String accountPassword;
    private Double initialDeposit;


}
