package com.erp.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erp.classes.TrailBalance;

@Repository
public interface TrailBalanceRepo extends JpaRepository<TrailBalance, Integer> {
	List<TrailBalance> findByType(int type);

	List<TrailBalance> ByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("type") int type);

	List<TrailBalance> DateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT  new com.erp.classes.TrailBalance(FUNCTION('YEAR', p.date),sum(p.total) ) " + "from TrailBalance p "
			+ "where p.type =:type" + " GROUP BY FUNCTION('YEAR', p.date) ")
	List<TrailBalance> YearlyReport(@Param("type") int type);

}