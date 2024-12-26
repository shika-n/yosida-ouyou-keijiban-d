package com.example.keijiban.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetail {
	@Id
	private Integer id;
	private Integer parentPostId;
	private Integer userId;
	private String username;
	private String usertag;
	private String content;
	private Date timestamp;
	private Integer replyCount;
	private Integer likeCount;
	private Boolean likedByUser;

}
