package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.TB_Details;
import com.erp.classes.TrailBalance;

@Repository
public interface TrailBalanceDetailsRepo extends JpaRepository<TB_Details, Integer> {
}