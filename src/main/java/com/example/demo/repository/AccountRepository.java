package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByNationalId(String nationalId);
    // Dummy repository methods for demonstration purposes
    Optional<Account> findByNationalIdAndThNameAndEngName(String nationalId, String thName, String engName);

    Optional<Account> findByEmailAndPassword(String email, String password);

    Optional<Account> findByAccountNumber(String accountNumber);
}
