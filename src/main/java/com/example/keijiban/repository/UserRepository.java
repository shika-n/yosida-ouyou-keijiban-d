package com.example.keijiban.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.keijiban.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	@Query("""
		SELECT * FROM users
		WHERE email = :email
		LIMIT 1;
	""")
	Optional<User> getUserByEmail(@Param("email") String email);

	@Query("""
		SELECT EXISTS(
			SELECT 1 FROM users
			WHERE email = :email
		);
	""")
	Boolean doesEmailExists(@Param("email") String email);

	@Query("""
		SELECT EXISTS(
			SELECT 1 FROM users
			WHERE usertag = :usertag
		);
	""")
	Boolean doesUsertagExists(@Param("usertag") String usertag);
}
