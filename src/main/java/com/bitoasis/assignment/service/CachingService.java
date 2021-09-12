package com.bitoasis.assignment.service;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CachingService {

	private final CacheManager cacheManager; 
	
	public void evictSingleCacheValue(String cacheName, String cacheKey) {
	    cacheManager.getCache(cacheName).evict(cacheKey);
	}

	public void evictAllCacheValues(String cacheName) {
	    cacheManager.getCache(cacheName).clear();
	}
	
}
