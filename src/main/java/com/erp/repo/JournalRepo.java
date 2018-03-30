package com.erp.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erp.classes.Journal;
import com.erp.classes.TrailBalance;

@Repository
public interface JournalRepo extends JpaRepository<Journal, Integer> {
	List<Journal> ByDateRange(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
}