package com.example.keijiban.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.example.keijiban.model.User;
import com.example.keijiban.model.validations.EditProfileValidation;
import com.example.keijiban.model.validations.UserRegistrationValidation;
import com.example.keijiban.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository repo;

	@Override
	public User register(UserRegistrationValidation form, BindingResult bindingResult) {
		if (!isEmailAndUsertagIsUnique(form.getEmail(), form.getUsertag(), bindingResult, null)) {
			return null;
		}

		Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
		String hashedPassword = encoder.encode(form.getPassword());

		User newUser = new User(null, form.getUsername(), form.getUsertag(), form.getEmail(), hashedPassword, null);

		return repo.save(newUser);
	}

	/// パスワードは隠されます
	@Override
	public Optional<User> getUserById(Integer id) {
		Optional<User> user = repo.findById(id);
		if (user.isPresent()) {
			return Optional.of(user.get().cloneUserWithNoPassword());
		}
		return user;
	}

	@Override
	public User edit(User user, EditProfileValidation form, BindingResult bindingResult) {
		if (!isEmailAndUsertagIsUnique(form.getEmail(), form.getUsertag(), bindingResult, user)) {
			return null;
		}

		Optional<User> userOpt = repo.findById(user.getId());
		if (userOpt.isPresent()) {
			User editedUser = userOpt.get();
			editedUser.setUsername(form.getUsername());
			editedUser.setUsertag(form.getUsertag());
			editedUser.setEmail(form.getEmail());
			editedUser.setDescription(form.getDescription());

			if (!form.getNewPassword().isBlank()) {
				Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

				if (encoder.matches(form.getOldPassword(), editedUser.getPassword())) {
					String newHashedPassword = encoder.encode(form.getNewPassword());
					editedUser.setPassword(newHashedPassword);
				} else {
					bindingResult.rejectValue("oldPassword", "error.oldPassword", "パスワードが間違いました。");
					return null;
				}
			}

			return repo.save(editedUser);
		}

		bindingResult.rejectValue("email", "error.email", "ユーザーが存在していません。");
		return null;
	}

	private boolean isEmailAndUsertagIsUnique(String email, String usertag, BindingResult bindingResult, User user) {
		boolean isUnique = true;
		if ((user == null || !user.getEmail().equals(email)) && repo.doesEmailExists(email)) {
			bindingResult.rejectValue("email", "error.email", "このメールはすでに登録されました。");
			isUnique = false;
		}
		if ((user == null || !user.getUsertag().equals(usertag)) && repo.doesUsertagExists(usertag)) {
			bindingResult.rejectValue("usertag", "error.usertag", "このタグはすでに登録されました。");
			isUnique = false;
		}
		return isUnique;
	}
}
