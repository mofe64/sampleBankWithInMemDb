package com.example.digicoreassessment.services;

import com.example.digicoreassessment.models.Account;
import com.example.digicoreassessment.payloads.requests.AccountDetailsRequest;
import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import com.example.digicoreassessment.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createAccount() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountName("Mofe");
        request.setInitialDeposit(501.00);
        request.setAccountPassword("test");
        accountService.createAccount(request);
        Account createdAccount = repository.getAccountByAccountName("Mofe");
        assertNotNull(createdAccount);
        assertEquals(501.00, createdAccount.getBalance());
        assertNotEquals("test", createdAccount.getAccountPassword());

    }

    @Test
    void deposit() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void getAccountStatement() {
    }

    @Test
    void getAccountInfo() {
    }

    @Test
    void loadUserByUsername() {
    }
}