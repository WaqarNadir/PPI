package com.erp.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erp.classes.ProfitLoss;
import com.erp.classes.Reports;

@Repository
public interface ReportsRepo extends JpaRepository<Reports, Integer> {
	List<ProfitLoss> ProfitLossReport(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("statusNotLike") String statusNotLike);

}