package com.blog.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.config.security.JwtTokenHelper;
import com.blog.app.exceptions.APIException;
import com.blog.app.payloads.JWTAuthRequest;
import com.blog.app.payloads.JWTAuthResponse;
import com.blog.app.payloads.UserDTO;
import com.blog.app.services.UserService;
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JWTAuthResponse response = new JWTAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JWTAuthResponse>(response, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try 
		{
			this.authenticationManager.authenticate(authenticationToken);
		} 
		catch (BadCredentialsException e) 
		{
			System.out.println("Invalid Details!!");
			throw new APIException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
		}

	}
	
	//register new user API
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO)
	{
		UserDTO registedNewUser = this.userService.registerNewUser(userDTO);
		return new ResponseEntity<UserDTO>(registedNewUser,HttpStatus.CREATED);
	}
}