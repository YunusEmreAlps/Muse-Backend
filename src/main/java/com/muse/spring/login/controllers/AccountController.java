package com.muse.spring.login.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.muse.spring.login.models.Account;
import com.muse.spring.login.models.User;
import com.muse.spring.login.payload.request.AccountRequest;
import com.muse.spring.login.repository.AccountRepository;
import com.muse.spring.login.repository.UserRepository;
import com.muse.spring.login.security.jwt.AuthEntryPointJwt;
import com.muse.spring.login.security.jwt.JwtUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
  @Autowired
  AccountRepository accountRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  JwtUtils jwtUtils;

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  // Create Account
  // Creates a new account for the authenticated user
  // POST /api/accounts
  @PostMapping
  public ResponseEntity<String> createAccount(@RequestBody AccountRequest account, HttpServletRequest request) {
    try {
      String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));

      // Get the authenticated user
      Optional<User> optionalUser = userRepository.findByUsername(username);

      if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        // Create a new account
        Account newAccount = new Account(user, account.getNumber(), account.getName(), account.getBalance());
        // Save the account
        accountRepository.save(newAccount);
        logger.info("Account created successfully for user: {}", user.getUsername());
        return ResponseEntity.ok("Account created successfully.");
      } else {
        logger.error("User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
      }
    } catch (Exception e) {
      logger.error("Error creating account", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create account", e);
    }
  }

  // Search Account
  // Search accounts for the authenticated user.
  // POST /api/accounts
  // Accounts should be filterable on number and name.
  @GetMapping
  public ResponseEntity<List<Account>> searchAccount(
      @RequestParam(required = false) String number,
      @RequestParam(required = false) String name) {
    try {
      List<Account> accounts;

      // Search based on the provided parameters
      if (number != null && name != null) {
        accounts = accountRepository.findByNameAndNumber(name, number);
      } else if (number != null) {
        accounts = accountRepository.findByNumber(number);
      } else if (name != null) {
        accounts = accountRepository.findByName(name);
      } else {
        return ResponseEntity.badRequest().body(Collections.emptyList());
      }

      if (accounts.isEmpty()) {
        logger.info("No accounts found for requested search criteria: number={}, name={}", number, name);
        return ResponseEntity.noContent().build(); // 204 No Content
      }

      return ResponseEntity.ok(accounts);
    } catch (DataAccessException e) {
      logger.error("Database error searching account with number={} and name={}", number, name, e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurred", e);
    } catch (Exception e) {
      logger.error("Unexpected error searching account with number={} and name={}", number, name, e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to search account", e);
    }
  }

  // Update Account
  // Update an account for the authenticated user
  // PUT /api/accounts/{id}
  @PutMapping("/{id}")
  public void updateAccount(@PathVariable("id") String id, @RequestBody AccountRequest account) {
    try {
      UUID accountId = UUID.fromString(id);

      // Get the account by UUID
      Optional<Account> accountOptional = accountRepository.findById(accountId);

      if (accountOptional.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
      } else {
        Account existingAccount = accountOptional.get();

        existingAccount.setNumber(account.getNumber());
        existingAccount.setName(account.getName());
        existingAccount.setBalance(account.getBalance());
        // Save the account
        accountRepository.save(existingAccount);
      }
    } catch (Exception e) {
      logger.error("Error updating account", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update account", e);
    }
  }

  // Delete Account
  // Deletes the selected account for the authenticated user.
  // DELETE /api/accounts/{id}
  @DeleteMapping(value = "/{id}")
  public void deleteAccount(@PathVariable("id") String id) {
    // Get the authenticated user
    try {
      UUID accountId = UUID.fromString(id);

      // Get the account by UUID
      Optional<Account> accountOptional = accountRepository.findById(accountId);

      if (accountOptional.isPresent()) {
        // Delete the account if it exists
        accountRepository.delete(accountOptional.get());
      } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
      }
    } catch (Exception e) {
      logger.error("Error deleting account", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete account", e);
    }
  }

  // View Account Details
  // Retrieves details of a specific account, including the balance. Access is
  // restricted to the account owner.
  // GET /api/accounts/{id}
  @GetMapping("/{id}")
  public Account viewAccountDetails(@PathVariable("id") UUID id) {
    try {
      Optional<Account> accountOptional = accountRepository.findById(id);

      if (accountOptional.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
      } else {
        return accountOptional.get();
      }
    } catch (Exception e) {
      logger.error("Error viewing account details", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to view account details", e);
    }
  }
}
