package com.erp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.TrailBalance;

@Repository
public interface TrailBalanceRepo extends JpaRepository<TrailBalance, Integer> {
	List<TrailBalance> findByType(int type);

}