package com.muse.spring.login.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muse.spring.login.enums.TransactionStatus;
import com.muse.spring.login.models.Account;
import com.muse.spring.login.models.Transaction;
import com.muse.spring.login.models.User;
import com.muse.spring.login.repository.AccountRepository;
import com.muse.spring.login.repository.TransactionRepository;
import com.muse.spring.login.repository.UserRepository;
import com.muse.spring.login.security.jwt.AuthEntryPointJwt;
import com.muse.spring.login.security.jwt.JwtUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
  @Autowired
  AccountRepository accountRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  JwtUtils jwtUtils;

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  // Initiate Money Transfer
  // POST /api/transactions/transfer
  // Transfers money from one account to another.
  // Transfers can occur simultaneously.
  // User can transfer money from their account to another account. The user must be authenticated. 
  @PostMapping("/transfer")
  public ResponseEntity<String> transfer(@RequestParam UUID fromAccountId, @RequestParam UUID toAccountId,
      @RequestParam Double amount, HttpServletRequest request) {
    try {
      String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));

      // Get the authenticated user
      Optional<User> optionalUser = userRepository.findByUsername(username);

      if (optionalUser.isPresent()) {
        User user = optionalUser.get();

        // Get the authenticated user's account
        Optional<Account> optionalFromAccount = accountRepository.findById(fromAccountId);
        Optional<Account> optionalToAccount = accountRepository.findById(toAccountId);

        if (optionalFromAccount.isPresent() && optionalToAccount.isPresent()) {
          Account fromAccount = optionalFromAccount.get();
          Account toAccount = optionalToAccount.get();

          // Check if the from account has enough balance
          if (fromAccount.getBalance() >= amount) {
            // Deduct the amount from the from account
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            accountRepository.save(fromAccount);

            // Add the amount to the to account
            toAccount.setBalance(toAccount.getBalance() + amount);
            accountRepository.save(toAccount);

            // Create a new transaction
            Transaction transaction = new Transaction(fromAccount, toAccount, BigDecimal.valueOf(amount),
                LocalDateTime.now(), TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);

            return ResponseEntity.ok("Transaction successful");
          } else {
            return ResponseEntity.badRequest().body("Insufficient balance");
          }
        } else {
          return ResponseEntity.badRequest().body("Account not found");
        }
      } else {
        return ResponseEntity.badRequest().body("User not found");
      }
    } catch (DataAccessException e) {
      logger.error("Error: " + e.getMessage());
      return ResponseEntity.badRequest().body("An error occurred");
    }
  }

  // View Transaction History
  // GET /api/transactions/account/{accountId}
  // Retrieves the transaction history for a specified account. Access is restricted to the account owner.
  @GetMapping("/account/{accountId}")
  public List<Transaction> getTransactions(@RequestParam UUID accountId, HttpServletRequest request) {
    String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));

    // Get the authenticated user
    Optional<User> optionalUser = userRepository.findByUsername(username);

    if (optionalUser.isPresent()) {
      User user = optionalUser.get();

      // Get the authenticated user's account
      Optional<Account> optionalAccount = accountRepository.findById(accountId);

      if (optionalAccount.isPresent()) {
        Account account = optionalAccount.get();

        // Check if the account belongs to the authenticated user
        if (account.getUser().getId().equals(user.getId())) {
          return transactionRepository.findByAccountId(accountId);
        }
      }
    }

    return null;
  }
  
}
