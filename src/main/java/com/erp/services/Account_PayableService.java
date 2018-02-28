package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.Account_Payable;
import com.erp.repo.Account_PayableRepo;

@Service
public class Account_PayableService {
	@Autowired
	public Account_PayableRepo repo;

	public void save(Account_Payable accountPayable) {
		repo.save(accountPayable);
		}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, Account_Payable accountPayable) {
		repo.save(accountPayable);

	}

	public Account_Payable find(int id) {
		return repo.findOne(id);

	}

	public List<Account_Payable> getAll() {
		return repo.findAll();

	}

}

