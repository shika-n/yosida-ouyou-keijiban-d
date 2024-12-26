package com.example.keijiban.service;

import com.example.keijiban.model.CustomPaging;
import com.example.keijiban.model.Post;
import com.example.keijiban.model.PostDetail;
import com.example.keijiban.model.PostDetailTree;

public interface PostService {
	// Optional<PostDetail> getPostById(Integer id, Integer authUserId);
	PostDetailTree getPostDetailTreeById(Integer id, Integer authUserId, CustomPaging paging);
	Iterable<PostDetail> getPostByUser(Integer id, Integer authUserId, CustomPaging paging);
	Iterable<PostDetail> getTimeline(Integer authUserId, CustomPaging paging);

	Post post(Integer userId, Integer parentId, String content);
}
