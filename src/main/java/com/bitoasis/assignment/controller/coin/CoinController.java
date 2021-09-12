package com.bitoasis.assignment.controller.coin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bitoasis.assignment.dto.response.CoinDto;
import com.bitoasis.assignment.dto.response.Result;
import com.bitoasis.assignment.enums.Order;
import com.bitoasis.assignment.service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Slf4j
public class CoinController  {

	private final CoinService coinsService;
	
	@GetMapping("/coin")
	@Operation(summary = "Get Coin data")
	public ResponseEntity<List<CoinDto>> getCoinData(@RequestParam(name = "sort") Order orderBy) throws JsonProcessingException {
		log.info("Inside "+this.getClass().getName());
		Result<List<CoinDto>> allCoins = coinsService.getCoinData(orderBy.name());
		return ResponseEntity.ok(allCoins.getData());
	}
}
