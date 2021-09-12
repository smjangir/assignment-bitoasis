package com.bitoasis.assignment.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bitoasis.assignment.dto.response.AlternativeResponseDto;
import com.bitoasis.assignment.dto.response.CoinDto;
import com.bitoasis.assignment.dto.response.Result;
import com.bitoasis.assignment.enums.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class CoinService {

	private final ObjectMapper mapper;
	private final Environment env;
	
	@Cacheable(cacheNames = "coin", key = "#id")
	public Result<List<CoinDto>> getCoinData(String id){
		log.info("Inside "+this.getClass().getName());
		try {
			final Path path = FileSystems.getDefault().getPath(env.getProperty("coin.json.path"));
			final File fileObject = new File(path.toAbsolutePath().toString());
			final AlternativeResponseDto customer = mapper.readValue(fileObject, AlternativeResponseDto.class);
			log.info("Coin fetched");
			List<CoinDto> data = customer.getData();
			List<CoinDto> sorted = null;
			if(Order.ASE.name().equals(id)) {
				sorted = data.parallelStream().sorted().collect(Collectors.toList());
			}
			else {
				sorted = data.parallelStream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
			}
			return Result.success(sorted);
		} catch (IOException e){
			return Result.fail("Unable to load json data");
		}
	}
}
