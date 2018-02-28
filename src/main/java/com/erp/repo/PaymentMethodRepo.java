package com.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.classes.PaymentMethods;

@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethods, Integer> {

}