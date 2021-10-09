package com.example.digicoreassessment.services;

import com.example.digicoreassessment.exceptions.AccountException;
import com.example.digicoreassessment.models.Account;
import com.example.digicoreassessment.models.Transaction;
import com.example.digicoreassessment.models.TransactionType;
import com.example.digicoreassessment.payloads.requests.AccountDetailsRequest;
import com.example.digicoreassessment.payloads.requests.CreateAccountRequest;
import com.example.digicoreassessment.payloads.requests.DepositRequest;
import com.example.digicoreassessment.payloads.requests.WithdrawRequest;
import com.example.digicoreassessment.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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
    public String deposit(DepositRequest request) {
        String accountNumber = request.getAccountNumber();
        double depositAmount = request.getAmount();
        if (depositAmount > 1_000_000.00 || depositAmount < 1.00) {
            throw new AccountException("Deposit amount must be between 1.00 and 1000000.00");
        }
        Account accountToFund = accountRepository.getAccountByAccountNumber(accountNumber);
        if(accountToFund == null) {
            throw  new AccountException(String.format("No account found with that account number %s", accountNumber));
        }
        log.info("account to fund --> {}", accountToFund);
        double oldAccountBalance = accountToFund.getBalance();
        double newAccountBalance = oldAccountBalance + depositAmount;
        accountToFund.setBalance(newAccountBalance);
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        String narration = "Deposit of " + depositAmount + " made on " + LocalDate.now();
        transaction.setNarration(narration);
        transaction.setAmount(depositAmount);
        transaction.setAccountBalance(newAccountBalance);
        accountToFund.addTransaction(transaction);
        accountRepository.updateAccount(accountNumber, accountToFund);
        return "Sum of " + depositAmount + " deposited successfully into account number " + accountNumber;
    }

    @Override
    public String withdraw(WithdrawRequest request) {
        String accountNumber = request.getAccountNumber();
        double withdrawAmount = request.getWithdrawalAmount();
        String password = request.getAccountPassword();
        Account accountToWithdrawFrom = accountRepository.getAccountByAccountNumber(accountNumber);
        if(accountToWithdrawFrom == null) {
            throw  new AccountException(String.format("No account found with that account number %s", accountNumber));
        }
        if (!passwordEncoder.matches(password, accountToWithdrawFrom.getAccountPassword())) {
            throw  new AccountException("Incorrect password for this account");
        }
        double oldAccountBalance = accountToWithdrawFrom.getBalance();
        double newAccountBalance = oldAccountBalance - withdrawAmount;
        if(newAccountBalance < 500) {
            throw  new AccountException("Cannot withdraw past minimum account balance of 500");
        }
        accountToWithdrawFrom.setBalance(newAccountBalance);
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        String narration = "Withdrawal of " + withdrawAmount + " made on " + LocalDate.now();
        transaction.setNarration(narration);
        transaction.setAmount(withdrawAmount);
        transaction.setAccountBalance(newAccountBalance);
        accountToWithdrawFrom.addTransaction(transaction);
        accountRepository.updateAccount(accountNumber, accountToWithdrawFrom);
        return "Sum of " + withdrawAmount + " withdrawn successfully from account number " + accountNumber;

    }

    @Override
    public List<Transaction> getAccountStatement(AccountDetailsRequest request, String accountNumber) {
        Account accountRequested = accountRepository.getAccountByAccountNumber(accountNumber);
        String accountPassword = request.getAccountPassword();
        if(accountRequested == null) {
            throw  new AccountException(String.format("No account found with that account number %s", accountNumber));
        }
        if (!passwordEncoder.matches(accountPassword, accountRequested.getAccountPassword())) {
            throw  new AccountException("Incorrect password for this account");
        }
        return accountRequested.getTransactions();
    }

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByAccountNumber(accountNumber);
        if(account == null) {
            throw new UsernameNotFoundException(String.format("No Account found with this account number %s", accountNumber));
        }
        return new org.springframework.security.core.userdetails.User(account.getAccountNumber(), account.getAccountPassword(), getAuthorities(account));
    }

    private Set<SimpleGrantedAuthority> getAuthorities(Account account) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        account.getRoles().forEach(
                role -> {
                    authorities.addAll(role.getGrantedAuthorities());
                }
        );
        return authorities;
    }

}
