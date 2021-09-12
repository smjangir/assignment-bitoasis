package com.bitoasis.assignment.config;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bitoasis.assignment.dto.response.Result;
import com.bitoasis.assignment.service.AlternativeAPI;
import com.bitoasis.assignment.service.CachingService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class SpringConfig {

	final private CachingService cachingService;
	final AlternativeAPI api;
	
 	@Scheduled(fixedRate = 1000*60)
	public void clearTrckerCache() {
 		cachingService.evictAllCacheValues("ticker");
 		log.info("Ticker Cache clear ");
	}
 	
 	@PostConstruct
    public void fetchCoinsDetails() {
        log.info("Application is up now : fetching coin data");
        Result<String> allCoins = api.fetchCoinsDetails();
        if(allCoins.isSuccessful()) {
        	log.info(allCoins.getMessage());
        }
    }
}
