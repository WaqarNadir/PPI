package com.erp.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.APReciept;
import com.erp.classes.Account_Payable;
import com.erp.repo.APRecieptRepo;

@Service
public class APRecieptService {
	@Autowired
	public APRecieptRepo repo;

	public void save(APReciept apReciept) {
		repo.save(apReciept);
	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, APReciept apReciept) {
		repo.save(apReciept);

	}

	public APReciept find(int id) {
		return repo.findOne(id);

	}

	public List<APReciept> getAll() {
		return repo.findAll();

	}

	public Double SumTotal(Account_Payable AP) {
		return repo.SumTotal(AP);
	}
}
