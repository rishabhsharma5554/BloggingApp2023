package com.blog.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entites.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
