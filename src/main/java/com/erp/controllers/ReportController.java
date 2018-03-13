package com.erp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.classes.AccountGroup;
import com.erp.classes.Constants;
import com.erp.classes.ProfitLoss;
import com.erp.services.AccountGroupService;
import com.erp.services.ReportsService;

@Controller
public class ReportController {

	@Autowired
	private ReportsService service;
	@Autowired
	private AccountGroupService AGService;

	AccountGroup assets = null;
	AccountGroup liability = null;
	AccountGroup equity = null;

	@GetMapping("Reports/ProfitLoss")
	public String ProfitLossReport(Model model) {
		double incomeSum = 0;
		double expenseSum = 0;
		List<ProfitLoss> profitLossList = service.profitLossReport();
		for (ProfitLoss PL : profitLossList) {

			if (PL.getType() == 1) {
				incomeSum += PL.getSubTotal();
			}
			if (PL.getType() == 2) {
				expenseSum += PL.getSubTotal();
			}

		}
		model.addAttribute("profitLossList", profitLossList);
		model.addAttribute("incomeSum", incomeSum);
		model.addAttribute("expenseSum", expenseSum);
		model.addAttribute("netEquity", (incomeSum + expenseSum));
		return "Report-ProfitLoss";
	}

	@GetMapping("Reports/BalanceSheet")
	public String BalanceSheet(Model model) {
		filterAccountGroups();
		model.addAttribute("assets", assets);
		model.addAttribute("liability", liability);
		model.addAttribute("equity", equity);

		return "Report-BalanceSheet";
	}

	@GetMapping("Reports/ChartsOfAccount")
	public String ChartsOfAccount(Model model) {
		model.addAttribute("list", getAccountGroups());
		return "Report-ChartOfAccount";
	}

	@GetMapping("Reports/Testing")
	public String Testing(Model model) {
		model.addAttribute("list", getAccountGroups());
		return "Report-Testing";
	}

	List<AccountGroup> getAccountGroups() {
		return AGService.getWithParefRef(null);
	}

	void filterAccountGroups() {
		assets = new AccountGroup();
		double total = 0;
		liability = new AccountGroup();
		equity = new AccountGroup();
		List<AccountGroup> AGList = getAccountGroups();

		for (AccountGroup AG : AGList) {
			if (AG.getAccName().equals(Constants.ASSETS)) {
				assets = AG;

				total = 0;
				for (AccountGroup child : AG.getChildList()) {
					total += child.getAmount();
				}
				assets.setRemarks(total + "");
				continue;
			}

			if (AG.getAccName().equals(Constants.LIABILITY)) {
				liability = AG;

				total = 0;
				for (AccountGroup child : AG.getChildList()) {
					total += child.getAmount();
				}
				liability.setRemarks(total + "");
				continue;
			}
			if (AG.getAccName().equals(Constants.EQUITY)) {
				equity = AG;

				total = 0;
				for (AccountGroup child : AG.getChildList()) {
					total += child.getAmount();
				}
				equity.setRemarks(total + "");
				continue;
			}
		}
		/*
		 * Assets current Assets account receivable
		 * 
		 * Liability current Liability Account Payable
		 * 
		 * Equity Net Equity
		 * 
		 */

	}

}
