package com.erp.controllers;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.classes.AccountGroup;
import com.erp.classes.Constants;
import com.erp.classes.Functions;
import com.erp.classes.Journal;
import com.erp.classes.JournalDetails;
import com.erp.classes.ProfitLoss;
import com.erp.services.AccountGroupService;
import com.erp.services.JournalService;
import com.erp.services.ReportsService;

@Controller
public class Dashboard {

	@Autowired
	private ReportsService service;

	@Autowired
	private JournalService journalService;
	@Autowired
	private Report_AgingReports agingReport;
	@Autowired
	private ReportController reportController;
	@Autowired
	private AccountGroupService AGService;

	@GetMapping(value = "/")
	public String MainPage(Model model) {
		reportController.profitLossDetail(model);
		agingReport.APSummary(model);
		agingReport.ARSummary(model);
		getCurrentAsset(model);
		return "Dashboard";
	}

	private void getCurrentAsset(Model model) {
		AccountGroup currentAsset = AGService.findByName(Constants.CURRENT_ASSETS);

		HashMap<String, Double> val = new HashMap<>();
		for (AccountGroup child : currentAsset.getChildList()) {
			if (child.getAmount() != 0.0) {
				val.put(child.getAccName(), child.getAmount());
			}
		}
		String[] name = new String[val.size()];
		Double[] value = new Double[val.size()];
		
		int i = 0;
		for (String key : val.keySet()) {
			name[i] = key;
			value[i] = val.getOrDefault(key, 0.0);
			i++;
		}

		model.addAttribute("groupNames", name);
		model.addAttribute("groupValue", value);
		model.addAttribute("groupTotal", currentAsset.getAmount());

	}

}
