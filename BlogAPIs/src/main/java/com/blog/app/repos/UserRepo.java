package com.blog.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entites.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
