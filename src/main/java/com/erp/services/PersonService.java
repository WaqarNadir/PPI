package com.erp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.classes.Person;
import com.erp.repo.PersonRepo;

@Service
public class PersonService {
	@Autowired
	public PersonRepo repo;

	List<Person> PersonList = new ArrayList<Person>();

	public void save(Person person) {
		repo.save(person);

	}

	public void delete(int id) {
		repo.delete(id);
		

	}

	public void update(int id, Person person) {
		repo.save(person);

	}

	public Person find(int id) {
		System.out.println(repo.findOne(id));
		return repo.findOne(id);

	}

	public List<Person> getAll() {
		PersonList.clear();
		repo.findAll().forEach(PersonList::add);
		return PersonList;

	}

}
