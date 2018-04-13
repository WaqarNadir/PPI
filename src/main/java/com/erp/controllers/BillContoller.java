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
		savePayable(data, Constants.PARTIAL);
		UpdateParent(data.getTotal());
		return "redirect:/BillReceipt/" + data.getAP_ID();
	}
	
	@GetMapping(value = "/BillReceipt/{billID}")
	public String BillPaymentHome(@PathVariable("billID") int billID, Model model) {
		Account_Payable AP = AP_Service.find(billID);
		APReciept APR = new APReciept();
		APR.setAP_ID(AP);
		model.addAttribute("currentAsset", AG_service.findByName(Constants.CURRENT_ASSETS));
		model.addAttribute("methodList", getMethods());
		model.addAttribute("apReciept", APR);
		return "BillReceipt";
	}

	@PostMapping("/BillReciept/Save")
	public String saveBillReieptNote(@ModelAttribute APReciept data, Errors errors, HttpServletRequest request,
			Model model) {
		saveBill(data);
		model.addAttribute("AccountPayable", new Account_Payable());
		return "redirect:/Bill/Add";
	}
	
	@GetMapping("ViewBills")
	public String ViewExpenseHome(Model model) {
		model.addAttribute("BillsList", getAllBills());
		return "ViewBills";
	}


	
//------------------------------ Utility functions ----------------------------------
	
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
	
	private Boolean updatePaidParent(double paidAmount) {
		boolean result = false;
		AccountGroup item = AG_service.findByName(Constants.ACCOUNT_PAYABLE);
		try {
			while (item.getIsParent() != null) {
				double amount = 0.0;
				amount = item.getAmount() - paidAmount;
				System.out.println("Subtracting " + paidAmount + " from " + item.getAccName() + " having balance: "
						+ item.getAmount());
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
