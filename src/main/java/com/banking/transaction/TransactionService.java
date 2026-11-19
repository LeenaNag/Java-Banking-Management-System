package com.banking.transaction;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.account.Account;
import com.banking.account.AccountRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Transaction deposit(Long accountId, BigDecimal amount) {

        validateAmount(amount);

        Account account = getAccount(accountId);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Long accountId, BigDecimal amount) {

        validateAmount(amount);

        Account account = getAccount(accountId);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient account balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setAmount(amount);
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccount(Long accountId) {
        getAccount(accountId);
        return transactionRepository.findByAccount_AccountId(accountId);
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException(
                        "Transaction not found: " + transactionId));
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException(
                        "Account not found: " + accountId));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be greater than zero");
        }
    }
}