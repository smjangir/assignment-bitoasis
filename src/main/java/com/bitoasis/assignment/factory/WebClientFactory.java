package com.bitoasis.assignment.factory;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class WebClientFactory {

	public WebClient getInstanceWithLogging(String baseUrl) {
		return getInstanceWithLogging(baseUrl, getLoggingFilters());
	}

	private WebClient getInstanceWithLogging(String baseUrl, List<ExchangeFilterFunction> loggingFilters) {
		Builder webClientBuilder = WebClient.builder().baseUrl(baseUrl).defaultHeader(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON_VALUE);
		loggingFilters.forEach(webClientBuilder::filter);
		return webClientBuilder.build();
	}

	private List<ExchangeFilterFunction> getLoggingFilters() {

		return List.of(ExchangeFilterFunction.ofRequestProcessor(this::logRequest),
				ExchangeFilterFunction.ofResponseProcessor(this::logResponse));

	}

	Mono<ClientRequest> logRequest(ClientRequest request) {
		log.info("Inbound: " + request.method() + " " + request.url() + " " + request.headers());
		return Mono.just(request);
	}

	Mono<ClientResponse> logResponse(ClientResponse response) {
		log.info("Outbound: " + response.statusCode() + " " + response.headers().asHttpHeaders());
		return Mono.just(response);
	}
}
