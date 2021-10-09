package com.example.digicoreassessment.services;

import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import com.example.digicoreassessment.payloads.requests.DepositRequest;

public interface AccountService {
    void createAccount(CreateAccountRequest request);
    String deposit(DepositRequest request);
}
