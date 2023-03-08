package com.blog.app.services;

import java.util.List;

import com.blog.app.payloads.PostDTO;

public interface PostService {
	
	//create
	PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId);
	
	//update
	PostDTO updatePost(PostDTO postDTO,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get
	List<PostDTO> getAllPosts();
	
	//get single Post by id
	PostDTO getPostById(Integer postId);
	
	//get All Post by Category id
	List<PostDTO> getPostsByCategoryId(Integer catId);
	
	//get All Posts by User id
	List<PostDTO> getPostsByUserId(Integer userId);
	
	//search posts
	List<PostDTO> searchPostsByKeyword(String keyword);
}
