package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.Journal;

@Repository
public interface JournalRepo extends JpaRepository<Journal, Integer> {

}