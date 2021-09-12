package com.bitoasis.assignment.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bitoasis.assignment.dto.response.Result;
import com.bitoasis.assignment.exception.UnableToFetchTicker;
import com.bitoasis.assignment.model.GetTickerResponseDto;
import com.bitoasis.assignment.utils.ApiUtil;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlternativeAPI {

	private final Environment env;
	private final ApiUtil apiUtil;

	public Result<String> fetchCoinsDetails() {

		log.info("In side " + this.getClass().getName());

		String endpoint = env.getProperty("api.getall.coin");
		Assert.notNull(endpoint, "Please provide api.getall.coin value");

		String coinPath = env.getProperty("coin.json.path");
		Assert.notNull(coinPath, "Please provide coin.json.path value");

		String baseUrl = env.getProperty("alternative.baseurl");
		Assert.notNull(baseUrl, "Please provide alternative.baseurl value");
		
		apiUtil.rawRequestToWrite(uriBuilder -> uriBuilder.path(endpoint).build(), coinPath,  baseUrl);

		return Result.success("fetch successfully");
	}

	@Cacheable(cacheNames = "ticker", key = "#id")
	public Result<GetTickerResponseDto> getTicker(String id) throws UnableToFetchTicker {

		String endpoint = env.getProperty("api.gettraker");
		Assert.notNull(endpoint, "Please provide api.gettraker value");

		String baseUrl = env.getProperty("alternative.baseurl");
		Assert.notNull(baseUrl, "Please provide alternative.baseurl value");

		String trimId = String.valueOf(Integer.parseInt(id));

		var result = apiUtil.blockingGetRequest(uriBuilder -> uriBuilder.path(endpoint).build(trimId), baseUrl);
		if (Objects.nonNull(result) && result.isObject() && result.hasNonNull("data")
				&& !result.get("data").isEmpty()) {
			GetTickerResponseDto build = prepareResponseObject(trimId, result);
			return Result.success(build);
		} else if (result.hasNonNull("metadata") && !result.get("metadata").isEmpty()) {
			String error = result.get("metadata").get("error").asText();
			throw new UnableToFetchTicker(error);
		}
		throw new UnableToFetchTicker("Unable to fetch ticker");
	}

	private GetTickerResponseDto prepareResponseObject(String formateId, JsonNode result) {
		JsonNode jsonNode = result.path("data").get(formateId);
		JsonNode usd = jsonNode.path("quotes").path("USD");
		return GetTickerResponseDto.builder().code(jsonNode.get("symbol").asText())
				.daily_change(usd.path("percentage_change_24h").decimalValue()).last_updated(LocalDateTime.now())
				.volume(usd.get("volume_24h").longValue()).price(usd.get("price").decimalValue()).build();
	}
}
