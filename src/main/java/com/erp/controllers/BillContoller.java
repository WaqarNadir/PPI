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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.classes.APReciept;
import com.erp.classes.AP_Details;
import com.erp.classes.AccountGroup;
import com.erp.classes.Account_Payable;
import com.erp.classes.Constants;
import com.erp.classes.Functions;
import com.erp.classes.PaymentMethods;
import com.erp.classes.Person;
import com.erp.services.APRecieptService;
import com.erp.services.AP_DetailsService;
import com.erp.services.AccountGroupService;
import com.erp.services.Account_PayableService;
import com.erp.services.PaymentMethodService;
import com.erp.services.PersonService;

@Controller
public class BillContoller {

	@Autowired
	private PersonService personService;
	@Autowired
	private AccountGroupService AG_service;

	@Autowired
	private PaymentMethodService PM_Service;
	@Autowired
	private Account_PayableService AP_Service;
	@Autowired
	private AP_DetailsService APD_Service;
	@Autowired
	private APRecieptService APRecieptService;
	// ---- Variables -------------
	private List<Account_Payable> AP_List;
	Double remaining = 0.0;

	// ----------------------- Bill ---------------------------------------------
	@GetMapping("Bill/Add")
	public String BillHome(Model model) {
		Account_Payable AP = new Account_Payable();
		Date cDate = new Date(System.currentTimeMillis());
		AP.setDate(cDate);
		Date dueDate = Functions.addDays(30, cDate);
		AP.setDueDate(dueDate);

		model.addAttribute("personList", getPerson());
		model.addAttribute("methodList", getMethods());
		model.addAttribute("expense", AG_service.findByName(Constants.EXPENSE));
		model.addAttribute("currentLiability", AG_service.findByName(Constants.CURRENT_LIABILITY));
		model.addAttribute("AccountPayable", AP);
		return "Bill";
	}

	@PostMapping("/Bill/Save")
	public String saveBillNote(@ModelAttribute Account_Payable data, Errors errors, HttpServletRequest request,
			Model model) {
		savePayable(data, Constants.OPEN);
		UpdateParent(data.getTotal());
		return "redirect:/BillReceipt/" + data.getAP_ID();
	}

	// --------------------------- Bill Reciept-------------------------------------

	@GetMapping(value = "/BillReceipt/{billID}")
	public String BillPaymentHome(@PathVariable("billID") int billID, Model model) {
		Account_Payable AP = AP_Service.find(billID);
		APReciept APR = new APReciept();
		APR.setAP_ID(AP);

		APR.setDate(Functions.getCurrentDate());
		model.addAttribute("currentAsset", AG_service.findByName(Constants.CURRENT_ASSETS));
		model.addAttribute("methodList", getMethods());
		model.addAttribute("apReciept", APR);
		remaining = AP.getTotal() - APRecieptService.SumTotal(AP);
		model.addAttribute("remaining", remaining);

		return "BillReceipt";
	}

	@PostMapping("/BillReciept/Save")
	public String saveBillReieptNote(@ModelAttribute APReciept data, Errors errors, HttpServletRequest request,
			Model model) {
		Double totalPaid = remaining - data.getAmountPaid();
		if (totalPaid == 0) { // amounts are equal
			data.getAP_ID().setstatus(Constants.CLOSED);
		} else {
			data.getAP_ID().setstatus(Constants.PARTIAL);
		}
		saveBill(data);
		model.addAttribute("AccountPayable", new Account_Payable());
		return "redirect:/ViewBills";
	}

	// --------------- View Bills --------------------------

	@GetMapping("ViewBills")
	public String ViewExpenseHome(Model model) {
		model.addAttribute("BillsList", getAllBills());
		return "ViewBills";
	}

