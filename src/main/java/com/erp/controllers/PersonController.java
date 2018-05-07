package com.erp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.erp.classes.Address;
import com.erp.classes.Email;
import com.erp.classes.Person;
import com.erp.classes.Phone;
import com.erp.services.PersonDetialService;
import com.erp.services.PersonService;

@Controller
public class PersonController {

	@Autowired
	private PersonService service;

	@Autowired
	private PersonDetialService personDetailservice;

	@GetMapping(value = "NewPerson")
	public String ViewPage(Model model) {
		model.addAttribute("person", new Person());
		model.addAttribute("phone", new Phone());
		model.addAttribute("email", new Email());
		model.addAttribute("address", new Address());

		return "NewPerson";
	}
	
	@GetMapping(value = "ViewAllPerson")
	public String ViewPersonPage(Model model) {	
		model.addAttribute("personList",getAll());
		return "ViewAllPerson";
	}

	@PostMapping(value = "SavePerson")
	public String SaveDetails(@ModelAttribute Person person, @ModelAttribute Phone phone,
			@ModelAttribute Address address, @ModelAttribute Email email, Errors error) {

		person = validate(person);

		service.save(person);
		System.out.println("value is :" + email.getEmail());
		if (!email.getEmail().isEmpty()) {
			email.setPerson(person);
			personDetailservice.saveEmail(email);
		}
		if (!address.getCity().isEmpty()) {
			address.setPerson(person);
			personDetailservice.saveAddress(address);
		}
		if (!phone.getPhoneNo().isEmpty()) {
			phone.setPerson(person);
			personDetailservice.savePhone(phone);
		}

		return "redirect:/NewPerson";
	}

	private Person validate(Person person) {
		if (!(person.getType() == null)) {

			if (person.getType().equals("0"))
				person.setType("Customer");
			else
				person.setType("Supplier");
		}

		return person;

	}

	public List<Person> getAll() {
		return service.getAll();
	}

}
