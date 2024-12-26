package com.example.keijiban.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.keijiban.model.CustomPaging;
import com.example.keijiban.model.User;
import com.example.keijiban.model.UserDetailsWrapper;
import com.example.keijiban.model.validations.EditProfileValidation;
import com.example.keijiban.model.validations.PostValidation;
import com.example.keijiban.model.validations.UserRegistrationValidation;
import com.example.keijiban.service.PostService;
import com.example.keijiban.service.UserService;


@Controller
public class UserController {

	@Autowired
	UserService service;

	@Autowired
	PostService postService;

	@GetMapping("/login")
	public String loginView(Model model) {
		return "login";
	}

	@GetMapping("/register")
	public String registerView(@ModelAttribute UserRegistrationValidation form, Model model) {
		model.addAttribute("form", form);
		return "register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute("form") @Validated UserRegistrationValidation form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return registerView(form, model);
		}

		User user = service.register(form, bindingResult);
		if (user == null) {
			return registerView(form, model);
		}

		return "redirect:/?newlyRegistered";
	}

	@GetMapping("/profile/{id}")
	public String profile(@AuthenticationPrincipal UserDetailsWrapper userDetails, @PathVariable("id") Integer id, @ModelAttribute PostValidation form, Model model, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
		Integer authUserId = null;
		if (userDetails != null) {
			model.addAttribute("authUser", userDetails.getUserModel());
			authUserId = userDetails.getUserModel().getId();
		}

		Optional<User> userOpt = service.getUserById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			CustomPaging paging = new CustomPaging(pageSize, page);
			model.addAttribute("user", user);
			model.addAttribute("posts", postService.getPostByUser(id, authUserId, paging));
			model.addAttribute("paging", paging);
		}
		model.addAttribute("form", form);
		return "profile";
	}

	@GetMapping("/edit_profile")
	public String editProfileView(@AuthenticationPrincipal UserDetailsWrapper userDetails, @ModelAttribute EditProfileValidation form, Model model) {
		if (userDetails != null) {
			model.addAttribute("authUser", userDetails.getUserModel());
		}

		User user = userDetails.getUserModel();

		form.setUsername(user.getUsername());
		form.setUsertag(user.getUsertag());
		form.setEmail(user.getEmail());
		form.setDescription(user.getDescription());

		model.addAttribute("form", form);
		return "profile_edit";
	}

	@PostMapping("/edit_profile")
	public String editProfile(@AuthenticationPrincipal UserDetailsWrapper userDetails, @ModelAttribute("form") @Validated EditProfileValidation form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return editProfileView(userDetails, form, model);
		}

		User user = service.edit(userDetails.getUserModel(), form, bindingResult);
		if (user == null) {
			return editProfileView(userDetails, form, model);
		}

		userDetails.setUserModel(user);

		return "redirect:/profile/" + userDetails.getUserModel().getId();
	}
}
