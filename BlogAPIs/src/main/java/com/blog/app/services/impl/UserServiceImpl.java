package com.blog.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entites.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.UserDTO;
import com.blog.app.repos.UserRepo;
import com.blog.app.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = this.dtoToEntity(userDTO);
		User savedUser = this.userRepo.save(user);
		return this.EntityToDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) { 
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getPassword());
		User updatedUser = this.userRepo.save(user);
		return this.EntityToDTO(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return this.EntityToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		
		/*
		old way
		List<UserDTO> userDTOs = new ArrayList<>();
		for(User u: users)
			userDTOs.add(this.EntityToDTO(u));
		*/
		
		//new way using steams api
		List<UserDTO> userDTOs = users.stream().map(user -> this.EntityToDTO(user)).collect(Collectors.toList());
		
		return userDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);
	}
	
	//convert DTO to Entity using ModelMapper
	private User dtoToEntity(UserDTO userDTO)
	{
		User user = this.modelMapper.map(userDTO, User.class);
		return user;
	}
	
	//convert Entity to DTO using ModelMapper
	private UserDTO EntityToDTO(User user)
	{
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
		return userDTO;
	}
	
	/*
	Manual Things to convert your Entity to DTO 
	and also it is specific to the User Entity
	What happens if some new Entity comes? like category, comments
	do you again create this conversion methods for the same?
	
	
	//Solution - ModelMapper Library
	private User dtoToEntity(UserDTO userDTO)
	{
		User user = new User();
		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		user.setEmail(userDTO.getEmail());
		return user;
	}
	
	private UserDTO EntityToDTO(User user)
	{
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setPassword(user.getPassword());
		userDTO.setAbout(user.getAbout());
		userDTO.setEmail(user.getEmail());
		return userDTO;
	}
	*/
}
