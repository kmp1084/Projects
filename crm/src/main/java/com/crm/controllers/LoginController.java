package com.crm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/sign_in")
	public String showSignIn() {
		return "sign_in";
	}
	
	@GetMapping("/access-denied")
	public String showDenialPage() {
		return "access-denied";
	}
}
