package com.muse.spring.login.models;

import javax.persistence.*;

import com.muse.spring.login.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "from_account_id", nullable = false)
  private Account from;

  @ManyToOne
  @JoinColumn(name = "to_account_id", nullable = false)
  private Account to;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(name = "transaction_date", nullable = false)
  private LocalDateTime transactionDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionStatus status;

  // Default constructor
  public Transaction() {
  }

  // Parameterized constructor
  public Transaction(Account from, Account to, BigDecimal amount, LocalDateTime transactionDate,
      TransactionStatus status) {
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.transactionDate = transactionDate;
    this.status = status;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Account getFrom() {
    return from;
  }

  public void setFrom(Account from) {
    this.from = from;
  }

  public Account getTo() {
    return to;
  }

  public void setTo(Account to) {
    this.to = to;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDateTime transactionDate) {
    this.transactionDate = transactionDate;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }
}