package com.example.digicoreassessment.payloads.response;

import lombok.Data;

@Data
public class AuthToken {
    private boolean success;
    private String accessToken;
}
