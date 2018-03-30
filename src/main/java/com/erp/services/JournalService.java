package com.erp.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.erp.classes.Journal;
import com.erp.classes.TrailBalance;
import com.erp.repo.JournalRepo;

@Service
public class JournalService {
	@Autowired
	public JournalRepo repo;

	public void save(Journal journal) {
		repo.save(journal);

	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, Journal journal) {
		repo.save(journal);

	}

	public Journal find(int id) {
		return repo.findOne(id);

	}

	public List<Journal> getAll() {
		return repo.findAll();

	}
	public List<Journal> ByDateRange(Date startDate, Date endDate) {
		return repo.ByDateRange(startDate, endDate);
	}

}
