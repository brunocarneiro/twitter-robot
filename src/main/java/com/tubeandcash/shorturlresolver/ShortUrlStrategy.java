package com.tubeandcash.shorturlresolver;

public interface ShortUrlStrategy {
	
	public String getOriginalUrl(String url) throws Exception;
	
}
