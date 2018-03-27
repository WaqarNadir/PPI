package com.erp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.erp.classes.AccountGroup;
import com.erp.services.AccountGroupService;

@Controller
public class GroupController {

	@Autowired
	private AccountGroupService service;
	private List<AccountGroup> AG_List;

	@GetMapping(value = "AddGroup")
	public String ViewPage(Model model) {
		model.addAttribute("group", new AccountGroup());
		return "AddGroup";
	}

	@PostMapping(value = "SaveGroup")
	// public String SaveDetails(@ModelAttribute AccountGroup group,Errors error)
	public String SaveDetails(@ModelAttribute(name = "group") AccountGroup group, Errors error) {

		// group = validate(group);

		service.save(group);

		return "AddGroup";
	}

	// private AccountGroup validate(AccountGroup group) {
	//
	// if (!(group.getIsParent() == null)) {
	//
	// if (group.getIsParent().equals("0"))
	// group.setIsParent(group.getIsParent());
	// else
	// group.setIsParent("Child");
	// }
	//
	// return group;
	//
	// }

	public List<AccountGroup> getAll() {
		return service.getAll();
	}
}
