package com.erp.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.AccountGroup;
import com.erp.classes.Account_Payable;
import com.erp.classes.Account_Receivable;
import com.erp.repo.Account_ReceivableRepo;

@Service
public class Account_ReceivableService {
	@Autowired
	public Account_ReceivableRepo repo;

	public void save(Account_Receivable accountReceivable) {
		repo.save(accountReceivable);
	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, Account_Receivable accountReceivable) {
		repo.save(accountReceivable);

	}

	public Account_Receivable find(int id) {
		return repo.findOne(id);

	}

	public List<Account_Receivable> getAll() {
		return repo.findAll();

	}

	public Account_Receivable SumTotal(Date startDate, Date endDate, String status) {
		return repo.sumTotal(startDate, endDate, status);
	}

}
