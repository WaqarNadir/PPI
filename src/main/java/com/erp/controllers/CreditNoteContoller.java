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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.erp.classes.ARReciept;
import com.erp.classes.AR_Details;
import com.erp.classes.AccountGroup;
import com.erp.classes.Account_Receivable;
import com.erp.classes.Constants;
import com.erp.classes.Functions;
import com.erp.classes.PaymentMethods;
import com.erp.classes.Person;
import com.erp.services.ARRecieptService;
import com.erp.services.AR_DetailsService;
import com.erp.services.AccountGroupService;
import com.erp.services.Account_ReceivableService;
import com.erp.services.PaymentMethodService;
import com.erp.services.PersonService;

@Controller
public class CreditNoteContoller {

	@Autowired
	private PersonService personService;
	@Autowired
	private AccountGroupService AG_service;
	@Autowired
	private PaymentMethodService PM_Service;
	@Autowired
	private Account_ReceivableService AR_Service;
	@Autowired
	private AR_DetailsService ARD_Service;
	@Autowired
	private ARRecieptService ARRecieptService;
	// ---- Variables -------------
	private List<Account_Receivable> AR_List;
	private List<AccountGroup> AG_List;
	AccountGroup income = null;
	AccountGroup currentAsset = null;

	@GetMapping("CreditNote/Add")
	public String CreditNoteHome(Model model) {
		Account_Receivable AR = new Account_Receivable();
		Date cDate = new Date(System.currentTimeMillis());
		AR.setDate(cDate);
		Date dueDate = Functions.addDays(30, cDate);
		AR.setDue_Date(dueDate);	
		income = AG_service.findByName(Constants.INCOME);
		currentAsset = AG_service.findByName(Constants.CURRENT_ASSETS);
		model.addAttribute("personList", getPerson());
		model.addAttribute("AccountRecievable", AR);
		model.addAttribute("income", income);
		model.addAttribute("currentAsset", currentAsset);

		return "CreditNote";
	}

	@PostMapping("/CreditNote/Save")
	public String saveCreditNote(@ModelAttribute Account_Receivable data, Errors errors, HttpServletRequest request,
			Model model) {
		saveReceivable(data, Constants.OPEN);
		UpdateParent(data.getTotal());
		return "redirect:/CreditReceipt/" + data.getAR_ID();
	}

	private Boolean UpdateParent(double creditAmount) {
		boolean result = false;
		AccountGroup item = AG_service.findByName(Constants.ACCOUNT_RECIEVABLE);
		try {
			while (item.getIsParent() != null) {
				double amount = 0.0;
				amount = creditAmount + item.getAmount();
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

	@GetMapping(value = "/CreditReceipt/{creditID}")
	public String ReceiveCreditNoteHome(@PathVariable("creditID") int creditID, Model model) {

		Account_Receivable AR = AR_Service.find(creditID);
		ARReciept ARR = new ARReciept();
		ARR.setAR_ID(AR);

		model.addAttribute("methodList", getMethods());
		model.addAttribute("currentAsset", AG_service.findByName(Constants.CURRENT_ASSETS));
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("arReciept", ARR);
		// model.addAttribute("wrapper", APR);
		return "CreditReceipt";
	}

	@PostMapping("/CreditReceipt/Save")
	public String saveBillReieptNote(@ModelAttribute ARReciept data, Errors errors, HttpServletRequest request,
			Model model) {
		saveCredit(data);
		// model.addAttribute("wrapper", data);
		model.addAttribute("AccountRecievable", new Account_Receivable());
		return "redirect:/CreditNote/Add";
	}

	@GetMapping("ViewCredits")
	public String ViewExpenseHome(Model model) {
		model.addAttribute("CreditList", getAllCredits());
		return "ViewCredits";
	}
	
	
	@GetMapping("Credit/CustomCredit")
	public String CustomBills(Model model) {
		double incomeSum = 0;
		double creditSum = 0;
		Date currentDate = Functions.getCurrentDate();
		Date lastMonth = Functions.thisMonth(currentDate);

		System.out.println(
				"This Month: " + Functions.thisMonth(currentDate) + "\n this Year: " + Functions.thisYear(currentDate));
		System.out.println("current Date: " + currentDate + "\n Last Month: " + lastMonth);

		List<Account_Receivable> cBalance = AR_Service.ByDateRange(lastMonth, currentDate);
		for (Account_Receivable CB : cBalance) {

			
			creditSum += CB.getTotal();
			

		}
		model.addAttribute("profitLossList", cBalance);
		model.addAttribute("expenseSum", creditSum);
		model.addAttribute("netEquity", (incomeSum + creditSum));
		return "CustomCredit";
	}
	
	@PostMapping("Credit/DateWiseCredit")
	public String dateWiseBill(HttpServletRequest request, Model model) {
		double creditSum = 0;
		Date startDate = null, endDate = null;
		List<Account_Receivable> aRList = null;
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
		aRList = AR_Service.ByDateRange(startDate, endDate);
		for (Account_Receivable AR : aRList) {
				creditSum += AR.getTotal();
		}
		msg = "From " + startDate + " to " + endDate;
		model.addAttribute("label", msg);
		model.addAttribute("selectedValue", value);
		model.addAttribute("aRList", aRList);
		model.addAttribute("creditSum", creditSum);
		return "CustomCredit";
	}
	
	

	// ------------------ Utility functions ------------------------

	public List<Account_Receivable> getAllCredits() {
		List<Account_Receivable> result = new ArrayList<>();
		populateCreditsList();
		for (Account_Receivable AR : AR_List) {
			result.add(AR);
		}
		return result;
	}

	public void populateCreditsList() {
		AR_List = new ArrayList<>();
		AR_List = AR_Service.getAll();
	}

	private void saveReceivable(Account_Receivable data, String status) {
		List<AR_Details> ArList = data.getAR_DetailList();
		// System.out.println(ArList.size());
		data.setstatus(status);
		AR_Service.save(data);
		for (AR_Details ARDetails : ArList) {
			AR_Details ARD = new AR_Details(ARDetails);
			if (ARD.getAmountReceived() != 0.0) {
				ARD.setReceivedDate(data.getDate());
				// data.getAccountPayable().setPerson_ID(data.get);
				ARD.setAR_ID(data);
				ARD_Service.save(ARD);
			}
		}
	}

	private void saveCredit(ARReciept arReciept) {
		ARRecieptService.save(arReciept);
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
}
