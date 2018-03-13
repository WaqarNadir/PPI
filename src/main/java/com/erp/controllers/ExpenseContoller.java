package com.erp.controllers;

import java.security.Provider.Service;
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

import com.erp.classes.AP_Details;
//import com.erp.classes.AR_Details;
import com.erp.classes.AccountGroup;
import com.erp.classes.Account_Payable;
import com.erp.classes.Constants;
import com.erp.classes.TrailBalanceWrapper;
import com.erp.classes.PaymentDetails;
import com.erp.classes.PaymentMethods;
import com.erp.classes.Person;
import com.erp.classes.ProfitLoss;
import com.erp.classes.TB_Details;
import com.erp.classes.TrailBalance;
import com.erp.services.AP_DetailsService;
//import com.erp.services.AR_DetailsService;
import com.erp.services.AccountGroupService;
import com.erp.services.Account_PayableService;
//import com.erp.services.Account_ReceivableService;
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
	@Autowired
	private Account_PayableService AP_Service;
	@Autowired
	private AP_DetailsService APD_Service;
	// @Autowired
	// private Account_ReceivableService AR_Service;
	// @Autowired
	// private AR_DetailsService ARD_Service;
	// ---- Variables -------------
	private List<AccountGroup> AG_List;

	@GetMapping("Expense/Add")
	public String ExpenseHome(Model model) {
		model.addAttribute("personList", getPerson());
		model.addAttribute("methodList", getMethods());
		model.addAttribute("AssetList", getCurrentAsset());
		// List<ProfitLoss> reportList = TB_service.profitLossReport();
		return "Expense";
	}

	@PostMapping("/Expense/Save")
	public String saveExpense(@ModelAttribute TrailBalanceWrapper data, Errors errors, HttpServletRequest request) {
		save(data, Constants.isExpense);
		return "redirect:/Expense/Add";
	}

	@GetMapping("Income/Add")
	public String IncomeHome(Model model) {
		model.addAttribute("personList", getPerson());
		model.addAttribute("methodList", getMethods());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("wrapper", new TrailBalanceWrapper());

		return "Income";
	}

	@PostMapping("/Income/Save")
	public String saveIncome(@ModelAttribute TrailBalanceWrapper data, Errors errors, HttpServletRequest request) {
		save(data, Constants.isIncome);
		return "redirect:/Income/Add";
	}

	@GetMapping("Transfer/Add")
	public String TransferHome(Model model) {
		model.addAttribute("methodList", getMethods());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("wrapper", new TrailBalanceWrapper());

		return "Transfer";
	}

	@PostMapping("/Transfer/Save")
	public String saveTransfer(@ModelAttribute TrailBalanceWrapper data, Errors errors, HttpServletRequest request) {
		save(data, Constants.isTransfer);
		return "redirect:/Transfer/Add";
	}

	@GetMapping("CreditNote/Add")
	public String CreditNoteHome(Model model) {
		model.addAttribute("personList", getPerson());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("wrapper", new TrailBalanceWrapper());

		return "CreditNote";
	}

	@PostMapping("/CreditNote/Save")
	public String saveCreditNote(@ModelAttribute TrailBalanceWrapper data, Errors errors, HttpServletRequest request) {
		// save(data, Constants.isTransfer);
		return "redirect:/Transfer/Add";
	}

	@GetMapping("Bill/Add")
	public String BillHome(Model model) {
		model.addAttribute("wrapper", new TrailBalanceWrapper());
		model.addAttribute("personList", getPerson());
		model.addAttribute("methodList", getMethods());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("ap", new Account_Payable());

		return "Bill";
	}

	@PostMapping("/Bill/Save")
	public String saveBillNote(@ModelAttribute TrailBalanceWrapper data, Errors errors, HttpServletRequest request) {
		savePayable(data, "Partial");
		return "redirect:/Bill/Add";
	}

	private void savePayable(TrailBalanceWrapper data, String status) {
		for (AP_Details APD : data.getAP_DetailList()) {
			if (APD.getAmountPayable() != 0.0) {
				data.getAccountPayable().setstatus(status);
				// data.getAccountPayable().setPerson_ID(data.get);
				APD.setAP_ID(data.getAccountPayable());
				AP_Service.save(APD.getAP_ID());
				APD_Service.save(APD);
			}
		}
	}

	@GetMapping("ReceiveBill/Add")
	public String ReceiveBillHome(Model model) {
		model.addAttribute("wrapper", new TrailBalanceWrapper());
		model.addAttribute("personList", getPerson());
		model.addAttribute("methodList", getMethods());
		model.addAttribute("AssetList", getCurrentAsset());
		model.addAttribute("ap", new Account_Payable());

		return "ReceiveBill";
	}

	@PostMapping("/ReceiveBill/Save")
	public String saveReceiveBillNote(@ModelAttribute TrailBalanceWrapper data, Errors errors,
			HttpServletRequest request) {
		// saveReceivable(data, "Partial");
		return "redirect:/ReceiveBill/Add";
	}

	// private void saveReceivable(TrailBalanceWrapper data, String status) {
	// for (AR_Details ARD : data.getAR_DetailsList()) {
	// if (ARD.getAmount_Received() != 0.0) {
	// data.getAccountReceivable().setstatus(status);
	// // data.getAccountReceivable().setstatus(status);
	// ARD.setReceived_Date(data.getAccountReceivable().getDate());
	// // data.getAccountPayable().setPerson_ID(data.get);
	// ARD.setAR_ID(data.getAccountReceivable());
	// AR_Service.save(ARD.getAR_ID());
	// ARD_Service.save(ARD);
	// }
	// }
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
