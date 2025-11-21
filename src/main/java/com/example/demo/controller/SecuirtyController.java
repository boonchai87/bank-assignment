package com.example.demo.controller;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.dto.ResultDto;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecuirtyController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResultDto register(@RequestBody @Valid RegisterDto dto) {
        Account account = Account.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nationalId(dto.getNationalId())
                .thName(dto.getThName())
                .engName(dto.getEngName())
                .pin(dto.getPin())
                .build();
        Account accountCreated = accountService.register(account);
        return ResultDto.builder().status(accountCreated!=null ? "SUCCESS" : "FAILED").result(accountCreated).build();
    }

    @PostMapping("/login")
    public ResultDto login(@RequestBody @Valid  LoginDto dto) {
        Account account = securityService.login(dto.getEmail(), dto.getPassword());
        return ResultDto.builder().status(account!=null ? "SUCCESS" : "FAILED").result(account).build();
    }
}
