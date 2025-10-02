package com.example.banksystem.dto;

import java.math.BigDecimal;

public class CreateAccountRequest {
    private String holderName;
    private BigDecimal initialBalance;

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }
    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) { this.initialBalance = initialBalance; }
}
