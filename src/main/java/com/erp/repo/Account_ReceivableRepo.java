package com.erp.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erp.classes.Account_Receivable;

@Repository
public interface Account_ReceivableRepo extends JpaRepository<Account_Receivable, Integer> {
	
	List<Account_Receivable> ByDateRange(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

	@Query("SELECT " + "    new com.erp.classes.Account_Receivable(p.status, sum(p.total)) "
			+ "from AccountReceivable p " + "where p.date BETWEEN :startDate and :endDate and p.status = :status"
			+ " GROUP BY p.status")

	Account_Receivable sumTotal(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("status") String status);

	List<Account_Receivable> DateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	List<Account_Receivable> findByStatus(String status);

}