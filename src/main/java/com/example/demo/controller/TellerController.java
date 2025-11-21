package com.example.demo.controller;

import com.example.demo.dto.CreateAccountDto;
import com.example.demo.dto.DepositDto;
import com.example.demo.dto.ResultDto;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teller")
public class TellerController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/createAccount")
    public ResultDto createAccount(@RequestBody CreateAccountDto dto) {
        Account account  = accountService.createAccount(dto.getNationalId(),dto.getThName(),dto.getEngName());
        return ResultDto.builder().status(account != null ? "SUCCESS" : "FAILED").result(account).build();

    }

    @PostMapping("/deposit")
    public ResultDto deposit(@RequestBody DepositDto dto) {
        Account account =  accountService.depositOrWidtdraw(true,dto.getAccountNumber(), dto.getAmount(),dto.getTerminalId());
        return ResultDto.builder().status(account != null ? "SUCCESS" : "FAILED").result(account).build();
    }

    @PostMapping("/widthdraw")
    public ResultDto widthdraw(@RequestBody DepositDto dto) {
        Account account =  accountService.depositOrWidtdraw(false,dto.getAccountNumber(), dto.getAmount(),dto.getTerminalId());
        return ResultDto.builder().status(account != null ? "SUCCESS" : "FAILED").result(account).build();
    }
}
