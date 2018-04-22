package com.erp.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.erp.classes.AccountGroup;
import com.erp.classes.Account_Payable;
import com.erp.classes.Account_Receivable;
import com.erp.classes.Constants;
import com.erp.classes.Functions;
import com.erp.classes.Journal;
import com.erp.classes.JournalDetails;
import com.erp.classes.ProfitLoss;
import com.erp.classes.TrailBalance;
import com.erp.services.AccountGroupService;
import com.erp.services.Account_PayableService;
import com.erp.services.Account_ReceivableService;
import com.erp.services.JournalService;
import com.erp.services.ReportsService;
import com.erp.services.TrailBalanceService;

@Controller
public class ReportController {

	@Autowired
	private ReportsService service;
	@Autowired
	private AccountGroupService AGService;
	@Autowired
	private Account_ReceivableService ARService;

	@Autowired
	private Account_PayableService APService;
	@Autowired
	private TrailBalanceService TBService;

	@Autowired
	private JournalService journalService;

	AccountGroup assets = null;
	AccountGroup liability = null;
	AccountGroup equity = null;

	@GetMapping("Reports/ProfitLoss")
	public String ProfitLossReport(Model model) {
		profitLossDetail(model);
		return "Report-ProfitLoss";
	}

	public Model profitLossDetail(Model model) {
		double incomeSum = 0;
		double expenseSum = 0;
		Date currentDate = Functions.getCurrentDate();
		Date lastMonth = Functions.thisMonth(currentDate);

		System.out.println(
				"This Month: " + Functions.thisMonth(currentDate) + "\n this Year: " + Functions.thisYear(currentDate));
		System.out.println("current Date: " + currentDate + "\n Last Month: " + lastMonth);

		List<ProfitLoss> profitLossList = service.profitLossReport(lastMonth, currentDate);
		HashMap<String, Double> incomeMap = new HashMap<>();
		HashMap<String, Double> expenseMap = new HashMap<>();
		for (ProfitLoss PL : profitLossList) {

			if (PL.getType() == Constants.isIncome) {
				incomeSum += PL.getSubTotal();
				incomeMap.put(PL.getName(), incomeMap.getOrDefault(PL.getName(), 0.0) + PL.getSubTotal());
			}
			if (PL.getType() == Constants.isExpense) {
				expenseSum += PL.getSubTotal();
				expenseMap.put(PL.getName(), expenseMap.getOrDefault(PL.getName(), 0.0) + PL.getSubTotal());
			}

		}

		incomeSum += populateJournalIncome(lastMonth, currentDate, profitLossList);
		model.addAttribute("profitLossList", profitLossList);
		model.addAttribute("incomeSum", incomeSum);
		model.addAttribute("incomeList", incomeMap);
		model.addAttribute("expenseList", expenseMap);
		model.addAttribute("expenseSum", expenseSum);
		model.addAttribute("netEquity", (incomeSum + expenseSum));
		return model;
	}

	private double populateJournalIncome(Date startDate, Date endDate, List<ProfitLoss> profitLossList) {
		List<Journal> JournalList = journalService.ByDateRange(startDate, endDate);
		HashMap<AccountGroup, Double> map = new HashMap<>();
		double incomeSum = 0.0;
		for (Journal journal : JournalList) {

			for (JournalDetails JD : journal.getJDList()) {
				if (!JD.getSubGroup_ID().getIsParent().getIsParent().getAccName().equals(Constants.INCOME))
					continue;
				map.put(JD.getSubGroup_ID(), map.getOrDefault(JD.getSubGroup_ID(), 0.0) + JD.getSubTotal());
			}
		}
		for (AccountGroup AG : map.keySet()) {
			incomeSum += map.get(AG);
			ProfitLoss pl = new ProfitLoss(AG.getAccName(), map.get(AG), Constants.isIncome);
			profitLossList.add(pl);

		}
		return incomeSum;
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
		HashMap<String, Double> incomeMap = new HashMap<>();
		HashMap<String, Double> expenseMap = new HashMap<>();
		for (ProfitLoss PL : profitLossList) {

			if (PL.getType() == Constants.isIncome) {
				incomeSum += PL.getSubTotal();
				incomeMap.put(PL.getName(), incomeMap.getOrDefault(PL.getName(), 0.0) + PL.getSubTotal());
			}
			if (PL.getType() == Constants.isExpense) {
				expenseSum += PL.getSubTotal();
				expenseMap.put(PL.getName(), expenseMap.getOrDefault(PL.getName(), 0.0) + PL.getSubTotal());
			}

		}
		incomeSum += populateJournalIncome(startDate, endDate, profitLossList);
		msg = "From " + startDate + " to " + endDate;
		model.addAttribute("label", msg);
		model.addAttribute("selectedValue", value);
		model.addAttribute("profitLossList", profitLossList);
		model.addAttribute("incomeSum", incomeSum);
		model.addAttribute("expenseSum", expenseSum);

		model.addAttribute("incomeList", incomeMap);
		model.addAttribute("expenseList", expenseMap);
		model.addAttribute("netEquity", (incomeSum + expenseSum));
		return "Report-ProfitLoss";
	}

