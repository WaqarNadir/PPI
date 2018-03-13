package com.erp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.ProfitLoss;
import com.erp.classes.Reports;

@Repository
public interface ReportsRepo extends JpaRepository<Reports, Integer> {
	List<ProfitLoss> ProfitLossReport();

}