package com.bitoasis.assignment.service.user;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bitoasis.assignment.dto.response.Result;
import com.bitoasis.assignment.dto.user.CreateUserRequestDto;
import com.bitoasis.assignment.dto.user.CreateUserResponseDto;
import com.bitoasis.assignment.entity.user.User;
import com.bitoasis.assignment.exception.UserAlreadyExistsException;
import com.bitoasis.assignment.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Result<CreateUserResponseDto> createUser(CreateUserRequestDto request) throws UserAlreadyExistsException {
		log.info("Inside "+this.getClass().getName());
		Optional<User> localUser = this.userRepository.getUserByEmail(request.getEmail());
		if (localUser.isPresent()) {
			throw new UserAlreadyExistsException("User Already Exists");
		}
		User userDE = this.userRepository.save(transform(request));
		return Result.success(
				CreateUserResponseDto.builder().email(userDE.getEmail()).username(request.getUsername()).build());
	}

	private User transform(CreateUserRequestDto request) {
		String username = Objects.isNull(request.getUsername()) ? request.getEmail() : request.getUsername();
		
		return User.builder().email(request.getEmail())
							.name(request.getName())
							.password(passwordEncoder
							.encode(request.getPassword()))
							.role("ROLE_USER")
							.username(username)
							.build();
	}

}
