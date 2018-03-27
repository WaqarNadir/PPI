package com.erp.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erp.classes.AccountGroup;
import com.erp.classes.ProfitLoss;

@Repository
public interface AccountGroupRepo extends JpaRepository<AccountGroup, Integer> {
	List<AccountGroup> findByIsParent(Integer parentRef);

	List<AccountGroup> findByremarks(String remarks);

	AccountGroup findByAccName(String name);

	List<AccountGroup> BalanceSheet(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}