package com.erp.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.classes.AccountGroup;
import com.erp.classes.Constants;
import com.erp.classes.Functions;
import com.erp.classes.PaymentMethods;
import com.erp.classes.Person;
import com.erp.classes.TB_Details;
import com.erp.classes.TrailBalance;
import com.erp.services.AccountGroupService;
import com.erp.services.PaymentDetailService;
import com.erp.services.PaymentMethodService;
import com.erp.services.PersonService;
import com.erp.services.TB_DetailService;
import com.erp.services.TrailBalanceService;

@Controller
public class ExpenseContoller {

	@Autowired
	private PersonService personService;
	@Autowired
	private AccountGroupService AG_service;
	@Autowired
	private TB_DetailService TBD_Service;
	@Autowired
	private PaymentDetailService PD_Service;
	@Autowired
	private TrailBalanceService TB_service;
	@Autowired
	private PaymentMethodService PM_Service;
	// ---- Variables -------------
	private List<AccountGroup> AG_List;
	private List<TrailBalance> TB_List;
	AccountGroup expense = null;

	@GetMapping("Expense/Add")
	public String ExpenseHome(Model model) {
		expense = new AccountGroup();
		expense = AG_service.findByName(Constants.EXPENSE);
		expense.getChildList();
		TrailBalance TB = new TrailBalance();

		TB.setDate(new Date(System.currentTimeMillis()));
		model.addAttribute("personList", getPerson());
		model.addAttribute("methodList", getMethods());
		model.addAttribute("currentAsset", AG_service.findByName(Constants.CURRENT_ASSETS));
		model.addAttribute("expense", expense);
		model.addAttribute("TrailBalance", TB);

		return "Expense";
	}

	@PostMapping("/Expense/Save")
	public String saveExpense(@ModelAttribute TrailBalance data, Errors errors, HttpServletRequest request) {
		save(data, Constants.isExpense);
		updateParent(data.getTotal());
		updateBankSource(data.getbankSourceID(), data.getTotal());
		return "redirect:/Expense/Add";
	}

	private Boolean updateParent(double BillAmount) {
		boolean result = false;
		AccountGroup item = expense;
		try {
			while (item.getIsParent() != null) {
				double amount = 0.0;
				amount = BillAmount + item.getAmount();
				item.setAmount(amount);
				AG_service.save(item);
				item = item.getIsParent();
			}

			result = true;
		} catch (Exception e) {
			result = false;
			System.err.println("=> Error while update Account group Parent: " + e.getMessage());
		}
		return result;

	}

	private Boolean updateBankSource(AccountGroup bankSource, double BillAmount) {
		boolean result = false;
		try {
			while (bankSource.getIsParent() != null) {
				double amount = 0.0;
				amount = bankSource.getAmount() - BillAmount;
				bankSource.setAmount(amount);
				AG_service.save(bankSource);
				bankSource = bankSource.getIsParent();
			}

			result = true;
		} catch (Exception e) {
			result = false;
			System.err.println("=> Error while update bank source Parent: " + e.getMessage());
		}
		return result;

	}

	@GetMapping("ViewExpenses")
	public String ViewExpenseHome(Model model) {
		model.addAttribute("ExpenseList", getAllExpenses(Constants.isExpense));
		return "ViewExpenses";
	}
	// ------------------ Utility functions ------------------------

	private void save(TrailBalance data, int type) {
		data.setType(type);
		TB_service.save(data);
		for (TB_Details TBD : data.getTB_DetailList()) {
			if (TBD.getSubTotal() != 0.0) {
				TBD.setTB_ID(data);
				TBD_Service.save(TBD);
			}
		}
		data.getPaymentDetail().get(0).setTB_ID(data);
		PD_Service.save(data.getPaymentDetail().get(0));

	}

	public List<Person> getPerson() {
		return personService.getAll();
	}

	public List<PaymentMethods> getMethods() {
		return PM_Service.getAll();
	}

	public List<TrailBalance> getAllExpenses(int num) {
		List<TrailBalance> result = new ArrayList<>();
		populateExpenseList(num);
		for (TrailBalance TB : TB_List) {
			result.add(TB);
		}
		return result;
	}

	public void populateExpenseList(int num) {
		TB_List = new ArrayList<>();
		TB_List = TB_service.findByType(num);
	}

	// public List<AccountGroup> getCurrentAsset() {
	// List<AccountGroup> result = new ArrayList<>();
	// populateAccountGroupList();
	// for (AccountGroup AG : AG_List) {
	// result.add(AG);
	// }
	// return result;
	// }
	//
	// public void populateAccountGroupList() {
	// AG_List = new ArrayList<>();
	// AG_List = AG_service.getWithParentRef(142);
	//
	// }

	@PostMapping("/getSubType")
	public @ResponseBody List<String[]> getSubType(@RequestBody String data) {
		int Id = Integer.parseInt(data);
		List<String[]> resultList = new ArrayList<>();
		// List<AccountGroup> AGList = AG_service.getWithParentRef(Id);
		List<AccountGroup> AGList = AG_service.find(Id).getChildList();

		for (AccountGroup AG : AGList) {
			String[] result = new String[4];
			result[1] = AG.getAccName();
			result[0] = AG.getAcc_ID() + "";
			resultList.add(result);
		}
		return resultList;
	}

	@GetMapping("Expense/CustomBills")
	public String CustomBills(Model model) {
		double incomeSum = 0;
		double expenseSum = 0;
		Date currentDate = Functions.getCurrentDate();
		Date lastMonth = Functions.thisMonth(currentDate);

		System.out.println(
				"This Month: " + Functions.thisMonth(currentDate) + "\n this Year: " + Functions.thisYear(currentDate));
		System.out.println("current Date: " + currentDate + "\n Last Month: " + lastMonth);

		List<TrailBalance> tBalance = TB_service.ByDateRange(lastMonth, currentDate, Constants.isExpense);
		for (TrailBalance TB : tBalance) {

			if (TB.getType() == Constants.isExpense) {
				expenseSum += TB.getTotal();
			}

		}
		model.addAttribute("profitLossList", tBalance);
		model.addAttribute("expenseSum", expenseSum);
		model.addAttribute("netEquity", (incomeSum + expenseSum));
		return "CustomBills";
	}

	@PostMapping("Expense/DateWiseExpense")
	public String dateWiseExpense(HttpServletRequest request, Model model) {
		double expenseSum = 0;
		Date startDate = null, endDate = null;
		List<TrailBalance> tBExpenseList = null;
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
		tBExpenseList = TB_service.ByDateRange(startDate, endDate, Constants.isExpense);
		for (TrailBalance TB : tBExpenseList) {
			if (TB.getType() == Constants.isExpense) {
				expenseSum += TB.getTotal();
			}

		}
		msg = "From " + startDate + " to " + endDate;
		model.addAttribute("label", msg);
		model.addAttribute("selectedValue", value);
		model.addAttribute("tBExpenseList", tBExpenseList);
		model.addAttribute("expenseSum", expenseSum);
		return "CustomBills";
	}

	public AccountGroup getAccountGroup(int ID) {
		for (AccountGroup val : AG_List) {
			if (val.getAcc_ID() == ID) {
				return val;
			}
		}
		return null;
	}
}
