package com.erp.controllers;

import java.sql.Date;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.classes.Account_Payable;
import com.erp.classes.Account_Receivable;
import com.erp.classes.Constants;
import com.erp.classes.Functions;
import com.erp.classes.TrailBalance;
import com.erp.services.Account_PayableService;
import com.erp.services.Account_ReceivableService;
import com.erp.services.TrailBalanceService;

@Controller
public class Report_FundsDetails {
	@Autowired
	private TrailBalanceService service;

	@GetMapping("Reports/FundsDetails")
	public String fundsDetails(Model model) {
		Date startDate = Functions.thisMonth(Functions.getCurrentDate());
		Date endDate = Functions.getCurrentDate();

		List<TrailBalance> TbList = service.ByDateRange(startDate, endDate, Constants.isEquity);
		TbList.sort(Comparator.comparing(TrailBalance::getDate));
		model.addAttribute("list", TbList);
		List<TrailBalance> list = service.YearlyReport(Constants.isEquity);
		return "Report-FundsDetails";

	}

	@GetMapping("Reports/YearlyFunds")
	public String YearlyFunds(Model model) {
		List<TrailBalance> list = service.YearlyReport(Constants.isEquity);
		model.addAttribute("list", list);

		return "Report-YearlyFunds";

	}

}
