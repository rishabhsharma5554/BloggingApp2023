package com.blog.app.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.app.entites.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
