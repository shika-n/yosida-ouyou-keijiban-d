package com.example.keijiban.service;

public interface LikeService {
	void likePost(Integer postId, Integer userId);
	void removeLike(Integer postId, Integer userId);

	Boolean exists(Integer postId, Integer userId);
}
