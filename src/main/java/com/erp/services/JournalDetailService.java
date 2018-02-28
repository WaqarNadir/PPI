package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.JournalDetails;
import com.erp.repo.JournalDetailRepo;

@Service
public class JournalDetailService {
	@Autowired
	public JournalDetailRepo repo;

	public void save(JournalDetails JournalDetails) {
		repo.save(JournalDetails);

	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, JournalDetails JournalDetails) {
		repo.save(JournalDetails);

	}

	public JournalDetails find(int id) {
		return repo.findOne(id);

	}

	public List<JournalDetails> getAll() {
		return repo.findAll();

	}

}
