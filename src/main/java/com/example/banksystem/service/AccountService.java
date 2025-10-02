package com.example.banksystem.service;

import com.example.banksystem.dto.*;
import com.example.banksystem.exception.InsufficientFundsException;
import com.example.banksystem.exception.ResourceNotFoundException;
import com.example.banksystem.model.Account;
import com.example.banksystem.model.Transaction;
import com.example.banksystem.model.TransactionType;
import com.example.banksystem.repository.AccountRepository;
import com.example.banksystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public AccountDTO createAccount(CreateAccountRequest req) {
        Account a = new Account(req.getHolderName(), req.getInitialBalance());
        Account saved = accountRepository.save(a);
        return toDTO(saved);
    }

    public List<AccountDTO> listAccounts() {
        return accountRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AccountDTO getAccount(Long id) {
        Account a = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return toDTO(a);
    }

    @Transactional
    public TransactionDTO deposit(Long accountId, BigDecimal amount, String description) {
        Account acc = accountRepository.findByIdForUpdate(accountId).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        acc.setBalance(acc.getBalance().add(amount));
        accountRepository.save(acc);

        Transaction t = new Transaction(null, acc, amount, TransactionType.DEPOSIT, description);
        Transaction saved = transactionRepository.save(t);
        return toDTO(saved);
    }

    @Transactional
    public TransactionDTO withdraw(Long accountId, BigDecimal amount, String description) {
        Account acc = accountRepository.findByIdForUpdate(accountId).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        if (acc.getBalance().compareTo(amount) < 0) throw new InsufficientFundsException("Saldo insuficiente");
        acc.setBalance(acc.getBalance().subtract(amount));
        accountRepository.save(acc);

        Transaction t = new Transaction(acc, null, amount, TransactionType.WITHDRAWAL, description);
        Transaction saved = transactionRepository.save(t);
        return toDTO(saved);
    }

    @Transactional
    public TransactionDTO transfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String description) {
        Account accFrom = accountRepository.findByIdForUpdate(fromAccountId).orElseThrow(() -> new ResourceNotFoundException("From account not found"));
        Account accTo = accountRepository.findByIdForUpdate(toAccountId).orElseThrow(() -> new ResourceNotFoundException("To account not found"));

        if (accFrom.getBalance().compareTo(amount) < 0) throw new InsufficientFundsException("Saldo insuficiente para transferir");

        accFrom.setBalance(accFrom.getBalance().subtract(amount));
        accTo.setBalance(accTo.getBalance().add(amount));

        accountRepository.save(accFrom);
        accountRepository.save(accTo);

        Transaction t = new Transaction(accFrom, accTo, amount, TransactionType.TRANSFER, description);
        Transaction saved = transactionRepository.save(t);
        return toDTO(saved);
    }

    public List<TransactionDTO> getStatement(Long accountId, LocalDateTime start, LocalDateTime end) {
        List<com.example.banksystem.model.Transaction> list;
        if (start != null && end != null) {
            list = transactionRepository.findByAccountIdAndTimestampBetween(accountId, start, end);
        } else {
            list = transactionRepository.findByAccountId(accountId);
        }
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private AccountDTO toDTO(Account a) {
        return new AccountDTO(a.getId(), a.getHolderName(), a.getBalance(), a.getCreatedAt());
    }

    private TransactionDTO toDTO(Transaction t) {
        Long fromId = t.getAccountFrom() != null ? t.getAccountFrom().getId() : null;
        Long toId = t.getAccountTo() != null ? t.getAccountTo().getId() : null;
        return new TransactionDTO(t.getId(), fromId, toId, t.getAmount(), t.getType(), t.getTimestamp(), t.getDescription());
    }
}
