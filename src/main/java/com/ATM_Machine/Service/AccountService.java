package com.ATM_Machine.Service;

import com.ATM_Machine.Entity.Account;
import com.ATM_Machine.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }
    public Account getAccountById(Long id){
        return accountRepository.findById(id).orElse(null);
    }

    public void deleteAccountById(Long id) {
        accountRepository.deleteById(id);
    }

    public Optional<Account> getAccountByAccountNumber1(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);


    }
}


