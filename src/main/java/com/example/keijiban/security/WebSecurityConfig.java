package com.example.keijiban.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	// どこか見たのか忘れた、、ごめんなさい！
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((auth) -> auth
			.requestMatchers(
				HttpMethod.GET,
				"/",
				"/css/**",
				"/images/**",
				"/js/**",
				"/profile/**",
				"/post/**"
			).permitAll() // 誰でも見られる
			.requestMatchers("/login", "/register").anonymous() // ログインと登録ページはログインしていない状態だけで見られる
			.anyRequest().authenticated() // その他はログイン必要
		).formLogin(formLogin -> formLogin
			.loginPage("/login")
			.defaultSuccessUrl("/?loggedIn")
			.usernameParameter("email")
			.permitAll(false)
		).logout(logout -> logout
			.logoutSuccessUrl("/login?loggedOut")
			.invalidateHttpSession(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")
		));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
	}

}
