package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.erp.classes.TrailBalance;
import com.erp.repo.TrailBalanceRepo;

@Service
public class TrailBalanceService {
	@Autowired
	public TrailBalanceRepo repo;

	public void save(TrailBalance TrailBalance) {
		repo.save(TrailBalance);

	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, TrailBalance TrailBalance) {
		repo.save(TrailBalance);

	}

	public TrailBalance find(int id) {
		return repo.findOne(id);

	}

	public List<TrailBalance> getAll() {
		return repo.findAll();

	}

}
