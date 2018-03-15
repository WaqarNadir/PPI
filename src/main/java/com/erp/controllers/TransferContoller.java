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
public class TransferContoller {

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

	@GetMapping("ViewTransfers")
	public String ViewExpenseHome(Model model) {
		model.addAttribute("TransferList", getAllTransfers(Constants.isTransfer));
		return "ViewTransfers";
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

	public List<TrailBalance> getAllTransfers(int num) {
		List<TrailBalance> result = new ArrayList<>();
		populateTransferList(num);
		for (TrailBalance TB : TB_List) {
			result.add(TB);
		}
		return result;
	}

	public void populateTransferList(int num) {
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
		AG_List = AG_service.getWithParentRef(28);

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