	// @GetMapping("Reports/BalanceSheet")
	/*
	 * public String BalanceSheet(Model model) { filterAccountGroups();
	 * model.addAttribute("assets", assets); model.addAttribute("liability",
	 * liability); model.addAttribute("equity", equity);
	 * 
	 * return "Report-BalanceSheet"; }
	 */

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

	// @GetMapping("Reports/BalanceSheet")
	public String test2(Model model) {
		AGService.init();
		Date startDate = Functions.thisMonth(Functions.getCurrentDate());
		Date endDate = Functions.getCurrentDate();

		List<AccountGroup> BalanceSheetList = AGService.BalanceSheetReport(startDate, endDate);
		Account_Payable payable = APService.SumTotal(startDate, endDate, Constants.OPEN);
		Account_Receivable recievable = ARService.SumTotal(startDate, endDate, Constants.OPEN);
		List<TrailBalance> TBList = TBService.DateBetween(startDate, endDate);

		// account recieveable and payable banksource

		AccountGroup Asset = new AccountGroup(AGService.Asset);
		AccountGroup liability = new AccountGroup(AGService.liability);
		AccountGroup equity = new AccountGroup(AGService.equity);

		if (recievable != null)
			Asset.setAmount(recievable.getTotal());
		else
			Asset.setAmount(0.0);

		if (payable != null)
			liability.setAmount(payable.getTotal());
		else
			liability.setAmount(0.0);
		equity.setAmount(0.0);
		Asset.getChildList().forEach(s -> {
			s.setChildList(new ArrayList<>());
			s.setAmount(0.0);
			if (s.getAccName().equals(Constants.CURRENT_ASSETS)) {

				if (recievable != null) {
					s.setAmount(recievable.getTotal());
				}
			}
		});

		liability.getChildList().forEach(s -> {
			s.setChildList(new ArrayList<>());
			s.setAmount(0.0);
			if (s.getAccName().equals(Constants.CURRENT_LIABILITY) && payable != null) {
				s.setAmount(payable.getTotal());
			}

		});

		System.out.println(" Before Bank source : " + Asset.getAmount());

		equity.getChildList().forEach(s ->

		{
			s.setChildList(new ArrayList<>());
			s.setAmount(0.0);
		});
		List<ProfitLoss> profitLossList = new ArrayList<>();
		for (AccountGroup AG : BalanceSheetList) {
			boolean childFound = false;

			for (AccountGroup child : Asset.getChildList()) {

				System.out.println("Child: " + child.getAcc_ID() + "  AG: " + AG.getIsParent().getAcc_ID());

				if (child.getAcc_ID() == AG.getIsParent().getAcc_ID()) {
					child.getChildList().add(AG);
					child.setAmount((child.getAmount() + AG.getAmount()));
					Asset.setAmount((Asset.getAmount() + AG.getAmount()));
					childFound = true;
					break;
				}

			}
			if (childFound)
				continue;

			for (AccountGroup child : liability.getChildList()) {
				System.out.println("Child: " + child.getAcc_ID() + "AG: " + AG.getIsParent().getAcc_ID());
				if (child.getAcc_ID() == AG.getIsParent().getAcc_ID()) {
					child.getChildList().add(AG);
					child.setAmount((child.getAmount() + AG.getAmount()));
					liability.setAmount((liability.getAmount() + AG.getAmount()));

					childFound = true;
					break;
				}
			}
			if (childFound)
				continue;

			for (AccountGroup child : equity.getChildList()) {
				System.out.println("Child: " + child.getAcc_ID() + "AG: " + AG.getIsParent().getAcc_ID());
				if (child.getAcc_ID() == AG.getIsParent().getAcc_ID()) {
					child.getChildList().add(AG);
					child.setAmount((child.getAmount() + AG.getAmount()));
					equity.setAmount((equity.getAmount() + AG.getAmount()));

					childFound = true;
					break;
				}
			}

			ProfitLoss pf = new ProfitLoss();
			pf.setName(AG.getAccName());
			pf.setSubTotal(AG.getAmount());
			if (AG.getIsParent().getAcc_ID() == Constants.EXPENSE_ID)
				pf.setType(Constants.isExpense);
			else
				pf.setType(Constants.isIncome);
			profitLossList.add(pf);
		}

		AccountGroup netEquity = new AccountGroup();
		netEquity.setAccName("Net Income");

		profitLossList.addAll(service.profitLossReport(startDate, endDate));
		for (ProfitLoss pf : profitLossList) {
			if (pf.getType() == Constants.isExpense)
				netEquity.setAmount(netEquity.getAmount() - pf.getSubTotal());
			else
				netEquity.setAmount(netEquity.getAmount() + pf.getSubTotal());
		}
		equity.getChildList().add(netEquity);
		equity.setAmount(equity.getAmount() + netEquity.getAmount());

		int index = -1;
		Double amount = 0.0;

		for (AccountGroup currentAsset : Asset.getChildList()) {
			if (!currentAsset.getAccName().equalsIgnoreCase(Constants.CURRENT_ASSETS))
				continue;

			for (TrailBalance bankSource : TBList) {
				if (bankSource.getType() == Constants.isExpense)
					amount = -bankSource.getTotal();
				else
					amount = bankSource.getTotal();

				index = currentAsset.getChildList().indexOf(bankSource.getbankSourceID());
				if (index == -1) {
					bankSource.getbankSourceID().setAmount(amount);
					currentAsset.getChildList().add(bankSource.getbankSourceID());

					currentAsset.setAmount(currentAsset.getAmount() + amount);
					Asset.setAmount(Asset.getAmount() + amount);
				} else {
					AccountGroup ag = currentAsset.getChildList().get(index);
					ag.setAmount(ag.getAmount() + amount);

					currentAsset.setAmount(currentAsset.getAmount() + amount);
					Asset.setAmount(Asset.getAmount() + amount);
				}

			}
		}

		System.out.println(" After Bank source : " + Asset.getAmount());

		model.addAttribute("recievable", recievable);
		model.addAttribute("payable", payable);
		model.addAttribute("assets", Asset);
		model.addAttribute("liability", liability);
		model.addAttribute("equity", equity);

		return "Report-BalanceSheet";
	}

