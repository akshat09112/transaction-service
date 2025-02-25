package com.zolve.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Transaction() {
    }

    @Id
    private String transactionId;
    private String merchant;
    private String userId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private Double amount;
}
