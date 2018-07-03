package com.erp.services;

import java.sql.Date;
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

	public List<Account_Payable> findByStatus(String status) {
		return repo.findByStatus(status);
	}

	public List<Account_Payable> findByStatusNotLike(String statusNotLike) {
		return repo.findByStatusNotLike(statusNotLike);
	}

	public Account_Payable SumTotal(Date startDate, Date endDate, String status) {
		return repo.SumTotal(startDate, endDate, status);
	}

	public List<Account_Payable> ByDateRange(Date startDate, Date endDate) {
		return repo.ByDateRange(startDate, endDate);
	}

	public List<Account_Payable> DateBetween(Date startDate, Date endDate) {
		return repo.DateBetween(startDate, endDate);
	}

	public List<Account_Payable> DueDateBetween(Date startDate, Date endDate) {
		return repo.DueDateBetween(startDate, endDate);
	}

	public List<Account_Payable> DateAndStatusNotLike(Date startDate, Date endDate, String statusNotLike) {
		return repo.DateAndStatusNotLike(startDate, endDate, statusNotLike);
	}

	public List<Account_Payable> DateAndStatus(Date startDate, Date endDate, String status) {
		return repo.DateAndStatus(startDate, endDate, status);
	}

}
