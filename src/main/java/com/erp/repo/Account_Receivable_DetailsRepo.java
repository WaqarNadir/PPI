package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erp.classes.AR_Details;

@Repository
public interface Account_Receivable_DetailsRepo extends JpaRepository<AR_Details, Integer> {
}
