package com.example.banksystem.controller;

import com.example.banksystem.dto.AccountDTO;
import com.example.banksystem.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final AccountService accountService;

    public ReportController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts-summary")
    public ResponseEntity<List<AccountDTO>> accountsSummary() {
        List<AccountDTO> list = accountService.listAccounts();
        return ResponseEntity.ok(list);
    }
}
