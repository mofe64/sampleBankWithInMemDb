package com.example.digicoreassessment.payloads.requests;


import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DepositRequest {
    @NotBlank
    private String accountNumber;
    @NotNull
    @Min(value = 1,message = "deposit amount cannot be less that 1.00")
    @Max(value = 1000000, message = "deposit amount cannot be more than 1,000,000")
    private Double amount;
}
