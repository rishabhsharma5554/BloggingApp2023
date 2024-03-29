package com.blog.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.payloads.APIResponse;
import com.blog.app.payloads.UserDTO;
import com.blog.app.repos.UserRepo;
import com.blog.app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController 
{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	//create new user
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO)
	{
		UserDTO createUser = this.userService.createUser(userDTO);
		return new ResponseEntity<>(createUser,HttpStatus.CREATED);
	}

	//update user using id
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,@PathVariable Integer id)
	{
		UserDTO updateUser = this.userService.updateUser(userDTO, id);
		return ResponseEntity.ok(updateUser);
	}
	
	//delete users using id
	@DeleteMapping("/{id}")
	public ResponseEntity<APIResponse> deleteUser(@PathVariable("id") Integer id)
	{
		this.userService.deleteUser(id);
		return new ResponseEntity<>(new APIResponse("User Deleted Succesfully", true),HttpStatus.OK);
	}
	
	//get all user
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers()
	{
		List<UserDTO> allUsers = this.userService.getAllUsers();
		return new ResponseEntity<List<UserDTO>>(allUsers,HttpStatus.OK);
	}
	
	//get specific user using id
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getSpecificUser(@PathVariable("id") Integer id)
	{
		UserDTO userDTO = this.userService.getUserById(id);
		return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
	}
}