	@GetMapping("Bill/CustomBills")
	public String CustomBills(Model model) {
		double incomeSum = 0;
		double expenseSum = 0;
		Date currentDate = Functions.getCurrentDate();
		Date lastMonth = Functions.thisMonth(currentDate);

		System.out.println(
				"This Month: " + Functions.thisMonth(currentDate) + "\n this Year: " + Functions.thisYear(currentDate));
		System.out.println("current Date: " + currentDate + "\n Last Month: " + lastMonth);

		List<Account_Payable> bBalance = AP_Service.ByDateRange(lastMonth, currentDate);
		for (Account_Payable BB : bBalance) {

			expenseSum += BB.getTotal();

		}
		model.addAttribute("profitLossList", bBalance);
		model.addAttribute("expenseSum", expenseSum);
		model.addAttribute("netEquity", (incomeSum + expenseSum));
		return "CustomBills";
	}

	@PostMapping("Bill/DateWiseBill")
	public String dateWiseBill(HttpServletRequest request, Model model) {
		double billSum = 0;
		Date startDate = null, endDate = null;
		List<Account_Payable> aPayableList = null;
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
		aPayableList = AP_Service.ByDateRange(startDate, endDate);
		for (Account_Payable AP : aPayableList) {
			billSum += AP.getTotal();
		}
		msg = "From " + startDate + " to " + endDate;
		model.addAttribute("label", msg);
		model.addAttribute("selectedValue", value);
		model.addAttribute("aPayableList", aPayableList);
		model.addAttribute("billSum", billSum);
		return "CustomBills";
	}

	// ------------------------------ Utility functions
	// ----------------------------------

	private Boolean UpdateParent(double BillAmount) {
		boolean result = false;
		AccountGroup item = AG_service.findByName(Constants.ACCOUNT_PAYABLE);
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

	// private Boolean updatePaidParent(double paidAmount) {
	// boolean result = false;
	// AccountGroup item = AG_service.findByName(Constants.ACCOUNT_PAYABLE);
	// try {
	// while (item.getIsParent() != null) {
	// double amount = 0.0;
	// amount = item.getAmount() - paidAmount;
	// System.out.println("Subtracting " + paidAmount + " from " + item.getAccName()
	// + " having balance: "
	// + item.getAmount());
	// item.setAmount(amount);
	// AG_service.save(item);
	// item = item.getIsParent();
	// }
	// result = true;
	// } catch (Exception e) {
	// result = false;
	// System.err.println("=> Error while update Account group Parent: " +
	// e.getMessage());
	// }
	// return result;
	// }

	@PostMapping("/getBalance")
	public @ResponseBody String getBalance(@RequestBody String data) {
		int Id = Integer.parseInt(data);
		AccountGroup AG = AG_service.find(Id);
		return AG.getAmount().toString();
	}

	public List<Account_Payable> getAllBills() {
		List<Account_Payable> result = new ArrayList<>();
		populateBillsList();
		for (Account_Payable AP : AP_List) {
			result.add(AP);
		}
		return result;
	}

	public void populateBillsList() {
		AP_List = new ArrayList<>();
		AP_List = AP_Service.getAll();
	}

	private void saveBill(APReciept apReciept) {
		APRecieptService.save(apReciept);
		updateBankSource(apReciept.getBankSourceID(), apReciept.getAmountPaid());
	}

	private void savePayable(Account_Payable data, String status) {
		List<AP_Details> ApList = data.getAP_DetailList();
		// System.out.println(ApList.size());
		data.setstatus(status);
		AP_Service.save(data);
		for (AP_Details APDetails : ApList) {
			AP_Details APD = new AP_Details(APDetails);
			if (APD.getAmount_Paid() != 0.0) {
				APD.setPaidDate(data.getDate());
				// data.getAccountPayable().setPerson_ID(data.get);
				APD.setAP_ID(data);
				APD_Service.save(APD);
			}
		}
	}

	public List<Person> getPerson() {
		return personService.getAll();
	}

	public List<PaymentMethods> getMethods() {
		return PM_Service.getAll();
	}

}
