package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.ARReciept;

@Repository
public interface ARRecieptRepo extends JpaRepository<ARReciept, Integer> {
}