package com.tubeandcash.engine;

import java.util.ArrayList;
import java.util.List;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import com.tubeandcash.mail.MailUtil;
import com.tubeandcash.shorturlresolver.ShortUrlStrategyFactory;

public class TwitterEngine {

	public static final Twitter twitter= new TwitterFactory().getInstance();
	public static final ShortUrlStrategyFactory shortUrlStrategyFactory = new ShortUrlStrategyFactory();
	
	public static void main (String [] args){
		try{
		    Twitter twitter = new TwitterFactory().getInstance();
		    long cursor=-1L;
		    IDs followers = twitter.getFriendsIDs(cursor);
		    List<String> youtubeUrls = new ArrayList<String>();
		    while(cursor!=0){
		    	for (long id : followers.getIDs()) {
		    		ResponseList<Status> statuss = twitter.getUserTimeline(id);
		    		for(Status s : statuss){
		    			String text = s.getText();
		    			if(text.contains("http://")){
		    				String words [] = text.split("\\s");
		    				for(String word:words){
		    					if(word.contains("http://")){
		    						try{
		    							String originalUrl = shortUrlStrategyFactory.getShortUrlStrategy(word).getOriginalUrl(word);
		    							if(originalUrl.contains("youtube.com"))
		    								youtubeUrls.add(originalUrl.replace("http://www.youtube.com/watch?v=", ""));
		    						}
		    						catch (Exception e) {
		    							e.printStackTrace();
		    							System.out.println("Exception, but I'll continue verifing tweets...");
		    							//se acontecer uma excecao tem que continuar verificando as urls
		    							continue;
									}
		    							
		    					}
		    				}
		    			}
		    		}
		    	}
		    	followers = twitter.getFollowersIDs(cursor);
		    	cursor=followers.getNextCursor();
		    }
		    new MailUtil().sendMail(youtubeUrls);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
