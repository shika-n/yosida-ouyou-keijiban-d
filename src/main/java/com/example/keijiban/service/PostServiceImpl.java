package com.example.keijiban.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.keijiban.model.CustomPaging;
import com.example.keijiban.model.Post;
import com.example.keijiban.model.PostDetail;
import com.example.keijiban.model.PostDetailTree;
import com.example.keijiban.repository.PostRepository;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	@Autowired
	PostRepository repo;

	/*
	@Override
	public Optional<PostDetail> getPostById(Integer id, Integer authUserId) {
		return repo.getPostById(id, authUserId);
	}
	*/

	@Override
	public PostDetailTree getPostDetailTreeById(Integer id, Integer authUserId, CustomPaging paging) {
		Optional<PostDetail> postOpt = repo.getPostById(id, authUserId);
		if (!postOpt.isPresent()) {
			return null;
		}

		PostDetailTree root = new PostDetailTree(postOpt.get(), new ArrayList<PostDetailTree>());

		paging.setDataCount(repo.getPostRepliesCountById(id));

		Iterable<PostDetail> postDetailIter = repo.getPostRepliesById(id, authUserId, paging.getLimit(), paging.getOffset(), 5, 5);
		ArrayList<PostDetailTree> postDetailTreeList = new ArrayList<PostDetailTree>();

		postDetailTreeList.add(root);
		for (PostDetail post : postDetailIter) {
			postDetailTreeList.add(new PostDetailTree(post, new ArrayList<PostDetailTree>()));
		}

		HashMap<Integer, PostDetailTree> hashMap = new HashMap<Integer, PostDetailTree>();

		for (PostDetailTree postTree : postDetailTreeList) {
			hashMap.put(postTree.getPost().getId(), postTree);
		}

		for (PostDetailTree postTree : postDetailTreeList) {
			Integer key = postTree.getPost().getParentPostId();
			if (hashMap.containsKey(key)) {
				hashMap.get(key).getReplies().add(postTree);
			}
		}

		return root;
	}

	@Override
	public Iterable<PostDetail> getPostByUser(Integer id, Integer authUserId, CustomPaging paging) {
		paging.setDataCount(repo.getPostCountByUser(id));
		return repo.getPostByUser(id, authUserId, paging.getLimit(), paging.getOffset());
	}

	@Override
	public Iterable<PostDetail> getTimeline(Integer authUserId, CustomPaging paging) {
		paging.setDataCount(repo.getTimelineCount());
		return repo.getTimeline(authUserId, paging.getLimit(), paging.getOffset());
	}

	@Override
	public Post post(Integer userId, Integer parentPostId, String content) {
		Post newPost = new Post(null, userId, content, parentPostId, Date.from(Instant.now()));

		repo.save(newPost);

		return newPost;
	}

}
