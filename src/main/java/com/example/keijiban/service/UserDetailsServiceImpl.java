package com.example.keijiban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.keijiban.model.User;
import com.example.keijiban.model.UserDetailsWrapper;
import com.example.keijiban.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository repo;

	// 神 -> https://zenn.dev/sthrok/articles/1a427f7a3f7f9a
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.getUserByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("ユーザー存在していません。"));
		
		return new UserDetailsWrapper(user);
	}
}
