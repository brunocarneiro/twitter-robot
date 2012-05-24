package com.tubeandcash.shorturlresolver;

import java.net.HttpURLConnection;
import java.net.URL;

public class TwitterShortUrlStrategy implements ShortUrlStrategy{
	
	private static TwitterShortUrlStrategy instance;

	public String getOriginalUrl(String url) throws Exception {
		if(url.startsWith("http") || url.startsWith("HTTP") ){
			HttpURLConnection con = (HttpURLConnection)(new URL( url ).openConnection());
			con.setInstanceFollowRedirects( false );
			con.setConnectTimeout(10000);
			con.connect();
			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
			String location = con.getHeaderField( "Location" );
			if(responseCode!=200 && responseCode<404 && location!=null)
				return getOriginalUrl(location);
		}
		return url;
	}
	
	private TwitterShortUrlStrategy(){}
	
	public static TwitterShortUrlStrategy getInstance(){
		if(instance==null)
			instance=new TwitterShortUrlStrategy();
		return instance;
	}

}
