package com.example.keijiban.model.validations;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationValidation {
	@NotBlank
	@Length(min = 1, max = 50)
	private String username;

	@NotBlank
	@Length(min = 4, max = 20)
	private String usertag;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Length(min = 4, max = 50)
	private String password;
}
