package com.ATM_Machine.Controller;

import com.ATM_Machine.Entity.Account;
import com.ATM_Machine.Repository.AccountRepository;
import com.ATM_Machine.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    // Method to show the form for creating a new account


    @GetMapping("/new")
    public String showAccountCreationForm(Model model){
        model.addAttribute("account", new Account());
        return "new_account";
    }

    // Method to handle the form submission and save the new account
    @PostMapping("/save")
    public String saveAccount(@ModelAttribute("account") Account account){
        accountService.saveAccount(account);
        return "redirect:/accounts/new";
    }

    // Method to display all accounts
    @GetMapping("/")
    public String getAllAccounts(Model model){
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "account_list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        Optional<Account> optionalAccount = Optional.ofNullable(accountService.getAccountById(id));
        if (optionalAccount.isPresent()) {
            accountService.deleteAccountById(id);
        }
        return "redirect:/accounts/";
    }

    @GetMapping("/edit/{id}")
    public String showEditAccountForm(@PathVariable Long id, Model model) {
        Account account = accountService.getAccountById(id);
        model.addAttribute("account",account);
        return "edit_account";
    }

    @PostMapping("/update/{id}")
    public String updateAccount(@PathVariable Long id, @ModelAttribute("account") Account account) {
        account.setId(id);
        accountService.saveAccount(account);
        return "redirect:/accounts";
    }
    //Search Account Number
    @GetMapping("/search")
    public String searchAccount(@RequestParam("accountNumber") String accountNumber, Model model){
        Optional<Account>optionalAccount = accountService.getAccountByAccountNumber1(accountNumber);
        if (optionalAccount.isPresent()){
            model.addAttribute("account",optionalAccount.get());
            return "account_details";
       }else {
           model.addAttribute("errorMessage", "Account not found");
            return "search_account";
       }
    }
}



