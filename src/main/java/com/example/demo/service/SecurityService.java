package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @Autowired
    private AccountRepository accountRepository;

    public Account login(String email, String password) {
        return accountRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}
