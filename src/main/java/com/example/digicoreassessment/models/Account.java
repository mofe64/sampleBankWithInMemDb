package com.example.digicoreassessment.models;

import lombok.Data;

@Data
public class Account {
    private String accountName;
    private String accountNumber;
    private Double balance;
    private String accountPassword;
}
