package com.blog.app.services;

import com.blog.app.payloads.CommentDTO;

public interface CommentService {
	CommentDTO createCommentDTO(CommentDTO commentDTO,Integer postId);
	void deleteComment(Integer commentId);
}
