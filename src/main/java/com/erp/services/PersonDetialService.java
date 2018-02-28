package com.erp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.Address;
import com.erp.classes.Email;
import com.erp.classes.Phone;
import com.erp.repo.AddressRepo;
import com.erp.repo.EmailRepo;
import com.erp.repo.PhoneRepo;

@Service
public class PersonDetialService {
	@Autowired
	public EmailRepo emailRepo;

	@Autowired
	public AddressRepo addressRepo;

	@Autowired
	public PhoneRepo phoneRepo;


	public void saveEmail(Email email) {
		emailRepo.save(email);
	}

	public void savePhone(Phone phone) {
		phoneRepo.save(phone);
	}

	public void saveAddress(Address address) {
		addressRepo.save(address);
	}

//	public List<Email> getEmail(Person person) {
//
//		return emailRepo.findByPerson(person);
//
//	}

//	public List<Address> getAddress(Person person) {
//
//		return addressRepo.findByPerson(person);
//
//	}
//
//	public List<Phone> getPhone(Person person) {
//
//		return phoneRepo.findByPerson(person);
//
//	}

}
