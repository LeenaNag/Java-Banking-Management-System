
package com.banking.account;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(
            @PathVariable Long customerId,
            @RequestBody Account account) {
        return accountService.createAccount(account, customerId);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PutMapping("/{id}")
    public Account updateAccount(
            @PathVariable Long id,
            @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
