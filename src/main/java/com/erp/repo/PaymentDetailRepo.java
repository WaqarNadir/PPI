package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.PaymentDetails;

@Repository
public interface PaymentDetailRepo extends JpaRepository<PaymentDetails, Integer> {

}