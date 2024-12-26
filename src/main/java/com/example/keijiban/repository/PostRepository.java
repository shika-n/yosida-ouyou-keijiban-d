package com.example.keijiban.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.keijiban.model.Post;
import com.example.keijiban.model.PostDetail;

public interface PostRepository extends CrudRepository<Post, Integer> {
	@Query("""
		SELECT
			p1.id,
			p1.parent_post_id,
			p1.user_id,
			u1.username,
			u1.usertag,
			p1.content,
			p1.timestamp,
			(
				SELECT COUNT(*)
				FROM posts
				WHERE parent_post_id = p1.id
			) AS "reply_count",
			(
				SELECT COUNT(*)
				FROM likes
				WHERE post_id = p1.id
			) AS "like_count",
			EXISTS (
				SELECT 1
				FROM likes
				WHERE post_id = p1.id AND user_id = :authUserId
			) AS "liked_by_user"
		FROM posts p1
		LEFT OUTER JOIN users u1 ON p1.user_id = u1.id
		WHERE p1.id = :id
		LIMIT 1;
	""")
	Optional<PostDetail> getPostById(@Param("id") Integer id, @Param("authUserId") Integer authUserId);

	@Query("""
		SELECT
			p1.id,
			p1.parent_post_id,
			p1.user_id,
			u1.username,
			u1.usertag,
			p1.content,
			p1.timestamp,
			(
				SELECT COUNT(*)
				FROM posts
				WHERE parent_post_id = p1.id
			) AS "reply_count",
			(
				SELECT COUNT(*)
				FROM likes
				WHERE post_id = p1.id
			) AS "like_count",
			EXISTS (
				SELECT 1
				FROM likes
				WHERE post_id = p1.id AND user_id = :authUserId
			) AS "liked_by_user"
		FROM posts p1
		LEFT OUTER JOIN users u1 ON p1.user_id = u1.id
		WHERE u1.id = :id
		ORDER BY p1.timestamp DESC
		LIMIT :limit OFFSET :offset;
	""")
	Iterable<PostDetail> getPostByUser(@Param("id") Integer id, @Param("authUserId") Integer authUserId, @Param("limit") Integer limit, @Param("offset") Integer offset);

	@Query("SELECT COUNT(id) FROM posts WHERE user_id = :id")
	Integer getPostCountByUser(@Param("id") Integer id);


	@Query("""
		WITH RECURSIVE post_thread AS
		(
			(
				SELECT
					0 depth,

					p1.id,
					p1.parent_post_id,
					p1.user_id,
					u1.username,
					u1.usertag,
					p1.content,
					p1.timestamp,
					(
						SELECT COUNT(*)
						FROM posts
						WHERE parent_post_id = p1.id
					) AS "reply_count",
					(
						SELECT COUNT(*)
						FROM likes
						WHERE post_id = p1.id
					) AS "like_count",
					EXISTS (
						SELECT 1
						FROM likes
						WHERE post_id = p1.id AND user_id = :authUserId
					) AS "liked_by_user"
				FROM posts p1
				LEFT OUTER JOIN users u1 ON p1.user_id = u1.id
				WHERE p1.parent_post_id = :id
				LIMIT :limit OFFSET :offset
			)

			UNION ALL

			(
				SELECT
					pt.depth + 1,

					p2.id,
					p2.parent_post_id,
					p2.user_id,
					u2.username,
					u2.usertag,
					p2.content,
					p2.timestamp,
					(
						SELECT COUNT(*)
						FROM posts
						WHERE parent_post_id = p2.id
					) AS "reply_count",
					(
						SELECT COUNT(*)
						FROM likes
						WHERE post_id = p2.id
					) AS "like_count",
					EXISTS (
						SELECT 1
						FROM likes
						WHERE post_id = p2.id AND user_id = :authUserId
					) AS "liked_by_user"
				FROM posts p2
				LEFT OUTER JOIN users u2 ON p2.user_id = u2.id
				INNER JOIN post_thread pt ON pt.id = p2.parent_post_id
				WHERE depth < :depthLimit AND p2.parent_post_id != :id
				LIMIT :subRepliesLimit + 1
			)
		)
		SELECT
			id,
			parent_post_id,
			user_id,
			username,
			usertag,
			content,
			timestamp,
			reply_count,
			like_count,
			liked_by_user
		FROM post_thread;
	""")
	Iterable<PostDetail> getPostRepliesById(@Param("id") Integer id, @Param("authUserId") Integer authUserId, @Param("limit") Integer limit, @Param("offset") Integer offset, @Param("depthLimit") Integer depthLimit, @Param("subRepliesLimit") Integer subRepliesLimit);

	@Query("SELECT COUNT(id) FROM posts WHERE parent_post_id = :id;")
	Integer getPostRepliesCountById(@Param("id") Integer id);

	@Query("""
		SELECT
			p1.id,
			p1.parent_post_id,
			p1.user_id,
			u1.username,
			u1.usertag,
			p1.content,
			p1.timestamp,
			(
				SELECT COUNT(*)
				FROM posts
				WHERE parent_post_id = p1.id
			) AS "reply_count",
			(
				SELECT COUNT(*)
				FROM likes
				WHERE post_id = p1.id
			) AS "like_count",
			EXISTS (
				SELECT 1
				FROM likes
				WHERE post_id = p1.id AND user_id = :authUserId
			) AS "liked_by_user"
		FROM posts p1
		LEFT OUTER JOIN users u1 ON p1.user_id = u1.id
		WHERE p1.parent_post_id IS NULL
		ORDER BY p1.timestamp DESC
		LIMIT :limit OFFSET :offset;
	""")
	Iterable<PostDetail> getTimeline(@Param("authUserId") Integer authUserId, @Param("limit") Integer limit, @Param("offset") Integer offset);

	@Query("SELECT COUNT(id) FROM posts WHERE parent_post_id IS NULL;")
	Integer getTimelineCount();
}
