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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.classes.AccountGroup;
import com.erp.classes.Constants;
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
	TrailBalanceWrapper wrapper = null;

	@GetMapping("Expense/Add")
	public String ExpenseHome(Model model) {
		model.addAttribute("personList", getPerson());
		model.addAttribute("methodList", getMethods());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("wrapper", new TrailBalanceWrapper());

		return "Expense";
	}

	@PostMapping("/Expense/Save")
	public String saveExpense(@ModelAttribute TrailBalanceWrapper data, Errors errors, HttpServletRequest request) {
		save(data, Constants.isExpense);
		return "redirect:/Expense/Add";
	}

	@GetMapping("ViewExpenses")
	public String ViewExpenseHome(Model model) {
		model.addAttribute("ExpenseList", getAllExpenses(Constants.isExpense));
		return "ViewExpenses";
	}
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
		AG_List = AG_service.getWithParentRef(142);

	}

	@PostMapping("/getSubType")
	public @ResponseBody List<String[]> getSubType(@RequestBody String data) {
		int Id = Integer.parseInt(data);
		List<String[]> resultList = new ArrayList<>();
		//List<AccountGroup> AGList = AG_service.getWithParentRef(Id);
		List<AccountGroup> AGList = AG_service.find(Id).getChildList();

		for (AccountGroup AG : AGList) {
			String[] result = new String[4];
			result[1] = AG.getAccName();
			result[0] = AG.getAcc_ID() + "";
			resultList.add(result);
		}
		return resultList;
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
