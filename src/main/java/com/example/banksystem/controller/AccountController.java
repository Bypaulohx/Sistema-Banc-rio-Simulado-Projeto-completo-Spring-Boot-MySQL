package com.example.banksystem.controller;

import com.example.banksystem.dto.*;
import com.example.banksystem.service.AccountService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountRequest req) {
        AccountDTO dto = accountService.createAccount(req);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> listAccounts() {
        return ResponseEntity.ok(accountService.listAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<TransactionDTO> deposit(@PathVariable Long id, @RequestBody TransactionRequest req) {
        TransactionDTO t = accountService.deposit(id, req.getAmount(), req.getDescription());
        return ResponseEntity.ok(t);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(@PathVariable Long id, @RequestBody TransactionRequest req) {
        TransactionDTO t = accountService.withdraw(id, req.getAmount(), req.getDescription());
        return ResponseEntity.ok(t);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transfer(@RequestBody TransferRequest req) {
        TransactionDTO t = accountService.transfer(req.getFromAccountId(), req.getToAccountId(), req.getAmount(), req.getDescription());
        return ResponseEntity.ok(t);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDTO>> getStatement(@PathVariable Long id,
                                                              @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                              @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(accountService.getStatement(id, start, end));
    }
}
