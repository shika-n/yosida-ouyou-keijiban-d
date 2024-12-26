package com.example.keijiban.model.validations;

import org.hibernate.validator.constraints.Length;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileValidation {
	@NotBlank
	@Length(min = 1, max = 50)
	private String username;

	@NotBlank
	@Length(min = 4, max = 20)
	private String usertag;
	
	@NotBlank
	@Email
	private String email;
	
	@Length(max = 256)
	private String description;

	@Nullable
	@Length(max = 50)
	private String oldPassword;
	@Nullable
	@Length(max = 50)
	private String newPassword = "";
	@Nullable
	@Length(max = 50)
	private String newPasswordConfirm = "";

	@AssertTrue(message = "新しいパスワードと確認のためパスワードが間違います。")
	public boolean isPasswordMatching() {
		return newPassword.equals(newPasswordConfirm);
	}

	@AssertTrue(message = "パスワードは4から50の間の長さにしてください。変更したくない場合、何も入力しないでください。")
	public boolean isPasswordValid() {
		return newPassword.isBlank() || ((newPassword.length() >= 4 && newPassword.length() <= 50) &&
			(newPasswordConfirm.length() >= 4 && newPasswordConfirm.length() <= 50));
	}
}
