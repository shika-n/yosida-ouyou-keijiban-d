package com.example.keijiban.service;

import java.util.Optional;

import org.springframework.validation.BindingResult;

import com.example.keijiban.model.User;
import com.example.keijiban.model.validations.EditProfileValidation;
import com.example.keijiban.model.validations.UserRegistrationValidation;

public interface UserService {
	User register(UserRegistrationValidation form, BindingResult bindingResult);
	Optional<User> getUserById(Integer id);
	User edit(User user, EditProfileValidation form, BindingResult bindingResult);
}
