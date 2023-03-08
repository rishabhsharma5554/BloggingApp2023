package com.blog.app.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entites.Category;
import com.blog.app.entites.Post;
import com.blog.app.entites.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
}
