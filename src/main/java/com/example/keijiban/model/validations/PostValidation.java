package com.example.keijiban.model.validations;

import org.hibernate.validator.constraints.Length;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostValidation {
	@Nullable
	private Integer replyToId;
	
	@Nullable
	private Boolean redirectToProfile;

	@NotBlank
	@Length(min = 1, max = 256)
	private String content;
}
