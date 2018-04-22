package com.erp.controllers;

import java.util.List;

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
import com.erp.classes.Functions;
import com.erp.services.AccountGroupService;

@Controller
public class GroupController {
	

	@Autowired
	private AccountGroupService service;

	@GetMapping(value = "AddGroup")
	public String ViewPage(Model model) {
		model.addAttribute("group", new AccountGroup());
		model.addAttribute("parentList", service.getWithParentRef(null));

		return "AddGroups";
	}

	@PostMapping(value = "SaveGroup")
	public String SaveDetails(@ModelAttribute(name = "group") AccountGroup group, Errors error) {

		service.save(group);

		return "AddGroup";
	}

	@PostMapping(value = "getRefNo")
	public @ResponseBody String getRefNo(@RequestBody String data) {
		int id = Integer.parseInt(data);
		List<AccountGroup> AGList = service.find(id).getChildList();
		String[] result = AGList.get((AGList.size() - 1)).getRefNo().split("-");

		int index = Integer.parseInt(result[result.length - 1]) + 1;
		String refNo = "";
		for (int i = 0; i < result.length; i++) {
			if (i == (result.length - 1))
				refNo += Functions.getTwoDigitInt(index);
			else
				refNo += result[i] + "-";
		}
		return refNo;

	}

	public List<AccountGroup> getAll() {
		return service.getAll();
	}
}
