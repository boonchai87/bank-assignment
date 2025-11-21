package com.example.demo.service;

import com.example.demo.dto.CreateAccountDto;
import com.example.demo.model.Account;
import com.example.demo.model.Statement;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.StatementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private StatementService statementService;

    public Account register(Account account) {
        List<Account> accounts = accountRepository.findByNationalId(account.getNationalId());
        if (!accounts.isEmpty() && accounts.get(0) != null) {
            //return null; // nationalId already exists
            throw new RuntimeException("National ID already exists");
        } else {
            return accountRepository.save(account);
        }
    }

    public Account createAccount(String nationalId, String thName, String enName) {
        // check account is exist
        System.out.println(nationalId+","+thName+","+enName);
        Optional<Account> accountOptional = accountRepository.findByNationalIdAndThNameAndEngName(nationalId, thName, enName);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            SecureRandom secureRandom = new SecureRandom();
            // Generate a random integer between 0 (inclusive) and 1,000,000 (exclusive)
            // This ensures the number is within the range that can be represented by 6 digits (000000 - 999999)
            int randomInteger = secureRandom.nextInt(1000000);

            // Format the integer to a 6-digit string, padding with leading zeros if needed
            String generateNumber = String.format("%07d", randomInteger);
            account.setAccountNumber(generateNumber);
            return accountRepository.save(account);
        }
        throw new RuntimeException("Account not found");
    }

    public Account getAccountBalance(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        return accountOptional.orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // deposit
    public Account depositOrWidtdraw(boolean isDopsit,String accountNumber, Double amount,String terminalId) {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            String code,remark;
            String channel = "OTC";
            if(isDopsit){
                double currentBalance = account.getBalance() != null ? account.getBalance() : 0.0;
                account.setBalance(currentBalance + amount);
                accountRepository.save(account);
                // save statement account
                code = "A0";
                remark = "Deposit Terminal " +terminalId;
            }else{
                account.setBalance(account.getBalance() - amount);
                accountRepository.save(account);
                // save statement account
                code = "A1";
                remark = "Withdraw Terminal " +terminalId;
            }
            // save statement
            statementService.saveStatement(code, channel, false, account.getBalance(), remark, account);
            return account;
        }else{
            throw new RuntimeException("Account not found");
        }
    }

    public boolean verify(String accountNumber, Double amount) {
        // acccount is exist
        Optional<Account> toAccountOptional = accountRepository.findByAccountNumber(accountNumber);
        if (toAccountOptional.isEmpty()){
            throw new RuntimeException("to Account not found");
        }
        if( amount <=0 ){
            throw new RuntimeException("Amount must be positive");
        }
        return true;
    }

    public boolean transferFunds(String fromAccountNumber, String toAccountNumber, Double amount, String pin) {
        //check pin
        Optional<Account> checkfromAccountOptional = accountRepository.findByAccountNumber(fromAccountNumber);
        //System.out.println(fromAccountNumber+","+toAccountNumber+","+amount+","+pin);
        if (checkfromAccountOptional.isPresent()) {
            Account fromAccount = checkfromAccountOptional.get();
            if (!fromAccount.getPin().equals(pin)) {
                //return false; // Invalid PIN
                throw new RuntimeException("Invalid PIN");
            }else{
                // Dummy transfer logic for demonstration purposes
                if (amount <= 0) {
                    //return false;// amount must be positive
                    throw new RuntimeException("Amount must be positive");
                } else {
                   // Optional<Account> fromAccountOptional = accountRepository.findById(fromAccountId);
                    Optional<Account> toAccountOptional = accountRepository.findByAccountNumber(toAccountNumber);
                    if ( toAccountOptional.isPresent() ) {
                        //Account fromAccount = fromAccountOptional.get();
                        Account toAccount = toAccountOptional.get();
                        double currentFromAccountBalance = fromAccount.getBalance() != null ? fromAccount.getBalance() : 0.0;
                        if ( currentFromAccountBalance >= amount) {
                            fromAccount.setBalance(currentFromAccountBalance - amount);
                            double currentToAccountBalance = toAccount.getBalance() != null ? toAccount.getBalance() : 0.0;
                            toAccount.setBalance(currentToAccountBalance + amount);
                            accountRepository.save(fromAccount);
                            accountRepository.save(toAccount);
                            // save statement fromAccount
                            String code = "A1";
                            String channel = "ATS";
                            String remark = "Transfer to " + toAccount.getAccountNumber() + " " + toAccount.getEngName();
                            statementService.saveStatement(code, channel, false, fromAccount.getBalance(), remark, fromAccount);
                            // save statement toAccount
                            remark = "Receive from " + fromAccount.getAccountNumber() + " " + fromAccount.getEngName();
                            statementService.saveStatement(code, channel, true, toAccount.getBalance(), remark, toAccount);
                            return true;
                        } else {
                            throw new RuntimeException("Insufficient funds");
                        }
                    }
                }
            }
        }else {
            throw new RuntimeException("from Account Not found");
        }
        return false;
    }
}