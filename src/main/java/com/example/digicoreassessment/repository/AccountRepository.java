package com.example.digicoreassessment.repository;

import com.example.digicoreassessment.models.Account;
import com.example.digicoreassessment.models.Role;
import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class AccountRepository {
    private final Map<String, Account> accounts= new HashMap<>();
    private String generateAccountNumber(){
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build();
        return randomStringGenerator.generate(10);
    }
    public Account createAccount(CreateAccountRequest request) {
        Account account = new Account();
        account.setAccountName(request.getAccountName());
        account.setBalance(request.getInitialDeposit());
        account.setAccountNumber(generateAccountNumber());
        account.setAccountPassword(request.getAccountPassword());
        account.addRole(Role.CUSTOMER);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

        public Account getAccountByAccountNumber(String accountNumber) {
            log.info("getting account ---> {}", accounts.get(accountNumber));
            return accounts.get(accountNumber);
        }

        public void updateAccount (String accountNumber, Account account) {
            accounts.put(accountNumber, account);
        }
}
