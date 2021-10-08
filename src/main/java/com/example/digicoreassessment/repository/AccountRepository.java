package com.example.digicoreassessment.repository;

import com.example.digicoreassessment.models.Account;
import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository {
    private List<Account> accounts = new ArrayList<>();
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
        accounts.add(account);
        return account;
    }
}
