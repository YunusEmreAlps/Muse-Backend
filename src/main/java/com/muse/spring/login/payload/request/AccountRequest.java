package com.muse.spring.login.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AccountRequest {

  @NotBlank(message = "Account number is mandatory")
  private String number;

  @NotBlank(message = "Account name is mandatory")
  private String name;

  @Min(value = 0, message = "Balance must be a non-negative value")
  private double balance = 0.0;

  // Default constructor
  public AccountRequest() {
  }

  // Parameterized constructor
  public AccountRequest(String number, String name, double balance) {
    this.number = number;
    this.name = name;
    this.balance = balance;
  }

  // Getters and Setters
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

  @Override
  public String toString() {
    return "AccountRequest{" +
        "number='" + number + '\'' +
        ", name='" + name + '\'' +
        ", balance=" + balance +
        '}';
  }
}
