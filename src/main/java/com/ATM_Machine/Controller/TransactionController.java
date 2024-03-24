package com.ATM_Machine.Controller;

import com.ATM_Machine.Entity.Account;
import com.ATM_Machine.Service.AccountService;
import com.ATM_Machine.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/Contact")
    public String Contact_Form(){
        return "Contact_form";
    }

    @GetMapping("/Welcome_Bank")
    public String Welcome_bank(){
        return "authentication/Bank_Welcome";
    }

    @GetMapping("/search")
    public String showSearchForm(Model model){
        model.addAttribute("accounts_details", new Account());
        return "accountInput";
    }

    @PostMapping("/search")
    public String searchAccount(@ModelAttribute("account") Account account,Model model){
        Optional<Account> optionalAccount = accountService.getAccountByAccountNumber1(account.getAccountNumber());
        if (optionalAccount.isPresent()){
            Account foundAccount = optionalAccount.get();
            model.addAttribute("firstName",foundAccount.getFirstName());
            model.addAttribute("lastName",foundAccount.getLastName());
            model.addAttribute("mobileNumber",foundAccount.getMobileNumber());
            model.addAttribute("accountNumber",foundAccount.getAccountNumber());
            model.addAttribute("balance",foundAccount.getBalance());

        }else {
            model.addAttribute("errorMessage", "Account not Found");
        }
        return "accountInput";
    }

    @GetMapping("/withdrawal")
    public String showWithdrawalForm(Model model){
        model.addAttribute("account",new Account());
        return "withdrawal_form";
    }

    @PostMapping("/withdraw")
    public  String withdraw(@ModelAttribute("account") Account account,
                            @RequestParam("amount") double amount,
                            Model model) {
        try {
            Optional<Account> optionalAccount = accountService.getAccountByAccountNumber1(account.getAccountNumber());
            if (optionalAccount.isPresent()) {
                Account foundAccount = optionalAccount.get();
                if (foundAccount.getBalance() >= amount) {
                    if (isValidWithdrawal(amount)) {
                        transactionService.withdraw(foundAccount, amount);
                        model.addAttribute("message", "Withdrawal successful: " + amount);
                    } else {
                        model.addAttribute("errorMessage", "Enter a withdrawal amount in multiples of 100, 200, or 500");
                    }
                } else {
                    model.addAttribute("errorMessage", "Insufficient balance");
                }
            } else {
                model.addAttribute("errorMessage", "Account not found");
            }
        } catch (Exception e) {
            return "withdrawal_form";
        }
        return "withdrawal_form";
    }

    private boolean isValidWithdrawal(double amount){
        return amount % 100 == 0 && (amount % 200 == 0 || amount % 500 ==0);
    }

    @GetMapping("/checkBalance")
    public String showCheckBalanceForm(Model model){
        model.addAttribute("account", new Account());
        return "check_balance_form";
    }

    @PostMapping("/checkBalance")
    public String checkBalance(@ModelAttribute("account") Account account, Model model){
        Optional<Account> optionalAccount = accountService.getAccountByAccountNumber1(account.getAccountNumber());
        if (optionalAccount.isPresent()){
            Account foundAccount = optionalAccount.get();
            model.addAttribute("firstName",foundAccount.getFirstName());
            model.addAttribute("lastName", foundAccount.getLastName());
            model.addAttribute("balance",  foundAccount.getBalance());
        } else {
            model.addAttribute("errorMessage", "Account not Found");
        }
        return "check_balance_form";
    }

    @GetMapping("/transfer")
    public String showTransferForm(Model model){
        model.addAttribute("fromAccount", new Account());
        model.addAttribute("toAccount",new Account());
        return "transfer_form";
    }

    @PostMapping("/transfer")
    public String transferAmount(@RequestParam("fromAccountNumber") String fromAccountNumber,
                                 @RequestParam("toAccountNumber") String toAccountNumber,
                                 @RequestParam("amount") double amount, Model model){

        Optional<Account> optionalFromAccount = accountService.getAccountByAccountNumber1(fromAccountNumber);
        Optional<Account> optionalToAccount = accountService.getAccountByAccountNumber1(fromAccountNumber);

        if (optionalFromAccount.isPresent() && optionalToAccount.isPresent()){
            Account fromAccount = optionalFromAccount.get();
            Account toAccount = optionalToAccount.get();

            if (fromAccount.getBalance()>= amount){
                fromAccount.setBalance(fromAccount.getBalance()- amount);
                toAccount.setBalance(toAccount.getBalance()+amount);
                accountService.saveAccount(fromAccount);
                accountService.saveAccount(toAccount);
                model.addAttribute("message","Amount transferred successfully.");
            } else {
                model.addAttribute("errorMessage", "Insufficient balance in the source account.");
            }
        }   else {
            model.addAttribute("errorMessage", "One of the accounts not found.");
        }
        return "transfer_form";
    }
}


