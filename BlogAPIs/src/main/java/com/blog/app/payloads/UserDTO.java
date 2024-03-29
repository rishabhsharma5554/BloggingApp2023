package com.blog.app.payloads;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserDTO 
{
	private int id;
	@NotEmpty
	@Size(min = 4,message = "Name must be of min 4 Characters")
	private String name;
	@Email(message = "Email address is not valid")
	private String email;
	@NotEmpty
	private String about;
	@Size(min=3,max=10,message = "Password must be min 3 and max 10 Characters")
	private String password;
	
}
