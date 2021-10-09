package com.example.digicoreassessment.controllers;

import com.example.digicoreassessment.models.Transaction;
import com.example.digicoreassessment.payloads.requests.*;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createNewAccount(@Valid @RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);
        ApiResponse response = new ApiResponse();
        response.setResponseCode(200);
        response.setSuccess(true);
        response.setMessage("Account created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
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
    @PostMapping("deposit")
    public ResponseEntity<?> deposit(@RequestBody @Valid DepositRequest request) {
        String successMessage = accountService.deposit(request);
        return new ResponseEntity<>(new ApiResponse(200, true, successMessage), HttpStatus.OK);
    }

    @PostMapping("withdraw")
    public ResponseEntity<?> withdraw(@RequestBody @Valid WithdrawRequest request) {
        String successMessage = accountService.withdraw(request);
        return new ResponseEntity<>(new ApiResponse(200, true, successMessage), HttpStatus.OK);
    }

    @GetMapping("account_statement/{accountNumber}")
    public ResponseEntity<?> getAccountStatement(@RequestBody @Valid AccountDetailsRequest request, @PathVariable String accountNumber) {
        List<Transaction> accountStatement = accountService.getAccountStatement(request,accountNumber);
        return new ResponseEntity<>(accountStatement, HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }));
        return errors;
    }
}
