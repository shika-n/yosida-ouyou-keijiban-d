package com.example.keijiban.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
	@Id
	private Integer id;
	private String username;
	private String usertag;
	private String email;
	private String password;
	private String description;

	public User cloneUserWithNoPassword() {
		return new User(
			id,
			username,
			usertag,
			email,
			"[REDACTED]",
			description
		);
	}

}
