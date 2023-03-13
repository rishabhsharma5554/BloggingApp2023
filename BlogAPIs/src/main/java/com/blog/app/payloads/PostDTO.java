package com.blog.app.payloads;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO 
{	
	private Integer postId;
	private String title;
	private String content;
	
	private String imageName;
	private Date addedDate;
	private UserDTO user;
	private CategoryDTO category;
	
	//pick user id from url
	private List<CommentDTO> comments;
}
