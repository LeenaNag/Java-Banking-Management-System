
package com.banking.account;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.customer.Customer;
import com.banking.customer.CustomerRepository;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public Account createAccount(Account account, Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                    new RuntimeException("Customer not found: " + customerId));

        if (account.getAccountNumber() == null
                || account.getAccountNumber().isBlank()) {
            throw new IllegalArgumentException("Account number is required");
        }

        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new IllegalArgumentException("Account number already exists");
        }

        if (account.getAccountType() == null
                || account.getAccountType().isBlank()) {
            throw new IllegalArgumentException("Account type is required");
        }

        if (account.getBalance() == null
                || account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Balance must be zero or greater");
        }

        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Account not found: " + id));
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        Account account = getAccountById(id);

        if (updatedAccount.getAccountType() == null
                || updatedAccount.getAccountType().isBlank()) {
            throw new IllegalArgumentException("Account type is required");
        }

        account.setAccountType(updatedAccount.getAccountType());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }
}
