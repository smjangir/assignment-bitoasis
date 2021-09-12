package com.bitoasis.assignment.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.bitoasis.assignment.dto.response.Failure;
import com.bitoasis.assignment.dto.response.Result;
import com.bitoasis.assignment.exception.UnableToFetchTicker;
import com.bitoasis.assignment.exception.UserAlreadyExistsException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class BaseController {

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Result<?>> handleException(MethodArgumentNotValidException exception) {
		log.info("In " + this.getClass().getName());
		List<Failure> errorMessage = exception.getBindingResult().getFieldErrors()
				.stream().map(err -> Failure.builder().field(err.getField())
														.rejectedValue(err.getRejectedValue())
														.message(err.getDefaultMessage())
														.build())
				.distinct().collect(Collectors.toList());

		return ResponseEntity.badRequest().body(Result.error(exception.getMessage(), errorMessage, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Result<?>> handleException(HttpMessageNotReadableException exception) {
		log.info("In " + this.getClass().getName());
		return ResponseEntity.badRequest().body(Result.error(exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Result<?>> handleException(MethodArgumentTypeMismatchException exception) {
		log.info("In " + this.getClass().getName());
		return ResponseEntity.badRequest().body(Result.error(exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(value = BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Result<?>> handleException(BadCredentialsException exception) {
		log.info("In " + this.getClass().getName());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.error(exception.getMessage(), HttpStatus.UNAUTHORIZED));
	}

	
	@ExceptionHandler(value = UserAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<Result<?>> handleException(UserAlreadyExistsException exception) {
		log.info("In " + this.getClass().getName());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.error(exception.getMessage(), HttpStatus.CONFLICT));
	}
	
	@ExceptionHandler(value = InternalServerError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Result<?>> handleException(InternalServerError exception) {
		log.info("In " + this.getClass().getName());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@ExceptionHandler(value = WebClientRequestException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Result<?>> handleException(WebClientRequestException exception) {
		log.info("In " + this.getClass().getName());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	
	@ExceptionHandler(value = UnableToFetchTicker.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Result<?>> handleException(UnableToFetchTicker exception) {
		log.info("In " + this.getClass().getName());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Result<?>> handleException(IllegalArgumentException exception) {
		log.info("In " + this.getClass().getName());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
	}
}
