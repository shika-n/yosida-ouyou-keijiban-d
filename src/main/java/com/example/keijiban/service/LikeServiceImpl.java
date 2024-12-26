package com.example.keijiban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.keijiban.repository.LikeRepository;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {
	@Autowired
	LikeRepository repo;

	@Override
	public void likePost(Integer postId, Integer userId) {
		repo.saveLike(postId, userId);
	}

	@Override
	public void removeLike(Integer postId, Integer userId) {
		repo.deleteLike(postId, userId);
	}

	@Override
	public Boolean exists(Integer postId, Integer userId) {
		return repo.getById(postId, userId).isPresent();
	}

}
