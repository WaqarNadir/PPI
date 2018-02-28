package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.AP_Details;
import com.erp.repo.Account_Payable_DetailsRepo;

@Service
public class AP_DetailsService {
	@Autowired
	public Account_Payable_DetailsRepo repo;

	public void save(AP_Details aP_Details) {
		repo.save(aP_Details);

	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, AP_Details aP_Details) {
		repo.save(aP_Details);

	}

	public AP_Details find(int id) {
		return repo.findOne(id);

	}

	public List<AP_Details> getAll() {
		return repo.findAll();

	}

}
