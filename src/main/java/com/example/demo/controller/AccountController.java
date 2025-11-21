package com.example.demo.controller;

import com.example.demo.dto.ResultDto;
import com.example.demo.dto.TransferDto;
import com.example.demo.dto.VerifyDto;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/balance/{accountId}")
    public ResultDto getAccountBalance(@PathVariable Long accountId) {
        ResultDto resultDto = new ResultDto();
        Account account = accountService.getAccountBalance(accountId);
        if( account != null ) {
            resultDto.setStatus("SUCCESS");
            resultDto.setMessage("Account found");
            resultDto.setResult(account);
        } else {
            resultDto.setStatus("FAILED");
            resultDto.setMessage("Account not found");
        }
        return resultDto;
    }

    @PostMapping("/verify")
    public ResultDto verify(@RequestBody VerifyDto dto) {
        boolean result = accountService.verify(dto.getAccountNumber(),dto.getAmount());
        return ResultDto.builder().status(result ? "SUCCESS" : "FAILED").message(result ? "Verification successful" : "Verification failed").build();
    }

    @PostMapping("/transfer")
    public ResultDto transferFunds(@RequestBody TransferDto dto) {
        boolean result =  accountService.transferFunds(dto.getFromAccountNumber(), dto.getToAccountNumber(), dto.getAmount(),dto.getPin() );
        return ResultDto.builder().status(result ? "SUCCESS" : "FAILED").message(result ? "successful" : "failed").build();
    }
}
