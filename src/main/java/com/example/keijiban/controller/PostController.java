package com.example.keijiban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.example.keijiban.model.UserDetailsWrapper;
import com.example.keijiban.model.validations.PostValidation;
import com.example.keijiban.service.LikeService;
import com.example.keijiban.service.PostService;

@Controller
public class PostController {

	@Autowired
	PostService service;

	@Autowired
	LikeService likeService;

	@Autowired
	UserController userController;

	@GetMapping("/")
	public String index(@AuthenticationPrincipal UserDetailsWrapper userDetails, @ModelAttribute PostValidation form, Model model, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
		Integer authUserId = null;
		if (userDetails != null) {
			model.addAttribute("authUser", userDetails.getUserModel());
			authUserId = userDetails.getUserModel().getId();
		}
		CustomPaging paging = new CustomPaging(pageSize, page);
		model.addAttribute("posts", service.getTimeline(authUserId, paging));
		model.addAttribute("form", form);
		model.addAttribute("paging", paging);

		return "index";
	}

	@GetMapping("/post/{id}")
	public String postThreadView(@AuthenticationPrincipal UserDetailsWrapper userDetails, @PathVariable("id") Integer id, @ModelAttribute PostValidation form, Model model, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
		Integer authUserId = null;
		if (userDetails != null) {
			model.addAttribute("authUser", userDetails.getUserModel());
			authUserId = userDetails.getUserModel().getId();
		}
		CustomPaging paging = new CustomPaging(pageSize, page);
		model.addAttribute("postDetailTree", service.getPostDetailTreeById(id, authUserId, paging));
		model.addAttribute("form", form);
		model.addAttribute("paging", paging);
		return "post_thread";
	}

	@PostMapping("/post")
	public String post(@AuthenticationPrincipal UserDetailsWrapper userDetails, @ModelAttribute("form") @Validated PostValidation form, BindingResult bindingResult, Model model, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
		if (bindingResult.hasErrors()) {
			if (form.getReplyToId() != null) {
				return postThreadView(userDetails, form.getReplyToId(), form, model, page, pageSize);
			}

			if (form.getRedirectToProfile() != null && form.getRedirectToProfile()) {
				return userController.profile(userDetails, userDetails.getUserModel().getId(), form, model, page, pageSize);
			}

			return index(userDetails, form, model, page, pageSize);
		}

		service.post(userDetails.getUserModel().getId(), form.getReplyToId(), form.getContent());

		if (form.getReplyToId() != null) {
			return "redirect:/post/" + form.getReplyToId();
		}

		if (form.getRedirectToProfile() != null && form.getRedirectToProfile()) {
			return "redirect:/profile/" + userDetails.getUserModel().getId();
		}

		return "redirect:/";
	}

	@PostMapping("/like")
	public ResponseEntity<String> likePost(@AuthenticationPrincipal UserDetailsWrapper userDetails, @RequestParam("postId") Integer postId) {
		int userId = userDetails.getUserModel().getId();
		if (likeService.exists(postId, userId)) {
			likeService.removeLike(postId, userId);
			return ResponseEntity.ok("Unliked");
		} else {
			likeService.likePost(postId, userId);
			return ResponseEntity.ok("Liked");
		}
	}
}
