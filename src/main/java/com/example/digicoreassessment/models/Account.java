package com.example.digicoreassessment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties({"transactions", "accountPassword", "roles"})
public class Account {
    private String accountName;
    private String accountNumber;
    private Double balance;
    private String accountPassword;
    private List<Transaction> transactions;
    private Set<Role> roles;

    public void addTransaction(Transaction newTransaction) {
        if(transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(newTransaction);
    }
    public void addRole(Role role) {
        if(roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }


}
