package com.example.keijiban.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsWrapper implements UserDetails {
	private static final long serialVersionUID = 1L;

	// Userのオブジェクトがあっちこっちに投げることがありますので
	// 念の為、パスワードを別の変数に保存します。
	private User user;
	private String password;

	public UserDetailsWrapper(User user) {
		setUserModel(user);
	}

	public User getUserModel() {
		return user;
	}

	public void setUserModel(User user) {
		this.password = user.getPassword();
		this.user = user.cloneUserWithNoPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("USER"));
		return roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

}
