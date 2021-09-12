package com.bitoasis.assignment.service.user;

import com.bitoasis.assignment.dto.response.Result;
import com.bitoasis.assignment.dto.user.CreateUserRequestDto;
import com.bitoasis.assignment.dto.user.CreateUserResponseDto;
import com.bitoasis.assignment.exception.UserAlreadyExistsException;

public interface UserService {
	public Result<CreateUserResponseDto> createUser(CreateUserRequestDto user) throws UserAlreadyExistsException;
}
