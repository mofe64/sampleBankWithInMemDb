package com.example.digicoreassessment.controllers;

import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import com.example.digicoreassessment.payloads.response.ApiResponse;
import com.example.digicoreassessment.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("create_account")
    public ResponseEntity<?> createNewAccount(@RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);
        ApiResponse response = new ApiResponse();
        response.setResponseCode(201);
        response.setSuccess(true);
        response.setMessage("Account created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
