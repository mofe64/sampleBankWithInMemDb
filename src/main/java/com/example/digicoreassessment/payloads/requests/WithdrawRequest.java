package com.example.digicoreassessment.payloads.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WithdrawRequest {
    @NotBlank(message = "Account Number cannot be empty")
    private String accountNumber;
    @NotBlank(message = "Account password cannot be empty")
    private String accountPassword;
    @NotNull(message = "withdraw amount cannot be null")
    @Min(value =1 ,message = "withdraw amount cannot be less than 1")
    private Double withdrawalAmount;
}
