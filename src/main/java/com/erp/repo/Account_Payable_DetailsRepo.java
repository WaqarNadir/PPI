package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.AP_Details;

@Repository
public interface Account_Payable_DetailsRepo extends JpaRepository<AP_Details, Integer> {
}
