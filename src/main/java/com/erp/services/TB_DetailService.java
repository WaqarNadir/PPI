package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.TB_Details;
import com.erp.repo.TrailBalanceDetailsRepo;

@Service
public class TB_DetailService {
	@Autowired
	public TrailBalanceDetailsRepo repo;

	public void save(TB_Details TB_Details) {
		repo.save(TB_Details);

	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, TB_Details TB_Details) {
		repo.save(TB_Details);

	}

	public TB_Details find(int id) {
		return repo.findOne(id);

	}

	public List<TB_Details> getAll() {
		return repo.findAll();

	}


}
