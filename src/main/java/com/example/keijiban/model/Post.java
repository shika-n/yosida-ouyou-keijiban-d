package com.example.keijiban.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="posts")
public class Post {
	@Id
	private Integer id;
	private Integer userId;
	private String content;
	private Integer parentPostId;
	private Date timestamp;
}
