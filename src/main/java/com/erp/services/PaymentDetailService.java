package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.PaymentDetails;
import com.erp.repo.PaymentDetailRepo;

@Service
public class PaymentDetailService {
	@Autowired
	public PaymentDetailRepo repo;

	public void save(PaymentDetails PaymentDetails) {
		repo.save(PaymentDetails);

	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, PaymentDetails PaymentDetails) {
		repo.save(PaymentDetails);

	}

	public PaymentDetails find(int id) {
		return repo.findOne(id);

	}

	public List<PaymentDetails> getAll() {
		return repo.findAll();

	}

}
