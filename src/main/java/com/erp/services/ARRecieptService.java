package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.ARReciept;
import com.erp.repo.ARRecieptRepo;

@Service
public class ARRecieptService {
	@Autowired
	public ARRecieptRepo repo;

	public void save(ARReciept arReciept) {
		repo.save(arReciept);
		}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, ARReciept arReciept) {
		repo.save(arReciept);

	}

	public ARReciept find(int id) {
		return repo.findOne(id);

	}

	public List<ARReciept> getAll() {
		return repo.findAll();

	}

}



