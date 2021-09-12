package com.bitoasis.assignment.utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.bitoasis.assignment.factory.WebClientFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApiUtil {

	private final WebClientFactory wcFactory;

	private final ObjectMapper mapper;

	public Mono<byte[]> rawRequest(Function<UriBuilder, URI> uriBuildingFunction, String baseUrl) {
		final WebClient wc = wcFactory.getInstanceWithLogging(baseUrl);
		return wc.get().uri(uriBuildingFunction).retrieve().bodyToMono(byte[].class);
	}

	public Boolean rawRequestToWrite(Function<UriBuilder, URI> uriBuildingFunction, String basePath, String baseUrl) {
		final WebClient wc = wcFactory.getInstanceWithLogging(baseUrl);
		Flux<DataBuffer> bodyToFlux = wc.get().uri(uriBuildingFunction).retrieve().bodyToFlux(DataBuffer.class);
		
		final Path path = FileSystems.getDefault().getPath(basePath);
		DataBufferUtils.write(bodyToFlux, path, StandardOpenOption.CREATE).block();

		return true;
	}

	public JsonNode blockingGetRequest(Function<UriBuilder, URI> uriBuildingFunction, String baseUrl) {
		try {
			var result = rawRequest(uriBuildingFunction, baseUrl).block();
			return mapper.readTree(result);
		} catch (IOException e) {
			log.error("Errored in converting response to json node", e);
			throw new UncheckedIOException(e);
		}
	}


}
