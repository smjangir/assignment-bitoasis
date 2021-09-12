package com.bitoasis.assignment.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponseDto {

	private String username;
	private String email;
	private String role;

}
