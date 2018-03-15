package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.APReciept;


@Repository
public interface APRecieptRepo extends JpaRepository<APReciept, Integer> {
}
