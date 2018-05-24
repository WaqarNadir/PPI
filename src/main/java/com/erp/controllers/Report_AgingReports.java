package com.erp.controllers;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.classes.Account_Payable;
import com.erp.classes.Account_Receivable;
import com.erp.classes.Constants;
import com.erp.classes.Functions;
import com.erp.services.Account_PayableService;
import com.erp.services.Account_ReceivableService;

@Controller
public class Report_AgingReports {
	@Autowired
	private Account_ReceivableService ARService;

	@Autowired
	private Account_PayableService APService;

	@GetMapping("Reports/APSummary")
	public String APAgingSummary(Model model) {
		APSummary(model);
		return "Report-APSummary";
	}

	@GetMapping("Reports/ARSummary")
	public String ARAgingSummary(Model model) {
		ARSummary(model);
		return "Report-ARSummary";
	}

	public Model APSummary(Model model) {

		List<Account_Payable> APList = APService.findByStatus(Constants.OPEN);
		List<Account_Payable> thirtyList = new ArrayList<>();
		List<Account_Payable> sixtyList = new ArrayList<>();
		List<Account_Payable> nintyList = new ArrayList<>();
		List<Account_Payable> nintyPlusList = new ArrayList<>();

		for (Account_Payable AP : APList) {

			Period diff = Period.between(AP.getDueDate().toLocalDate(), Functions.getCurrentDate().toLocalDate());

			System.out.println(diff.getMonths() + " - " + diff.getDays());

			if (diff.getMonths() > 3 || (diff.getMonths() == 3 && diff.getDays() != 0)) {
				nintyPlusList.add(AP);
				continue;
			}
			if (diff.getMonths() == 3 || (diff.getMonths() == 2 && diff.getDays() != 0)) {
				nintyList.add(AP);
				continue;
			}
			if (diff.getMonths() == 2 || (diff.getMonths() == 1 && diff.getDays() != 0)) {
				sixtyList.add(AP);
				continue;
			}
			if (diff.getMonths() <= 1) {
				thirtyList.add(AP);
				continue;
			}

		}
		Double thirtyTotal = 0.0;
		Double sixtyTotal = 0.0;
		Double nintyTotal = 0.0;
		Double nintyPlusTotal = 0.0;
		Double total = 0.0;

		for (Account_Payable AP : thirtyList) {
			thirtyTotal += AP.getTotal();
		}
		for (Account_Payable AP : sixtyList) {
			sixtyTotal += AP.getTotal();
		}
		for (Account_Payable AP : nintyList) {
			nintyTotal += AP.getTotal();
		}
		for (Account_Payable AP : nintyPlusList) {
			nintyPlusTotal += AP.getTotal();
		}
		total = thirtyTotal + sixtyTotal + nintyPlusTotal + nintyTotal;

		model.addAttribute("ApThirtyTotal", thirtyTotal);
		model.addAttribute("ApSixtyTotal", sixtyTotal);
		model.addAttribute("ApNintyTotal", nintyTotal);
		model.addAttribute("ApNintyPlusTotal", nintyPlusTotal);
		model.addAttribute("ApTotal", total);

		model.addAttribute("thirtyList", thirtyList);
		model.addAttribute("sixtyList", sixtyList);
		model.addAttribute("nintyList", nintyList);
		model.addAttribute("nintyPlusList", nintyPlusList);
		return model;
	}

	public Model ARSummary(Model model) {
		List<Account_Receivable> APList = ARService.findByStatus(Constants.OPEN);
		List<Account_Receivable> thirtyList = new ArrayList<>();
		List<Account_Receivable> sixtyList = new ArrayList<>();
		List<Account_Receivable> nintyList = new ArrayList<>();
		List<Account_Receivable> nintyPlusList = new ArrayList<>();

		for (Account_Receivable AP : APList) {

			Period diff = Period.between(AP.getDue_Date().toLocalDate(), Functions.getCurrentDate().toLocalDate());

			System.out.println(diff.getMonths() + " - " + diff.getDays());

			if (diff.getMonths() > 3 || (diff.getMonths() == 3 && diff.getDays() != 0)) {
				nintyPlusList.add(AP);
				continue;
			}
			if (diff.getMonths() == 3 || (diff.getMonths() == 2 && diff.getDays() != 0)) {
				nintyList.add(AP);
				continue;
			}
			if (diff.getMonths() == 2 || (diff.getMonths() == 1 && diff.getDays() != 0)) {
				sixtyList.add(AP);
				continue;
			}
			if (diff.getMonths() <= 1) {
				thirtyList.add(AP);
				continue;
			}

		}
		Double thirtyTotal = 0.0;
		Double sixtyTotal = 0.0;
		Double nintyTotal = 0.0;
		Double nintyPlusTotal = 0.0;
		Double total = 0.0;

		for (Account_Receivable AP : thirtyList) {
			thirtyTotal += AP.getTotal();
		}
		for (Account_Receivable AP : sixtyList) {
			sixtyTotal += AP.getTotal();
		}
		for (Account_Receivable AP : nintyList) {
			nintyTotal += AP.getTotal();
		}
		for (Account_Receivable AP : nintyPlusList) {
			nintyPlusTotal += AP.getTotal();
		}
		total = thirtyTotal + sixtyTotal + nintyPlusTotal + nintyTotal;

		model.addAttribute("thirtyTotal", thirtyTotal);
		model.addAttribute("sixtyTotal", sixtyTotal);
		model.addAttribute("nintyTotal", nintyTotal);
		model.addAttribute("nintyPlusTotal", nintyPlusTotal);
		model.addAttribute("total", total);

		model.addAttribute("thirtyList", thirtyList);
		model.addAttribute("sixtyList", sixtyList);
		model.addAttribute("nintyList", nintyList);
		model.addAttribute("nintyPlusList", nintyPlusList);
		return model;
	}

}
