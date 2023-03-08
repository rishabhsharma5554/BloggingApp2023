package com.blog.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.payloads.APIResponse;
import com.blog.app.payloads.PostDTO;
import com.blog.app.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//Tested
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(
			@RequestBody PostDTO postDTO, 
			@PathVariable Integer userId, 
			@PathVariable Integer categoryId)
	{
		PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);
		return new ResponseEntity<>(createdPost,HttpStatus.CREATED);
	}
	
	//Tested
	@GetMapping("/posts")
	public ResponseEntity<List<PostDTO>> getAllPosts()
	{
		List<PostDTO> allDTOPosts = this.postService.getAllPosts();
		return new ResponseEntity<List<PostDTO>>(allDTOPosts,HttpStatus.OK);
	}
	
	
	//tested
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(
			@PathVariable Integer userId)
	{
		List<PostDTO> allPosts = this.postService.getPostsByUserId(userId);
		return new ResponseEntity<List<PostDTO>>(allPosts,HttpStatus.OK);
		
	}
	
	//tested
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(
			@PathVariable Integer catId)
	{
		List<PostDTO> allPosts = this.postService.getPostsByCategoryId(catId);
		return new ResponseEntity<List<PostDTO>>(allPosts,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<APIResponse> deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ResponseEntity<APIResponse>(new APIResponse("Deleted Succesfully",true),HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,@PathVariable Integer postId)
	{
		this.postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(postDTO,HttpStatus.OK);
	}
	
	
	
}
