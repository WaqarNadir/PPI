package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.AR_Details;
import com.erp.repo.Account_Receivable_DetailsRepo;

@Service
public class AR_DetailsService {
	@Autowired
	public Account_Receivable_DetailsRepo repo;

	public void save(AR_Details aR_Details) {
		repo.save(aR_Details);

	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, AR_Details aR_Details) {
		repo.save(aR_Details);

	}

	public AR_Details find(int id) {
		return repo.findOne(id);

	}

	public List<AR_Details> getAll() {
		return repo.findAll();

	}

}