	@GetMapping("Reports/BalanceSheet")
	public String BalanceSheet(Model model) {
		Date startDate = Functions.thisMonth(Functions.getCurrentDate());
		Date endDate = Functions.getCurrentDate();
		List<TrailBalance> TBList = TBService.DateBetween(startDate, endDate);
		List<Journal> JournalList = journalService.ByDateRange(startDate, endDate);
		// List<AccountGroup> JournalList = AGService.BalanceSheetReport(startDate,
		// endDate);
		List<Account_Payable> APList = APService.DateBetween(startDate, endDate);
		List<Account_Receivable> ARList = ARService.DateBetween(startDate, endDate);

		double netIncome = 0.0;
		HashMap<AccountGroup, Double> currentAsset = new HashMap<>();
		AGService.init();
		AccountGroup Asset = new AccountGroup(AGService.Asset);
		List<AccountGroup> bank = new ArrayList<>();
		Asset.getChildList().forEach(child -> {
			child.setAmount(0.0);
			child.setChildList(new ArrayList<>());
		});

		AccountGroup liability = new AccountGroup(AGService.liability);
		liability.getChildList().forEach(child -> {
			child.setAmount(0.0);
			child.setChildList(new ArrayList<>());
		});

		AccountGroup equity = new AccountGroup(AGService.equity);
		equity.getChildList().forEach(child -> {
			child.setAmount(0.0);
			child.setChildList(new ArrayList<>());
		});

		// ----------------------- Case 1 --------------------------------------
		// -------------- Trail balance entries and bank sources ------------
		for (TrailBalance TB : TBList) {
			if (TB.getType() == Constants.isExpense) {
				netIncome = netIncome - TB.getTotal();
				Double amount = currentAsset.getOrDefault(TB.getbankSourceID(), 0.0) - TB.getTotal();
				currentAsset.put(TB.getbankSourceID(), amount);
			}

			else {
				netIncome = netIncome + TB.getTotal();
				Double amount = currentAsset.getOrDefault(TB.getbankSourceID(), 0.0) + TB.getTotal();
				currentAsset.put(TB.getbankSourceID(), amount);
			}
		}

		// for (AccountGroup bankSource : currentAsset.keySet()) {
		// bankSource.setAmount(currentAsset.get(bankSource));
		//
		// Asset.getChildList().forEach(child -> {
		// if (child.getAccName().equalsIgnoreCase(Constants.CURRENT_ASSETS)) {
		// child.getChildList().add(bankSource);
		// child.setAmount(child.getAmount() + bankSource.getAmount());
		// Asset.setAmount(Asset.getAmount() + bankSource.getAmount());
		// }
		// });
		// }
		// ------------------------------------------------------------------------

		// ----------------------- Case 2 --------------------------------------
		// ----------------------- Account Payable ------------------

		Account_Payable payable = new Account_Payable();

		for (Account_Payable AP : APList) {
			if (!AP.getstatus().equals(Constants.CLOSED)) {
				payable.setTotal(payable.getTotal() + AP.getTotal());
			} else {
				Asset.getChildList().forEach(child -> {
					if (child.getAccName().equalsIgnoreCase(Constants.CURRENT_ASSETS)) {
						AP.getReciptList().forEach(reciept -> {
							if (reciept.getBankSourceID().equals(child)) {
								child.setAmount(child.getAmount() + reciept.getAmountPaid());
								Asset.setAmount(child.getAmount() + reciept.getAmountPaid());
							}
						});
					}
				});
			}

		}

		liability.getChildList().forEach(child -> {
			if (child.getAccName().equalsIgnoreCase(Constants.CURRENT_LIABILITY)) {
				child.setAmount(child.getAmount() + payable.getTotal());
				liability.setAmount(liability.getAmount() + payable.getTotal());
			}
		});

		// --------------------------------------------------

		// ----------------------- Case 3 --------------------------------------
		// ----------------------- Account receivable------------------

		Account_Receivable receivable = new Account_Receivable();

		for (Account_Receivable AP : ARList) {
			if (!AP.getstatus().equals(Constants.CLOSED)) {
				double amount = receivable.getTotal() + AP.getTotal();
				receivable.setTotal(amount);

			} else {
				Asset.getChildList().forEach(child -> {
					if (child.getAccName().equalsIgnoreCase(Constants.CURRENT_ASSETS)) {
						AP.getRecieptList().forEach(reciept -> {
							if (reciept.getBankSourceID().equals(child)) {
								child.setAmount(child.getAmount() + reciept.getAmountReceived());
								Asset.setAmount(child.getAmount() + reciept.getAmountReceived());
							}
						});
					}
				});
			}

		}

		Asset.getChildList().forEach(child -> {
			if (child.getAccName().equalsIgnoreCase(Constants.CURRENT_ASSETS)) {
				child.setAmount(child.getAmount() + receivable.getTotal());
				Asset.setAmount(Asset.getAmount() + receivable.getTotal());
			}
		});
		// --------------------------------------------------
		// ----------------------- Case 4 --------------------------------------
		// ----------------------- Journal------------------
		HashMap<AccountGroup, Double> map = currentAsset;

		for (Journal journal : JournalList) {

			for (JournalDetails JD : journal.getJDList()) {

				map.put(JD.getSubGroup_ID(), map.getOrDefault(JD.getSubGroup_ID(), 0.0) + JD.getSubTotal());
			}
		}
		for (AccountGroup AG : map.keySet()) {
			AccountGroup parent = new AccountGroup(AG);

			while (parent.getIsParent() != null) {
				parent = parent.getIsParent();
			}
			if (parent.getAccName().equals(Constants.ASSETS)) {

				Asset.getChildList().forEach(child -> {
					if (child.equals(AG.getIsParent())) {
						AG.setAmount(map.get(AG));
						child.getChildList().add(AG);
						child.setAmount(child.getAmount() + map.get(AG));
					}
				});
				Asset.setAmount(Asset.getAmount() + map.get(AG));
			}
			if (parent.getAccName().equals(Constants.LIABILITY)) {

				liability.getChildList().forEach(child -> {
					if (child.equals(AG.getIsParent())) {
						AG.setAmount(map.get(AG));
						child.getChildList().add(AG);
						child.setAmount(child.getAmount() + map.get(AG));
					}
				});
				liability.setAmount(liability.getAmount() + map.get(AG));
			}
			if (parent.getAccName().equals(Constants.EQUITY)) {

				equity.getChildList().forEach(child -> {
					if (child.equals(AG.getIsParent())) {
						AG.setAmount(map.get(AG));
						child.getChildList().add(AG);
						child.setAmount(child.getAmount() + map.get(AG));
					}
				});
				equity.setAmount(equity.getAmount() + map.get(AG));
			}
			if (parent.getAccName().equals(Constants.EXPENSE)) {
				netIncome -= map.get(AG);
			}
			if (parent.getAccName().equals(Constants.INCOME)) {
				netIncome += map.get(AG);
			}

		}

		// -------------------------------------------------

		// -------------- balance Profit and loss -------------
		netIncome += receivable.getTotal();
		netIncome -= payable.getTotal();
		equity.setAmount(equity.getAmount() + netIncome);
		// -----------------------------------------------

		Double totalEquity = equity.getAmount() + liability.getAmount();
		model.addAttribute("netIncome", netIncome);
		model.addAttribute("payable", payable);
		model.addAttribute("recievable", receivable);
		model.addAttribute("assets", Asset);
		model.addAttribute("liability", liability);
		model.addAttribute("equity", equity);
		model.addAttribute("totalEquity", totalEquity);
		return "Report-BalanceSheet";

	}

}
