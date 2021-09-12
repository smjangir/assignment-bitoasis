package com.bitoasis.assignment.dto.user;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateUserRequestDto {

	@NotNull
	private String name;
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String email;
	private String role;
	
}
