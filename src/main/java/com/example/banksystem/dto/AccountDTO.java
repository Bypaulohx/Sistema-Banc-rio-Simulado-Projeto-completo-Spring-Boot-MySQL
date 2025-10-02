package com.example.banksystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDTO {
    private Long id;
    private String holderName;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    public AccountDTO() {}

    public AccountDTO(Long id, String holderName, BigDecimal balance, LocalDateTime createdAt) {
        this.id = id;
        this.holderName = holderName;
        this.balance = balance;
        this.createdAt = createdAt;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
