package com.erp.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

	public List<TrailBalance> findByType(int type) {
		return repo.findByType(type);
	}

	public List<TrailBalance> ByDateRange(Date startDate, Date endDate, int type) {
		return repo.ByDateRange(startDate, endDate, type);
	}
}
