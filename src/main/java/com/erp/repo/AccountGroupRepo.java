package com.erp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.AccountGroup;

@Repository
public interface AccountGroupRepo extends JpaRepository<AccountGroup, Integer> {
	List<AccountGroup> findByIsParent(Integer parentRef);

	List<AccountGroup> findByremarks(String remarks);

	AccountGroup findByAccName(String name);

}