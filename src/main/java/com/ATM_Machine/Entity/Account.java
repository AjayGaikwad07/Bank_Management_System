package com.ATM_Machine.Entity;

import com.ATM_Machine.Repository.AccountRepository;
import com.ATM_Machine.Service.AccountService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number",unique = true, updatable = false)
    private String accountNumber;
    private double balance;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private Long lastAccountNumberFromDatabase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    // Additional logic to generate account number automatically
    @PrePersist
    protected void onCreate() {
        this.accountNumber = generateAccountNumber(); // Automatically generate account number before persisting
    }

    private String generateAccountNumber(){
        StringBuilder accountNumberBuilder = new StringBuilder();
        accountNumberBuilder.append("622");

        Random random =new Random();
        for (int i=0; i< 6; i++){
            accountNumberBuilder.append(random.nextInt(10));
        }
        return accountNumberBuilder.toString();
    }
}
