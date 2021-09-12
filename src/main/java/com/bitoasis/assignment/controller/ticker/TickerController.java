package com.bitoasis.assignment.controller.ticker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitoasis.assignment.dto.response.Result;
import com.bitoasis.assignment.exception.UnableToFetchTicker;
import com.bitoasis.assignment.model.GetTickerResponseDto;
import com.bitoasis.assignment.service.AlternativeAPI;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Slf4j
public class TickerController  {

	private final AlternativeAPI api;
	
	@GetMapping("/ticker/{id}")
	@Operation(summary = "Get a Ticker Data by its id")
	public ResponseEntity<GetTickerResponseDto> getTickerData(@PathVariable("id") String id) throws JsonProcessingException, UnableToFetchTicker {
		log.info("Inside "+this.getClass().getName());
		Result<GetTickerResponseDto> allCoins = api.getTicker(id);
		return ResponseEntity.ok(allCoins.getData());
	}
	
}
