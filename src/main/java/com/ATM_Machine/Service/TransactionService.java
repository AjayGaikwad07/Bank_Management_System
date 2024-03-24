package com.ATM_Machine.Service;

import com.ATM_Machine.Entity.Account;
import com.ATM_Machine.Repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void withdraw(Account account, double amount){
        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

//    @Transactional
//    public void transfer(Account fromAccount, Account toAccount, double amount){
//        double fromAccountNewBalance = fromAccount.getBalance() - amount;
//        double toAccountNewBalance = fromAccount.getBalance() + amount;
//
//        fromAccount.setBalance(fromAccountNewBalance);
//        toAccount.setBalance(toAccountNewBalance);
//
//        accountRepository.save(fromAccount);
//        accountRepository.save(toAccount);
//    }

    public double checkBalance(Account account){
        return account.getBalance();
    }



}
