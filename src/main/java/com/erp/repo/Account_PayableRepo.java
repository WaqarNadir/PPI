package com.erp.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erp.classes.Account_Payable;


@Repository
public interface Account_PayableRepo extends JpaRepository<Account_Payable, Integer> {
	
	List<Account_Payable> ByDateRange(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

	@Query("SELECT " + "    new com.erp.classes.Account_Payable(p.status, sum(p.total)) " + "from AccountPayable p "
			+ "where p.date BETWEEN :startDate and :endDate and p.status = :status" + " GROUP BY p.status")
	Account_Payable SumTotal(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("status") String status);


	List<Account_Payable> DateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	List<Account_Payable> DueDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	List<Account_Payable> DateAndStatus(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("status") String status);

	List<Account_Payable> findByStatus(String status);

}