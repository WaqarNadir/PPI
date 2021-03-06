package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.Journal;
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

}
