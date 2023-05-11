package com.crm.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage {
	@NotNull(message="First Name cannot be left empty")
	private String firstName;
	@NotNull(message="Last Name cannot be left empty")
	private String lastName;
	@NotNull(message="email cannot be empty")
	@Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message="not a valid email address")
	private String email;
	@NotNull(message="Message cannot be left empty")
	private String message;
	
	@Override
	public String toString() {
		return "EmailMessage [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", message="
				+ message + "]";
	}
}
