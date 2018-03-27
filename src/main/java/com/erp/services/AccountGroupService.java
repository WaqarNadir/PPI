package com.erp.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.AccountGroup;
import com.erp.classes.Constants;
import com.erp.repo.AccountGroupRepo;

@Service
public class AccountGroupService {
	@Autowired
	public AccountGroupRepo repo;

	public AccountGroup Asset = null;
	public AccountGroup equity = null;
	public AccountGroup liability = null;

	public void init() {
		List<AccountGroup> AGList = getWithParentRef(null);
		for (AccountGroup AG : AGList) {
			if (AG.getAccName().equals(Constants.ASSETS)) {
				Asset = AG;
			}
			if (AG.getAccName().equals(Constants.EQUITY)) {
				equity = AG;
			}
			if (AG.getAccName().equals(Constants.LIABILITY)) {
				liability = AG;
			}
		}
	}

	public void save(AccountGroup account_Group) {
		repo.save(account_Group);

	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, AccountGroup account_Group) {
		repo.save(account_Group);

	}

	public AccountGroup find(int id) {
		return repo.findOne(id);

	}

	public List<AccountGroup> getAll() {
		return repo.findAll();

	}

	public List<AccountGroup> getWithParentRef(Integer parentRef) {
		return repo.findByIsParent(parentRef);

	}

	public List<AccountGroup> getAllWithRemarks(String remarks) {
		return repo.findByremarks(remarks);
	}

	public AccountGroup findByName(String name) {
		return repo.findByAccName(name);
	}

	public List<AccountGroup> BalanceSheetReport(Date startDate, Date endDate) {
		return repo.BalanceSheet(startDate, endDate);
	}

	public AccountGroupService() {
	}
}
