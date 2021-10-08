package com.example.digicoreassessment.controllers;

import com.example.digicoreassessment.payloads.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception, WebRequest request) {
        ApiResponse response = new ApiResponse();
        response.setResponseCode(401);
        response.setSuccess(false);
        response.setMessage("Incorrect account number or password");
        return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception, WebRequest request) {
        ApiResponse response = new ApiResponse();
        response.setResponseCode(401);
        response.setSuccess(false);
        response.setMessage("Incorrect account number or password");
        return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

}
