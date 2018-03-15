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
		model.addAttribute("journal", new Journal());
		model.addAttribute("AccountGroup", getFilteredAccountList());

		return "AddJournal";
	}

	@PostMapping(value = "/Journal/Save")
	public String SaveDetails(@ModelAttribute(name = "group") Journal journal, Errors error) {

		FilterAndSave(journal);

		return "AddJournal";
	}

	private void FilterAndSave(Journal journal) {
		service.save(journal);

		for (JournalDetails JD : journal.getJDList()) {
			if (JD.getSubTotal() != 0.0) {
				JD.setJournal_ID(journal);
				JDservice.save(JD);
			}
		}
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
		List<AccountGroup> result = new ArrayList<>();
		populateAccountGroupList();
		for (AccountGroup AG : AG_List) {
			result.add(AG);
		}
		return result;
	}
}
