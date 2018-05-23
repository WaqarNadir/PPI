package com.erp.repo;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erp.classes.ARReciept;
import com.erp.classes.Account_Receivable;

@Repository
public interface ARRecieptRepo extends JpaRepository<ARReciept, Integer> {
	@Query("SELECT p.AmountReceived " + "from ARReciept p " + "where p.date =:date and p.AR_ID =:AR_ID")
	Double SumTotal(@Param("date") Date date, @Param("AR_ID") Account_Receivable AR);

}