package com.example.demo.controller;

import com.example.demo.dto.RequestStatementDto;
import com.example.demo.dto.ResultDto;
import com.example.demo.model.Statement;
import com.example.demo.service.SecurityService;
import com.example.demo.service.StatementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/statement")
public class StatementController {
    @Autowired
    private StatementService statementService;

    @PostMapping("/getStatementByMonth")
    public ResultDto getAccountStatements(@RequestBody @Valid RequestStatementDto dto) {
        List<Statement> results =  statementService.getAccountStatements(dto.getAccountId(), dto.getPin(), dto.getMonth());
        return ResultDto.builder().status("SUCCESS").results(results).build();
    }
}
