package com.blog.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.payloads.APIResponse;
import com.blog.app.payloads.CommentDTO;
import com.blog.app.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment,@PathVariable Integer postId)
	{
		CommentDTO createCommentDTO = this.commentService.createCommentDTO(comment, postId);
		return new ResponseEntity<>(createCommentDTO,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commId}")
	public ResponseEntity<APIResponse> createComment(@PathVariable Integer commId)
	{
		this.commentService.deleteComment(commId);
		return new ResponseEntity<>(new APIResponse("commented deleted.",true),HttpStatus.OK);
	}
}
