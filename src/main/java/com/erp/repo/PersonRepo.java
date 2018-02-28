package com.erp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.Person;

@Repository
public interface PersonRepo extends CrudRepository<Person, Integer> {
}