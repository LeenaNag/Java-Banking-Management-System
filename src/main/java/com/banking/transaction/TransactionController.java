package com.banking.transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction deposit(
            @PathVariable Long accountId,
            @RequestBody Map<String, BigDecimal> request) {

        return transactionService.deposit(
                accountId, request.get("amount"));
    }

    @PostMapping("/withdraw/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction withdraw(
            @PathVariable Long accountId,
            @RequestBody Map<String, BigDecimal> request) {

        return transactionService.withdraw(
                accountId, request.get("amount"));
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionsByAccount(
            @PathVariable Long accountId) {

        return transactionService.getTransactionsByAccount(accountId);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }
}