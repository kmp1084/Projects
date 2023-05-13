package com.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.CustomerRepository;
import com.crm.entities.Customer;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerRepository cr;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository cr) {
		this.cr = cr;
	};
	
	@Override
	public List<Customer> showAll() {
		return cr.findAll();
	}

	@Override
	public Customer addCustomer(Customer c) {
		Customer savedCustomer = cr.save(c);
		return savedCustomer;
	}

	@Override
	public void deleteCustomerById(int id) {
		cr.deleteById(id);
	}

	@Override
	public Customer findById(int id) {
		return cr.getReferenceById(id);
	}

}
