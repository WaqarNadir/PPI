package com.erp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.erp.classes.AccountGroup;
import com.erp.classes.Functions;
import com.erp.classes.Journal;
import com.erp.classes.JournalDetails;
import com.erp.services.AccountGroupService;
import com.erp.services.JournalDetailService;
import com.erp.services.JournalService;

@Controller
public class JournalController {

	@Autowired
	private JournalService service;
	@Autowired
	private JournalDetailService JDservice;
	@Autowired
	private AccountGroupService AG_service;

	// ---- Variables -------------
	private List<AccountGroup> AG_List;

	@GetMapping(value = "Journal/Add")
	public String ViewPage(Model model) {
		Journal journal = new Journal();
		journal.setDate(Functions.getCurrentDate());

		model.addAttribute("journal", journal);
		model.addAttribute("AccountGroup", getFilteredAccountList());
		// model.addAttribute("list", AG_service.getAll());

		return "AddJournal";
	}

	@PostMapping(value = "/Journal/Save")
	public String SaveDetails(@ModelAttribute(name = "group") Journal journal, Errors error) {

		FilterAndSave(journal);

		return "redirect:/ViewAllJournal";
	}

	private void FilterAndSave(Journal journal) {
		service.save(journal);

		for (JournalDetails JD : journal.getJDList()) {
			if (JD.getSubTotal() != 0.0) {
				JD.setJournal_ID(journal);
				UpdateParent(JD);
				JDservice.save(JD);
			}
		}
	}

	private Boolean UpdateParent(JournalDetails JD) {
		boolean result = false;
		AccountGroup item = JD.getSubGroup_ID();
		try {
			while (item.getIsParent() != null) {
				double amount = 0.0;
				if (JD.getIsCredit() == 1)
					amount = JD.getSubTotal() + item.getAmount();
				else
					amount = item.getAmount() - JD.getSubTotal();
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

	@GetMapping("ViewAllJournal")
	public String viewall(Model model) {
		model.addAttribute("list", getAll());
		return "ViewAllJournal";
	}

	@GetMapping("/Journal/View/{id}")
	public String viewDetails(@PathVariable("id") int id, Model model) {
		model.addAttribute("journal", find(id));
		return "JournalDetail";
	}

	public Journal find(int id) {
		return service.find(id);
	}

	public List<Journal> getAll() {
		return service.getAll();
	}

	public void populateAccountGroupList() {
		AG_List = new ArrayList<>();
		AG_List = AG_service.getAll();

	}

	public List<AccountGroup> getFilteredAccountList() {
		// List<AccountGroup> result = new ArrayList<>();
		// populateAccountGroupList();
		// for (AccountGroup AG : AG_List) {
		// result.add(AG);
		// }

		return AG_service.getWithParentRef(null);

	}
}
