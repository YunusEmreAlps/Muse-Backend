package com.muse.spring.login.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muse.spring.login.models.Account;
import java.util.List;
import java.util.Optional;

import com.muse.spring.login.models.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  List<Account> findByUser(User user);

  Optional<Account> findById(UUID id);

  Account findById(String id);

  List<Account> findByNumber(String number);

  List<Account> findByName(String name);

  List<Account> findByNameAndNumber(String name, String number);

  Boolean existsByNumber(String number);

  Boolean existsByName(String name);

  Boolean existsByUserAndNumber(User user, String number);
}
