package com.crm.service;

import java.util.List;

import com.crm.entities.Customer;

public interface CustomerService {
	public List<Customer> showAll();
	public Customer findById(int id);
	public Customer addCustomer(Customer c);
	public void deleteCustomerById(int id);
}
