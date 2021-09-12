package com.bitoasis.assignment.controller.user;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bitoasis.assignment.dto.response.Result;
import com.bitoasis.assignment.dto.user.CreateUserRequestDto;
import com.bitoasis.assignment.dto.user.CreateUserResponseDto;
import com.bitoasis.assignment.exception.UserAlreadyExistsException;
import com.bitoasis.assignment.model.JwtRequest;
import com.bitoasis.assignment.model.JwtResponse;
import com.bitoasis.assignment.service.user.UserDetailsServiceImpl;
import com.bitoasis.assignment.service.user.UserService;
import com.bitoasis.assignment.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Slf4j
public class UserController {

	private final AuthenticationManager authenticationManager;
	
	private final UserDetailsServiceImpl customUserDetailsService;
	
	private final JwtUtil jwtUtil;
	
	private final UserService userservice;
	
	
	@PostMapping("/register")
	@Operation(summary = "Register user")
	public ResponseEntity<?> resisterUser(@Valid @RequestBody CreateUserRequestDto user) throws JsonProcessingException, UserAlreadyExistsException {
		log.info("Inside "+this.getClass().getName());
		Result<CreateUserResponseDto> userDto = this.userservice.createUser(user);
		if(userDto.isSuccessful()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
		}
		return ResponseEntity.status(409).body(userDto);
	}
	
	@Operation(summary = "Generate token by username || email and password")
	@RequestMapping(value = "/generate-token", method = RequestMethod.POST) 
	public ResponseEntity<?> genrateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		log.info("Inside "+this.getClass().getName());
		this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		UserDetails loadUserByUsername = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String generateToken = this.jwtUtil.generateToken(loadUserByUsername);
		return ResponseEntity.ok(Result.success(JwtResponse.builder().token(generateToken).build())); 
	}

}
