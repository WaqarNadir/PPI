package com.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.ProfitLoss;
import com.erp.repo.ReportsRepo;

@Service
public class ReportsService {
	@Autowired
	public ReportsRepo repo;

	public List<ProfitLoss> profitLossReport() {
		return repo.ProfitLossReport();
	}
}
