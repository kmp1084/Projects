package com.crm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.entities.Customer;
import com.crm.service.CustomerService;

import jakarta.validation.Valid;

@Controller
public class CustomerController {
	
	private CustomerService cs;
	
	@Autowired
	public CustomerController(CustomerService cs) {
		this.cs = cs;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor ste = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, ste);
	}
	
	@GetMapping("/")
	public String showAllCustomers(Model model) {
		List<Customer> list = cs.showAll();
		model.addAttribute("leads", list);
		return "leads/current_leads";
	}
	
	@GetMapping("/lead/addLead")
	public String showAddLeadForm(Model model) {
		Customer lead = new Customer();
		model.addAttribute("lead", lead);
		return "leads/addLead_form";
	}
	
	@PostMapping("/lead/save")
	public String saveLead(@Valid @ModelAttribute("lead") Customer lead, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "leads/addLead_form";
		}
		cs.addCustomer(lead);
		return "redirect:/";
	}
	
	@GetMapping("/lead/update")
	public String updateLead(@RequestParam("id") int id, Model model) {
		Customer lead = cs.findById(id);
		model.addAttribute("lead", lead);
		return "leads/addLead_form";
	}
	
	@GetMapping("/lead/delete")
	public String deleteLead(@RequestParam("id") int id) {
		cs.deleteCustomerById(id);
		return "redirect:/";
	}
}
