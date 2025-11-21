package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Statement;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.StatementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatementService {
    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Statement> getAccountStatements(Long accountId,String pin,int month) {

        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if( accountOptional.isEmpty() ) {
            throw new RuntimeException("Account not found");
        }else{
            // check pin
            if( !accountOptional.get().getPin().equals(pin) ) {
                throw new RuntimeException("Invalid PIN");
            }else {
                Account account = accountOptional.get();
                if( month >=1 && month <=12 ) {
                    // calculate start and end date
                    Date now = new Date();
                    @SuppressWarnings("deprecation")
                    Date startDate = new Date(now.getYear(), month - 1, 1);
                    @SuppressWarnings("deprecation")
                    Date endDate = new Date(now.getYear(), month, 1);
                    return statementRepository.findByAccountAndDateBetweenOrderByDateAsc(account, startDate, endDate);
                }else{
                    throw new RuntimeException("Invalid month");
                }
            }
        }
    }

    public void saveStatement(String code,String channel,boolean isDebit,Double balance,String remark,Account account) {
        Statement statement = Statement.builder()
                .date(new Date())
                .time(Instant.now())
                .code(code)
                .channel(channel)
                .isDebit(isDebit)
                .balance(balance)
                .remark(remark)
                .account(account)
                .build();
        statementRepository.save(statement);
    }
}
