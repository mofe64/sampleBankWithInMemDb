package com.example.digicoreassessment.payloads.response;

import com.example.digicoreassessment.models.Account;
import lombok.Data;

@Data
public class AccountInfoResponse {
    private int responseCode;
    private boolean success;
    private String message;
    private Account account;
}
