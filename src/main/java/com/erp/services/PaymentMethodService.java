package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.PaymentMethods;
import com.erp.repo.PaymentMethodRepo;

@Service
public class PaymentMethodService {
	@Autowired
	public PaymentMethodRepo repo;

	public void save(PaymentMethods PaymentMethods) {
		repo.save(PaymentMethods);

	}

	public void delete(int id) {
		repo.delete(id);

	}

	public void update(int id, PaymentMethods PaymentMethods) {
		repo.save(PaymentMethods);

	}

	public PaymentMethods find(int id) {
		return repo.findOne(id);

	}

	public List<PaymentMethods> getAll() {
		return repo.findAll();

	}

}
