package com.tubeandcash.shorturlresolver;

public class ShortUrlStrategyFactory {

	public ShortUrlStrategy getShortUrlStrategy(String url){
		if(url.startsWith("http://t.co/")){
			return TwitterShortUrlStrategy.getInstance();
		}
		else
			return new ShortUrlStrategy() {
				public String getOriginalUrl(String url) throws Exception { return ""; }
			};
	}
	
}
