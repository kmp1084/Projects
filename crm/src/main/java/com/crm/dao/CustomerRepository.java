package com.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.entities.Customer;

import jakarta.persistence.OrderBy;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@OrderBy("firstName ASC, lastName ASC")
	public List<Customer> findAll();
}
