package com.blog.app.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entites.Category;
import com.blog.app.entites.Post;
import com.blog.app.entites.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	Page<Post> findByUser(User user, Pageable pageable);
	Page<Post> findByCategory(Category category,Pageable pageable);
}
