package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.Account_Payable;

@Repository
public interface Account_PayableRepo extends JpaRepository<Account_Payable, Integer> {
}