package com.example.keijiban.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.keijiban.model.Like;

public interface LikeRepository extends  CrudRepository<Like, Integer> {

	// CrudRepositoryは集合主キーをサポートしてないみたいんで

	@Modifying
	@Query("""
		INSERT INTO likes VALUES (:postId, :userId);
	""")
	void saveLike(@Param("postId") Integer postId, @Param("userId") Integer userId);

	@Modifying
	@Query("""
		DELETE FROM likes WHERE post_id = :postId AND user_id = :userId;
	""")
	void deleteLike(@Param("postId") Integer postId, @Param("userId") Integer userId);

	@Query("""
		SELECT * FROM likes WHERE post_id = :postId AND user_id = :userId;
	""")
	Optional<Like> getById(@Param("postId") Integer postId, @Param("userId") Integer userId);
}
