package com.erp.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.erp.classes.AccountGroup;
import com.erp.classes.Constants;
import com.erp.classes.Functions;
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
		Date currentDate = Functions.getCurrentDate();
		Date lastMonth = Functions.thisMonth(currentDate);

		System.out.println(
				"This Month: " + Functions.thisMonth(currentDate) + "\n this Year: " + Functions.thisYear(currentDate));
		System.out.println("current Date: " + currentDate + "\n Last Month: " + lastMonth);

		List<ProfitLoss> profitLossList = service.profitLossReport(lastMonth, currentDate);
		for (ProfitLoss PL : profitLossList) {

			if (PL.getType() == Constants.isIncome) {
				incomeSum += PL.getSubTotal();
			}
			if (PL.getType() == Constants.isExpense) {
				expenseSum += PL.getSubTotal();
			}

		}
		model.addAttribute("profitLossList", profitLossList);
		model.addAttribute("incomeSum", incomeSum);
		model.addAttribute("expenseSum", expenseSum);
		model.addAttribute("netEquity", (incomeSum + expenseSum));
		return "Report-ProfitLoss";
	}

	@PostMapping("Reports/DateWiseProfitLoss")
	public String dateWiseProfitReport(HttpServletRequest request, Model model) {
		double incomeSum = 0, expenseSum = 0;
		Date startDate = null, endDate = null;
		List<ProfitLoss> profitLossList = null;
		String msg = "";
		String value = request.getParameter("selectValue");

		if (value.equals("3")) {
			System.out.println("Displaying Custom Report");
			startDate = Functions.getSQLDate(request.getParameter("startDate"));
			endDate = Functions.getSQLDate(request.getParameter("endDate"));
		}

		if (value.equals("2")) {
			System.out.println("Displaying Yearly Report");
			startDate = Functions.thisYear(Functions.getCurrentDate());
			endDate = Functions.getCurrentDate();
		}

		if (value.equals("1")) {
			System.out.println("Displaying Monthly Report");
			startDate = Functions.thisMonth(Functions.getCurrentDate());
			endDate = Functions.getCurrentDate();
		}
		profitLossList = service.profitLossReport(startDate, endDate);
		for (ProfitLoss PL : profitLossList) {

			if (PL.getType() == Constants.isIncome) {
				incomeSum += PL.getSubTotal();
			}
			if (PL.getType() == Constants.isExpense) {
				expenseSum += PL.getSubTotal();
			}

		}
		msg = "From " + startDate + " to " + endDate;
		model.addAttribute("label", msg);
		model.addAttribute("selectedValue", value);
		model.addAttribute("profitLossList", profitLossList);
		model.addAttribute("incomeSum", incomeSum);
		model.addAttribute("expenseSum", expenseSum);
		model.addAttribute("netEquity", (incomeSum + expenseSum));
		return "Report-ProfitLoss";
	}

	// @GetMapping("Reports/BalanceSheet")
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
		return AGService.getWithParentRef(null);
	}

	void filterAccountGroups() {
		assets = new AccountGroup();
		double total = 0;
		liability = new AccountGroup();
		equity = new AccountGroup();
		Date startDate = Functions.thisMonth(Functions.getCurrentDate());
		Date endDate = Functions.getCurrentDate();
		List<AccountGroup> AGList = getAccountGroups();
		List<AccountGroup> BalanceSheetList = AGService.BalanceSheetReport(startDate, endDate);
		List<AccountGroup> list = AGService.getAll();

		for (AccountGroup AG : AGList) {
			if (AG.getAccName().equals(Constants.ASSETS)) {
				assets = AG;

				total = 0;
				for (AccountGroup child : AG.getChildList()) {
					child.setChildList(new ArrayList<>());
					double childSubTotal = 0;

					System.out.println(child.getAccName() + " - " + child.getAcc_ID());

					for (AccountGroup BS : BalanceSheetList) {
						if (BS.getIsParent().getAcc_ID() == child.getAcc_ID()) {
							child.getChildList().add(BS);
							childSubTotal += BS.getAmount();
						}
					}

					child.setAmount(childSubTotal);
					total += child.getAmount();
				}
				assets.setRemarks(total + "");
				continue;
			}

			if (AG.getAccName().equals(Constants.LIABILITY)) {
				liability = AG;

				total = 0;
				for (AccountGroup child : AG.getChildList()) {
					child.setChildList(new ArrayList<>());
					double childSubTotal = 0;
					System.out.println(child.getAccName() + " - " + child.getAcc_ID());
					for (AccountGroup BS : BalanceSheetList) {
						if (BS.getIsParent().getAcc_ID() == child.getAcc_ID()) {
							child.getChildList().add(BS);
							childSubTotal += BS.getAmount();
						}
					}

					child.setAmount(childSubTotal);
					total += child.getAmount();
				}
				liability.setRemarks(total + "");
				continue;
			}
			if (AG.getAccName().equals(Constants.EQUITY)) {
				equity = AG;

				total = 0;
				for (AccountGroup child : AG.getChildList()) {
					child.setChildList(new ArrayList<>());
					double childSubTotal = 0;
					System.out.println(child.getAccName() + " - " + child.getAcc_ID());
					for (AccountGroup BS : BalanceSheetList) {
						if (BS.getIsParent().getAcc_ID() == child.getAcc_ID()) {
							child.getChildList().add(BS);
							childSubTotal += BS.getAmount();
						}
					}

					child.setAmount(childSubTotal);
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

	public String test(Model model) {
		Date startDate = Functions.thisMonth(Functions.getCurrentDate());
		Date endDate = Functions.getCurrentDate();

		List<AccountGroup> BalanceSheetList = AGService.BalanceSheetReport(startDate, endDate);

		AccountGroup fixedAsset = new AccountGroup();
		AccountGroup currentAsset = new AccountGroup();
		AccountGroup currentLiability = new AccountGroup();
		AccountGroup longTermLiability = new AccountGroup();
		AccountGroup equity = new AccountGroup();

		// double fixedAssetTotal = 0.0;
		// double currentAssetTotal = 0.0;
		// double londTermLiabilityTotal = 0.0;
		// double currentLiabilityTotal = 0.0;
		// double equityTotal = 0.0;

		fixedAsset.setAccName("fixedAsset");
		currentAsset.setAccName("currentAsset");
		currentLiability.setAccName("currentLiability");
		longTermLiability.setAccName("longTermLiability");
		equity.setAccName("equity");

		for (AccountGroup AG : BalanceSheetList) {
			if (AG.getIsParent().getAcc_ID() == Constants.FIXED_ASSETS_ID) {
				// fixedAssetTotal += AG.getAmount();
				fixedAsset.setAmount((fixedAsset.getAmount() + AG.getAmount()));
				fixedAsset.getChildList().add(AG);

			}
			if (AG.getIsParent().getAcc_ID() == Constants.Current_ASSETS_ID) {
				// currentAssetTotal += AG.getAmount();
				currentAsset.setAmount((currentAsset.getAmount() + AG.getAmount()));
				currentAsset.getChildList().add(AG);

			}
			if (AG.getIsParent().getAcc_ID() == Constants.CURRENT_LIABILITIES_ID) {
				// currentLiabilityTotal += AG.getAmount();
				currentLiability.setAmount((currentLiability.getAmount() + AG.getAmount()));
				currentLiability.getChildList().add(AG);

			}
			if (AG.getIsParent().getAcc_ID() == Constants.LONG_TERM_LIABILITIES_ID) {
				// londTermLiabilityTotal += AG.getAmount();
				longTermLiability.setAmount((longTermLiability.getAmount() + AG.getAmount()));
				longTermLiability.getChildList().add(AG);

			}
			if (AG.getIsParent().getAcc_ID() == Constants.EQUITY_AND_FUNDS_ID) {
				// equityTotal += AG.getAmount();
				equity.setAmount((equity.getAmount() + AG.getAmount()));
				equity.getChildList().add(AG);

			}

		}
		model.addAttribute("fixedAsset", fixedAsset);
		model.addAttribute("currentAsset", currentAsset);
		model.addAttribute("currentLiability", currentLiability);
		model.addAttribute("longTermLiability", longTermLiability);
		model.addAttribute("equity", equity);
		return "Report-BalanceSheet";
	}

	@GetMapping("Reports/BalanceSheet")
	public String test2(Model model) {
		AGService.init();
		Date startDate = Functions.thisMonth(Functions.getCurrentDate());
		Date endDate = Functions.getCurrentDate();

		List<AccountGroup> BalanceSheetList = AGService.BalanceSheetReport(startDate, endDate);

		AccountGroup Asset = new AccountGroup(AGService.Asset);
		AccountGroup liability = new AccountGroup(AGService.liability);
		AccountGroup equity = new AccountGroup(AGService.equity);

		Asset.getChildList().forEach(s -> {
			s.setChildList(new ArrayList<>());
			s.setAmount(0.0);
		});

		liability.getChildList().forEach(s -> {
			s.setChildList(new ArrayList<>());
			s.setAmount(0.0);
		});

		equity.getChildList().forEach(s -> {
			s.setChildList(new ArrayList<>());
			s.setAmount(0.0);
		});

		for (AccountGroup AG : BalanceSheetList) {

			for (AccountGroup child : Asset.getChildList()) {

				System.out.println("Child: " + child.getAcc_ID() + "  AG: " + AG.getIsParent().getAcc_ID());
				if (child.getAcc_ID() == AG.getIsParent().getAcc_ID()) {
					child.getChildList().add(AG);
					child.setAmount((child.getAmount() + AG.getAmount()));
				}
			}

			for (AccountGroup child : liability.getChildList()) {
				System.out.println("Child: " + child.getAcc_ID() + "AG: " + AG.getIsParent().getAcc_ID());
				if (child.getAcc_ID() == AG.getIsParent().getAcc_ID()) {
					child.getChildList().add(AG);
					child.setAmount((child.getAmount() + AG.getAmount()));
				}
			}

			for (AccountGroup child : equity.getChildList()) {
				System.out.println("Child: " + child.getAcc_ID() + "AG: " + AG.getIsParent().getAcc_ID());
				if (child.getAcc_ID() == AG.getIsParent().getAcc_ID()) {
					child.getChildList().add(AG);
					child.setAmount((child.getAmount() + AG.getAmount()));
				}
			}

		}
		AccountGroup netEquity = new AccountGroup();
		netEquity.setAccName("Net Equity");

		List<ProfitLoss> profitLossList = service.profitLossReport(startDate, endDate);
		for (ProfitLoss pf : profitLossList) {
			netEquity.setAmount(netEquity.getAmount() + pf.getSubTotal());
		}
		equity.getChildList().add(netEquity);
		model.addAttribute("assets", Asset);
		model.addAttribute("liability", liability);
		model.addAttribute("equity", equity);

		return "Report-BalanceSheet";
	}

}
