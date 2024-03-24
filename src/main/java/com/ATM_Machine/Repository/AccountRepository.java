package com.ATM_Machine.Repository;

import com.ATM_Machine.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
     Optional<Account> findByAccountNumber(String accountNumber);
}
