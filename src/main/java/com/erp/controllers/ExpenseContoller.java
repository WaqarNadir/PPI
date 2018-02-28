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
import org.springframework.web.bind.annotation.PostMapping;

import com.erp.classes.AccountGroup;
import com.erp.classes.ExpenseWrapper;
import com.erp.classes.PaymentDetails;
import com.erp.classes.Person;
import com.erp.classes.TB_Details;
import com.erp.classes.TrailBalance;
import com.erp.services.AccountGroupService;
import com.erp.services.PaymentDetailService;
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
	// ---- Variables -------------
	private List<AccountGroup> AG_List;

	@GetMapping("Expense/Add")
	public String ExpenseHome(Model model) {
		model.addAttribute("personList", getPerson());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("wrapper", new ExpenseWrapper());

		return "Expense";
	}

	@PostMapping("/Expense/Save")
	public String save(@ModelAttribute ExpenseWrapper data, Errors errors, HttpServletRequest request) {

		for (TB_Details TBD : data.getTB_DetailList()) {
			if (TBD.getSubTotal() != 0.0) {
				TBD.setTB_ID(data.getTrailBalance());
				TB_service.save(TBD.getTB_ID());
				TBD_Service.save(TBD);
				data.getPaymentDetail().setTB_ID(TBD.getTB_ID());
				PD_Service.save(data.getPaymentDetail());
			}
		}

		return "redirect:/Expense";
	}

	// ------------------ Utility functions ------------------------

	public List<Person> getPerson() {
		return personService.getAll();
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
