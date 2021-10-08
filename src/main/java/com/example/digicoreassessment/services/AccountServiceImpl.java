package com.example.digicoreassessment.services;

import com.example.digicoreassessment.exceptions.AccountException;
import com.example.digicoreassessment.models.Account;
import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import com.example.digicoreassessment.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service(value = "accountService")
@Slf4j
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createAccount(CreateAccountRequest request) {
        String encryptedPassword = passwordEncoder.encode(request.getAccountPassword());
        request.setAccountPassword(encryptedPassword);
        Account newAccount = accountRepository.createAccount(request);
        log.info("new Account --> {}", newAccount);
    }

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(String.format("No Account found with this account number %s", accountNumber)));
        Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
        return new org.springframework.security.core.userdetails.User(account.getAccountName(), account.getAccountPassword(), authoritySet);
    }

}
