package com.example.digicoreassessment.payloads.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateAccountRequest {
    @NotBlank(message = "account name cannot be blank")
    @NotNull(message = "account name cannot be null")
    private String accountName;
    @NotBlank(message = "account password cannot be blank")
    @NotNull(message = "account password cannot be null")
    private String accountPassword;
    @Min(value = 500, message = "Initial deposit cannot be less than 500.00")
    private Double initialDeposit;


}
