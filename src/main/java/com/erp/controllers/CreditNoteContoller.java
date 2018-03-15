package com.erp.controllers;

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

import com.erp.classes.ARReciept;
import com.erp.classes.AR_Details;
import com.erp.classes.AccountGroup;
import com.erp.classes.Account_Receivable;
import com.erp.classes.PaymentMethods;
import com.erp.classes.Person;
import com.erp.classes.TrailBalanceWrapper;
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
	TrailBalanceWrapper wrapper = null;

	@GetMapping("CreditNote/Add")
	public String CreditNoteHome(Model model) {
		wrapper = new TrailBalanceWrapper();
		model.addAttribute("wrapper", wrapper);
		model.addAttribute("personList", getPerson());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("AccountRecievable", new Account_Receivable());

		return "CreditNote";
	}

	@PostMapping("/CreditNote/Save")
	public String saveCreditNote(@ModelAttribute Account_Receivable data, Errors errors, HttpServletRequest request,
			Model model) {
		saveReceivable(data, "Open");
		// return ReceiveCreditNoteHome(model);
		return "redirect:/CreditReceipt/" + data.getAR_ID();
	}

	/*
	 * @GetMapping("CreditReceipt/Add") public String ReceiveCreditNoteHome(Model
	 * model) { model.addAttribute("personList", getPerson());
	 * model.addAttribute("AssetList", getCurrentAsset());
	 * //model.addAttribute("wrapper", new TrailBalanceWrapper());
	 * model.addAttribute("arReciept", new ARReciept()); return "CreditReceipt"; }
	 */
	@GetMapping(value = "/CreditReceipt/{creditID}")
	public String ReceiveCreditNoteHome(@PathVariable("creditID") int creditID, Model model) {

		Account_Receivable AR = AR_Service.find(creditID);
		ARReciept ARR = new ARReciept();
		ARR.setAR_ID(AR);

		model.addAttribute("methodList", getMethods());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("arReciept", ARR);
		// model.addAttribute("wrapper", APR);
		return "creditReceipt";
	}

	@PostMapping("/CreditReceipt/Save")
	public String saveBillReieptNote(@ModelAttribute ARReciept data, Errors errors, HttpServletRequest request,
			Model model) {
		saveCredit(data);
		// model.addAttribute("wrapper", data);
		model.addAttribute("AccountRecievable", new Account_Receivable());
		return "CreditNote";
	}

	@GetMapping("ViewCredits")
	public String ViewExpenseHome(Model model) {
		model.addAttribute("CreditList", getAllCredits());
		return "ViewCredits";
	}

	@PostMapping("/getSubTypeCreditNote")
	public @ResponseBody List<String[]> getSubType(@RequestBody String data) {
		int Id = Integer.parseInt(data);
		List<String[]> resultList = new ArrayList<>();
		List<AccountGroup> AGList = AG_service.getWithParentRef(Id);

		for (AccountGroup AG : AGList) {
			String[] result = new String[4];
			result[1] = AG.getAccName();
			result[0] = AG.getAcc_ID() + "";
			resultList.add(result);
		}
		return resultList;
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
			if (ARD.getAmount_Received() != 0.0) {
				ARD.setReceived_Date(data.getDate());
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
		AG_List = AG_service.getWithParentRef(50);

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
