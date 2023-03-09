package com.blog.app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import com.blog.app.payloads.APIResponse;
import com.blog.app.payloads.PostDTO;
import com.blog.app.payloads.PostResponse;
import com.blog.app.services.FileService;
import com.blog.app.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
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
	
	//Pagination Implemented for this
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
			@RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy)
	{
		PostResponse postResp = this.postService.getAllPosts(pageNo,pageSize,sortBy);
		return new ResponseEntity<>(postResp,HttpStatus.OK);
	}
	
	
	//tested
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(
			@PathVariable Integer userId,
			@RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
			@RequestParam(value = "pageSize",defaultValue = "10", required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
			@RequestParam(value = "sortBy",defaultValue = "asc",required = false) String sortDir)
	{
		PostResponse postResp =  this.postService.getPostsByUserId(userId,pageNo,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(postResp,HttpStatus.OK);
	}
	
	//tested
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(
			@PathVariable Integer catId,
			@RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
			@RequestParam(value = "pageSize",defaultValue = "10", required = false)Integer pageSize)
	{
		PostResponse postResp = this.postService.getPostsByCategoryId(catId,pageNo,pageSize);
		return new ResponseEntity<>(postResp,HttpStatus.OK);
		
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
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchByTitle(@PathVariable("keyword") String key)
	{
		List<PostDTO> allDTOPosts = this.postService.searchPostsByKeyword(key);
		return new ResponseEntity<>(allDTOPosts,HttpStatus.OK);
	}
	
	//post image upload
	
//	@PostMapping("/posts/image/upload/{postId}")
//	public ResponseEntity<PostDTO> uploadImage(
//			@PathVariable Integer postId,
//			@RequestParam("image") MultipartFile imageFile) throws IOException
//	{
//		
//		PostDTO postById = this.postService.getPostById(postId);
//		String fileName = this.fileService.uploadImage(path, imageFile);
//		
//		postById.setImageName(fileName);
//		PostDTO updatedPost = this.postService.updatePost(postById, postId);
//		return new ResponseEntity<PostDTO>(updatedPost,HttpStatus.OK);
//	}
	
	//Async call for large data 
	@PostMapping("/posts/image/upload/{postId}")
	public CompletableFuture<PostDTO> uploadImageAsync(
	    @PathVariable Integer postId,
	    @RequestParam("image") MultipartFile imageFile) {

	    return CompletableFuture.supplyAsync(() -> {
	        PostDTO postById = postService.getPostById(postId);
	        String fileName = null;
			try 
			{
				fileName = fileService.uploadImage(path, imageFile);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
	        postById.setImageName(fileName);
	        PostDTO updatedPost = postService.updatePost(postById, postId);
	        return updatedPost;
	    });
	}
	
	@GetMapping(value ="/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE )
	public void downloadIamge(@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException
	{
		InputStream resource =  this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
