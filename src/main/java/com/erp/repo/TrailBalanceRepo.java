package com.erp.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.classes.ProfitLoss;
import com.erp.classes.TrailBalance;
import java.sql.Date;

@Repository
public interface TrailBalanceRepo extends JpaRepository<TrailBalance, Integer> {
	List<TrailBalance> findByType(int type);

}