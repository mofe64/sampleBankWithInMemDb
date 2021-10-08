package com.example.digicoreassessment.controllers;

import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import com.example.digicoreassessment.payloads.requests.LoginRequest;
import com.example.digicoreassessment.payloads.response.ApiResponse;
import com.example.digicoreassessment.payloads.response.AuthToken;
import com.example.digicoreassessment.services.AccountService;
import com.example.digicoreassessment.services.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TokenProvider tokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("create_account")
    public ResponseEntity<?> createNewAccount(@RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);
        ApiResponse response = new ApiResponse();
        response.setResponseCode(201);
        response.setSuccess(true);
        response.setMessage("Account created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getAccountNumber(),
                        request.getAccountPassword()
                )
        );  
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenUtil.generateJWTToken(authentication);
        AuthToken response = new AuthToken();
        response.setAccessToken(token);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
