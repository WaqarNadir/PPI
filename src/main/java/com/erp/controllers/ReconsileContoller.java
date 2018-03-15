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
import com.erp.classes.PaymentMethods;
import com.erp.classes.Person;
import com.erp.classes.TrailBalanceWrapper;
import com.erp.services.AccountGroupService;
import com.erp.services.PaymentMethodService;
import com.erp.services.PersonService;

@Controller
public class ReconsileContoller {

	@Autowired
	private PersonService personService;
	@Autowired
	private AccountGroupService AG_service;
	@Autowired
	private PaymentMethodService PM_Service;

	// ---- Variables -------------
	private List<AccountGroup> AG_List;
	TrailBalanceWrapper wrapper = null;

	@GetMapping("Reconsile")
	public String ReconsileHome(Model model) {
		model.addAttribute("personList", getPerson());
		model.addAttribute("methodList", getMethods());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("wrapper", new TrailBalanceWrapper());

		return "Reconsile";
	}
	@PostMapping("/Reconsile/Save")
	public String saveExpense(@ModelAttribute TrailBalanceWrapper data, Errors errors, HttpServletRequest request) {
		//save(data, Constants.isExpense);
		return "redirect:/Reconsile";
	}
	// ------------------ Utility functions ------------------------

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
