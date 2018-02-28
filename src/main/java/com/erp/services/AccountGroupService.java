package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.AccountGroup;
import com.erp.repo.AccountGroupRepo;

@Service
public class AccountGroupService {
	@Autowired
	public AccountGroupRepo repo;

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

	public List<AccountGroup> getWithParefRef(int parentRef) {
		return repo.findByparentRef(parentRef);

	}

}
