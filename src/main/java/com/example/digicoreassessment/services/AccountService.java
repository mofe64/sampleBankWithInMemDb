package com.example.digicoreassessment.services;

import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;

public interface AccountService {
    void createAccount(CreateAccountRequest request);
}
