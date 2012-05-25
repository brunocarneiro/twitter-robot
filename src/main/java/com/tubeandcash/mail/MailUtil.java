package com.tubeandcash.mail;

import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class MailUtil {

	public String writeHeader(){
		return "<html><head><title>Email do Robô do Twitter</title></head><body>";
	}
	
	public String writeForm(String videoUrl) {
		return "<form action=\"http://ec2-50-16-71-46.compute-1.amazonaws.com/creative/\" method=\"POST\">" +
				"<input name=\"url\" value=\""+videoUrl+"\"><input name=\"title\"><input name=\"keywords\">" +
				"<input name=\"description\"><input name=\"category\"><input type=\"submit\" title=\"OK\"><br/>"+writeEmbed(videoUrl)+"</form>";
	}
	
	public String writeEmbed(String videoUrl) {
		
		return "<a href=\"http://www.youtube.com/watch?v="+videoUrl+"\" >Link</a><iframe width=\"420\" height=\"315\" src=\"http://www.youtube.com/embed/"+videoUrl+"\" frameborder=\"0\" allowfullscreen></iframe>";
	}

	public String writeFooter(){
		return "</body></html>";
	}

	public String writeHtmlMessage(List<String> youtubeUrls){
		String html=writeHeader();
		
		for(String s : youtubeUrls){
			html+=writeForm(s);
		}
		html+=writeFooter();
		return html;
	}
	
	public void sendMail(List<String> youtubeUrls) throws EmailException{
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("socialsharedvideos@gmail.com", "youtube_social"));
		email.setSSL(true);
		email.setFrom("brunoac88@gmail.com");
		email.setSubject("YouTube Twitter Daily Videos! ;)");
		email.setHtmlMsg(writeHtmlMessage(youtubeUrls));
		email.addTo("brunoac88@gmail.com");
		email.send();
	}
}
