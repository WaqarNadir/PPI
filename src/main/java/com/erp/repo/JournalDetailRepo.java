package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.JournalDetails;

@Repository
public interface JournalDetailRepo extends JpaRepository<JournalDetails, Integer> {
}