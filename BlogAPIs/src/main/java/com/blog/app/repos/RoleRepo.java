package com.blog.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entites.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}