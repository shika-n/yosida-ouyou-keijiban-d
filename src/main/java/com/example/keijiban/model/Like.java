package com.example.keijiban.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="likes")
public class Like {
	private Integer postId;
	private Integer userId;

}
