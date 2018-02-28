package com.erp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.Email;

@Repository
public interface EmailRepo extends CrudRepository<Email, Integer> {
	//List<Email> findByPerson(Person person);
}