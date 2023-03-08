package com.blog.app.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.blog.app.entites.Category;
import com.blog.app.entites.Post;
import com.blog.app.entites.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.PostDTO;
import com.blog.app.payloads.PostResponse;
import com.blog.app.repos.CategoryRepo;
import com.blog.app.repos.PostRepo;
import com.blog.app.repos.UserRepo;
import com.blog.app.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo catRepo;
	
	private Post dtotoEntity(PostDTO postDTO)
	{
		Post post = this.modelMapper.map(postDTO, Post.class);
		return post;
	}
	
	private PostDTO entityToDto(Post post)
	{
		PostDTO postDTO = this.modelMapper.map(post, PostDTO.class);
		return postDTO;
	}
	
	
	@Override
	public PostDTO createPost(PostDTO postDTO,Integer userId,Integer catId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User id", " id ", catId));
		Category category = this.catRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", catId));
		Post post = this.dtotoEntity(postDTO);
		post.setUser(user);
		post.setCategory(category);
		post.setImageName("default.jpeg");
		post.setAddedDate(new Date());
		this.postRepo.save(post);
		return this.entityToDto(post);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.entityToDto(updatedPost);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		this.postRepo.delete(post);
	}

	//commented due to pagination implemented
//	@Override
//	public List<PostDTO> getAllPosts() 
//	{
//		List<Post> allPost = this.postRepo.findAll();
//		List<PostDTO> allPostDTO = allPost.stream().map(postDTO -> this.entityToDto(postDTO)).collect(Collectors.toList());
//		return allPostDTO;
//	}
	
	@Override
	public PostResponse getAllPosts(Integer pageNo,Integer pageSize) 
	{
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> allPost = pagePost.getContent();
		List<PostDTO> allPostDTO = allPost.stream().map(postDTO -> this.entityToDto(postDTO)).collect(Collectors.toList());
		
		PostResponse postResp = new PostResponse();
		postResp.setContent(allPostDTO);
		postResp.setPageNo(pagePost.getNumber());
		postResp.setPageSize(pagePost.getSize());
		postResp.setTotalRecords(pagePost.getTotalElements());
		postResp.setTotalPages(pagePost.getTotalPages());
		postResp.setLastPage(pagePost.isLast());
		return postResp;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		return this.entityToDto(post);
	}

	@Override
	public List<PostDTO> getPostsByCategoryId(Integer catId) {
		
		Category cat = this.catRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", catId));
		List<Post> allPosts = this.postRepo.findByCategory(cat);
		return allPosts.stream().map(post -> this.entityToDto(post)).collect(Collectors.toUnmodifiableList());
	}

	@Override
	public List<PostDTO> getPostsByUserId(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
		List<Post> allPosts = this.postRepo.findByUser(user);
		List<PostDTO> allPostsByUserid = allPosts.stream().map(post -> this.entityToDto(post)).collect(Collectors.toUnmodifiableList());
		return allPostsByUserid;
	}

	@Override
	public List<PostDTO> searchPostsByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
}
