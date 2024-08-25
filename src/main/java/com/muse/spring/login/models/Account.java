package com.muse.spring.login.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "accounts")
public class Account extends Base {
  // One User can have multiple accounts (One-to-Many)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  // Unique account number
  @Column(name = "account_number", unique = true, nullable = false)
  private String number;

  // Unique account name
  @Column(name = "account_name", unique = true, nullable = false)
  private String name;

  @Column(name = "balance")
  @Min(value = 0, message = "Balance must be a non-negative value")
  private double balance;

  public Account() {
  }

  public Account(User user, String number, String name, double balance) {
    this.user = user;
    this.number = number;
    this.name = name;
    this.balance = balance;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }
}
