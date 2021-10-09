package com.example.digicoreassessment.services;

import com.example.digicoreassessment.models.Transaction;
import com.example.digicoreassessment.payloads.requests.AccountDetailsRequest;
import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import com.example.digicoreassessment.payloads.requests.DepositRequest;
import com.example.digicoreassessment.payloads.requests.WithdrawRequest;

import java.util.List;

public interface AccountService {
    void createAccount(CreateAccountRequest request);
    String deposit(DepositRequest request);
    String withdraw(WithdrawRequest request);
    List<Transaction> getAccountStatement(AccountDetailsRequest request, String accountNumber);
}
