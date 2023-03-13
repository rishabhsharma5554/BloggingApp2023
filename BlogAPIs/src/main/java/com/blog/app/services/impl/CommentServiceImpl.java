package com.blog.app.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entites.Comment;
import com.blog.app.entites.Post;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.CommentDTO;
import com.blog.app.repos.CommentRepo;
import com.blog.app.repos.PostRepo;
import com.blog.app.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private CommentDTO entityToDTO(Comment comments)
	{
		return this.modelMapper.map(comments, CommentDTO.class);
	}
	
	private Comment dtoToEntity(CommentDTO commentDTO)
	{
		return this.modelMapper.map(commentDTO, Comment.class);
	}
	
	@Override
	public CommentDTO createCommentDTO(CommentDTO commentDTO, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = this.dtoToEntity(commentDTO);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.entityToDTO(savedComment);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		this.commentRepo.delete(comment);
	}
}
