package com.example.keijiban.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailTree {
	private PostDetail post;
	private ArrayList<PostDetailTree> replies;
}
