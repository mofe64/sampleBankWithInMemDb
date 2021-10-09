package com.example.digicoreassessment.services;

import com.example.digicoreassessment.models.Account;
import com.example.digicoreassessment.models.Transaction;
import com.example.digicoreassessment.models.TransactionType;
import com.example.digicoreassessment.payloads.requests.AccountDetailsRequest;
import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import com.example.digicoreassessment.payloads.requests.DepositRequest;
import com.example.digicoreassessment.payloads.requests.WithdrawRequest;
import com.example.digicoreassessment.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository repository;

    Account demoAccount;
    @BeforeEach
    void setUp() {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountName("demo");
        request.setInitialDeposit(10000.00);
        request.setAccountPassword("test");
        accountService.createAccount(request);
        demoAccount = repository.getAccountByAccountName("demo");
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
        DepositRequest request = new DepositRequest();
        request.setAccountNumber(demoAccount.getAccountNumber());
        request.setAmount(1000.00);
        accountService.deposit(request);
        Account updatedAccount = repository.getAccountByAccountName("demo");
        assertEquals(11000.00, updatedAccount.getBalance());
    }

    @Test
    void withdraw() {
        WithdrawRequest request = new WithdrawRequest();
        request.setAccountNumber(demoAccount.getAccountNumber());
        request.setWithdrawalAmount(1000.00);
        request.setAccountPassword("test");
        accountService.withdraw(request);
        Account updatedAccount = repository.getAccountByAccountName("demo");
        assertEquals(9000.00, updatedAccount.getBalance());
    }

    @Test
    void getAccountStatement() {
        DepositRequest request = new DepositRequest();
        request.setAccountNumber(demoAccount.getAccountNumber());
        request.setAmount(1000.00);
        accountService.deposit(request);
        WithdrawRequest request2 = new WithdrawRequest();
        request2.setAccountNumber(demoAccount.getAccountNumber());
        request2.setWithdrawalAmount(1000.00);
        request2.setAccountPassword("test");
        accountService.withdraw(request2);
        AccountDetailsRequest detailsRequest = new AccountDetailsRequest();
        detailsRequest.setAccountPassword("test");
        List<Transaction> statement = accountService.getAccountStatement(detailsRequest,demoAccount.getAccountNumber());
        assertNotNull(statement);
        assertEquals(2, statement.size());
        assertEquals(TransactionType.DEPOSIT,statement.get(0).getTransactionType());
    }

    @Test
    void getAccountInfo() {
        DepositRequest request = new DepositRequest();
        request.setAccountNumber(demoAccount.getAccountNumber());
        request.setAmount(1000.00);
        accountService.deposit(request);
        AccountDetailsRequest detailsRequest = new AccountDetailsRequest();
        detailsRequest.setAccountPassword("test");
        Account account = accountService.getAccountInfo(detailsRequest, demoAccount.getAccountNumber());
        assertNotNull(account);
        assertEquals("demo", account.getAccountName());
        assertEquals(11000.00, account.getBalance());

    }


}