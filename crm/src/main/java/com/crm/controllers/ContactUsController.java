package com.crm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.crm.entities.EmailMessage;

import jakarta.validation.Valid;

@Controller
public class ContactUsController {
	
	@Autowired
    private JavaMailSender emailSender;
	@Value("${spring.mail.username}")
	private String userName;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor ste = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, ste);
	}
	
	@GetMapping("/contact")
	public String showContactUsPage(Model model) {
		model.addAttribute("email", new EmailMessage());
		return "contact_us";
	}
	
	@PostMapping("/send_email")
	public String sendEmail(@Valid @ModelAttribute("email") EmailMessage message, BindingResult theBindingResult, Model model) {
		if (theBindingResult.hasErrors()) {
			return "contact_us";
		}
		SimpleMailMessage simple_message = new SimpleMailMessage();
		simple_message.setTo(userName);
		simple_message.setFrom(message.getEmail());
		simple_message.setSubject("Contact Us");
		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + message.getFirstName() + " " + message.getLastName());
		sb.append("\n");
		sb.append("email: " + message.getEmail());
		sb.append("\n");
		sb.append("Message: " + message.getMessage());
		simple_message.setText(sb.toString());
        emailSender.send(simple_message);
		model.addAttribute("successMessage", "Thank you for contacting us. We shall get back to you within 2 business days.");
		return "contact_us";
	}
}
