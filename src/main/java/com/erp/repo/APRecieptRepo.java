package com.erp.repo;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.erp.classes.APReciept;
import com.erp.classes.Account_Payable;

@Repository
public interface APRecieptRepo extends JpaRepository<APReciept, Integer> {
	@Query("SELECT COALESCE(sum(p.AmountPaid),0) " + "from APReciept p " + "where p.AP_ID =:AP")
	Double SumTotal(@Param("AP") Account_Payable AP);

	// @Query("SELECT p.AmountPaid " + "from APReciept p " + "where p.date =:date
	// and p.AP_ID =:AP")
	// Double SumTotal(@Param("date") Date date, @Param("AP") Account_Payable AP);

}
