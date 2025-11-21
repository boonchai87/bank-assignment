package com.example.demo.repository;

import com.example.demo.model.Account;
import com.example.demo.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
    // Dummy repository methods for demonstration purposes
    List<Statement> findByAccountAndDateBetweenOrderByDateAsc(Account account, Date startDate, Date endDate);
}