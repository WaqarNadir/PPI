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

import com.erp.classes.AccountGroup;
import com.erp.classes.Constants;
import com.erp.classes.Functions;
import com.erp.classes.PaymentMethods;
import com.erp.classes.Person;
import com.erp.classes.TB_Details;
import com.erp.classes.TrailBalance;
import com.erp.classes.TrailBalanceWrapper;
import com.erp.services.AccountGroupService;
import com.erp.services.PaymentDetailService;
import com.erp.services.PaymentMethodService;
import com.erp.services.PersonService;
import com.erp.services.TB_DetailService;
import com.erp.services.TrailBalanceService;

@Controller
public class IncomeContoller {

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

	private List<TrailBalance> TB_List;
	private List<AccountGroup> AG_List;
	TrailBalanceWrapper wrapper = null;
	AccountGroup income = null;

	@GetMapping("Income/Add")
	public String IncomeHome(Model model) {
		income = AG_service.findByName(Constants.INCOME);
		model.addAttribute("personList", getPerson());
		model.addAttribute("methodList", getMethods());
		model.addAttribute("currentAsset", AG_service.findByName(Constants.CURRENT_ASSETS));
		model.addAttribute("income", income);
		model.addAttribute("wrapper", new TrailBalanceWrapper());

		return "Income";
	}

	@PostMapping("/Income/Save")
	public String saveIncome(@ModelAttribute TrailBalanceWrapper data, Errors errors, HttpServletRequest request) {
		save(data, Constants.isIncome);
		updateParent(data.getTrailBalance().getTotal());
		updateBankSource(data.getTrailBalance().getbankSourceID(), data.getTrailBalance().getTotal());

		return "redirect:/Income/Add";
	}

	private Boolean updateParent(double BillAmount) {
		boolean result = false;
		AccountGroup item = income;
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
				amount = bankSource.getAmount() + BillAmount;
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

	@GetMapping("ViewIncomes")
	public String ViewExpenseHome(Model model) {
		model.addAttribute("IncomesList", getAllIncomes(Constants.isIncome));
		return "ViewIncomes";
	}

	// @PostMapping("/getSubTypeIncome")
	// public @ResponseBody List<String[]> getSubType(@RequestBody String data) {
	// int Id = Integer.parseInt(data);
	// List<String[]> resultList = new ArrayList<>();
	// List<AccountGroup> AGList = AG_service.getWithParentRef(Id);
	//
	// for (AccountGroup AG : AGList) {
	// String[] result = new String[4];
	// result[1] = AG.getAccName();
	// result[0] = AG.getAcc_ID() + "";
	// resultList.add(result);
	// }
	// return resultList;
	// }

	// ------------------ Utility functions ------------------------

	private void save(TrailBalanceWrapper data, int type) {
		for (TB_Details TBD : data.getTB_DetailList()) {
			if (TBD.getSubTotal() != 0.0) {
				TBD.setTB_ID(data.getTrailBalance());

				TBD.getTB_ID().setType(type);
				// TBD.getTB_ID().setDiaryNo(data.getTrailBalance().getDiaryNo());

				TB_service.save(TBD.getTB_ID());
				TBD_Service.save(TBD);
				data.getPaymentDetail().setTB_ID(TBD.getTB_ID());
				PD_Service.save(data.getPaymentDetail());
			}
		}

	}

	public List<TrailBalance> getAllIncomes(int num) {
		List<TrailBalance> result = new ArrayList<>();
		populateIncomeList(num);
		for (TrailBalance TB : TB_List) {
			result.add(TB);
		}
		return result;
	}

	public void populateIncomeList(int num) {
		TB_List = new ArrayList<>();
		TB_List = TB_service.findByType(num);
	}

	public List<Person> getPerson() {
		return personService.getAll();
	}

	public List<PaymentMethods> getMethods() {
		return PM_Service.getAll();
	}

	public List<AccountGroup> getCurrentAsset() {
		List<AccountGroup> result = new ArrayList<>();
		populateAccountGroupList();
		for (AccountGroup AG : AG_List) {
			result.add(AG);
		}
		return result;
	}

	public void populateAccountGroupList() {
		AG_List = new ArrayList<>();
		AG_List = AG_service.getAll();

	}

	public AccountGroup getAccountGroup(int ID) {
		for (AccountGroup val : AG_List) {
			if (val.getAcc_ID() == ID) {
				return val;
			}
		}
		return null;
	}
	
	
	
	@GetMapping("Income/CustomIncomes")
	public String CustomIncomes(Model model) {
		double incomeSum = 0;
		Date currentDate = Functions.getCurrentDate();
		Date lastMonth = Functions.thisMonth(currentDate);

		System.out.println(
				"This Month: " + Functions.thisMonth(currentDate) + "\n this Year: " + Functions.thisYear(currentDate));
		System.out.println("current Date: " + currentDate + "\n Last Month: " + lastMonth);

		List<TrailBalance> tBalance = TB_service.ByDateRange(lastMonth, currentDate, Constants.isIncome);
		for (TrailBalance TB : tBalance) {

			if (TB.getType() == Constants.isIncome) {
				incomeSum += TB.getTotal();
			}

		}
		model.addAttribute("tBalance", tBalance);
		model.addAttribute("incomeSum", incomeSum);
		//model.addAttribute("netEquity", (incomeSum + expenseSum));
		return "CustomIncomes";
	}

	@PostMapping("Income/DateWiseIncome")
	public String dateWiseIncome(HttpServletRequest request, Model model) {
		double incomeSum = 0;
		Date startDate = null, endDate = null;
		List<TrailBalance> tBIncomeList = null;
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
		tBIncomeList = TB_service.ByDateRange(startDate, endDate, Constants.isIncome);
		for (TrailBalance TB : tBIncomeList) {
			if (TB.getType() == Constants.isIncome) {
					incomeSum += TB.getTotal();			
			}
			

		}
		msg = "From " + startDate + " to " + endDate;
		model.addAttribute("label", msg);
		model.addAttribute("selectedValue", value);
		model.addAttribute("tBIncomeList", tBIncomeList);
		model.addAttribute("incomeSum", incomeSum);
		return "CustomIncomes";
	}
	
	
}
