package com.tubeandcash.mail;

import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class MailUtil {

	public String writeHeader(){
		return "<html><head><title>Email do Robô do Twitter</title></head><body>";
	}
	
	public String writeForm(int cont, String videoUrl) {
		return "<table><tr><h1>"+cont+".</h1></tr>" +
				"<tr><td><label>Url</label></td><td><label>Title</label></td><td><label>Keywords</label></td><td><label>Description</label></td>"+
				"<td><label>Category</label></td></tr>"+
				"<tr><form action=\"http://ec2-50-16-71-46.compute-1.amazonaws.com/creative/\" method=\"POST\">" +

				"<td><input name=\"url\" value=\""+videoUrl+"\"></td><td><input name=\"title\"><input name=\"keywords\"></td>" +
				"<td><input name=\"description\"></td><td><input name=\"category\"></td><td><input type=\"submit\" title=\"OK\"></td>" +
				"<br/></form></tr><tr>"+writeEmbed(videoUrl)+"</tr></table>";
	}
	
	public String writeEmbed(String videoUrl) {
		
		return "<td><a href=\"http://www.youtube.com/watch?v="+videoUrl+"\" >Link</a></td><td><iframe width=\"420\" height=\"315\" src=\"http://www.youtube.com/embed/"+videoUrl+"\" frameborder=\"0\" allowfullscreen></iframe></td>";
	}

	public String writeFooter(){
		return "</table></body></html>";
	}

	public String writeHtmlMessage(List<String> youtubeUrls){
		String html=writeHeader();
		int cont=0;
		for(String s : youtubeUrls){
			html+=writeForm(++cont, s);
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
