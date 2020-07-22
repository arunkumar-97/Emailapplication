package com.jesperapps.test.Emailservice;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.jesperapps.test.User.User;

@Service
public class Emailservice {

	
	private JavaMailSender javamailsender;
	
	@Autowired
	public Emailservice(JavaMailSender javamailsender) {
		this.javamailsender = javamailsender;
	}
	
	private Session initializeSession() {
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.smtp.host", "smtp.gmail.com");
		mailProperties.put("mail.smtp.port", "587");
		Session mimeSession = Session.getInstance(mailProperties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("arun.thril@gmail.com","arunvenkat");
			}
		});
		return mimeSession;
	}

	
	public void sendnotification(User user)throws MailException, MessagingException, IOException {
		//attachment directory location
		final String FILE_DIRECTORY_LOCATION = "C:\\Users\\Public\\Pictures\\Sample Pictures\\";
		//array of attachment file name
		String[] attachmentFiles = {"Desert.jpg", "JellyFish.jpg","Koala.jpg"};
		
		//new mail
		MimeMessage mail=new MimeMessage(this.initializeSession());
		
		//setting from address
		mail.setFrom(new InternetAddress("arun.thril@gmail.com", false));
		
		//setting to address
		mail.setRecipients(RecipientType.TO, InternetAddress.parse(String.join(",",user.getEmail())));
		
		//setting mail subject
		mail.setSubject("Mail with attachment from Spring");

		//mail body
		MimeBodyPart messagebodypart=new MimeBodyPart();
		
		//mail message
		messagebodypart.setContent("Bro multiple attachments added. Refer it."
				, "text/html");
		
		//mail attachments
		Multipart multipart=new MimeMultipart();
		multipart.addBodyPart(messagebodypart);
		
		/*
		 *  MimeBodyPart attachpart=new MimeBodyPart(); 
		 *  attachpart.attachFile("E:\\photos\\download.jfif");
		 * multipart.addBodyPart(attachpart);
		 */
		
		//adding multiple file attachment
		for(String attachmentFileName : attachmentFiles) {
			try {
				//trying to add attachment if any attachment is failed to attach then 
				//go to next attachment
				MimeBodyPart mailAttachment = new MimeBodyPart();
				
				//adding from the FILE_DIRECTORY_LOCATION with attachmentFileName
				//eg. Desert.jpg = C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg
				mailAttachment.attachFile(FILE_DIRECTORY_LOCATION+attachmentFileName);
				
				//add the attachment to the multipart
				multipart.addBodyPart(mailAttachment);
			}
			catch(IOException e) {
				continue;
			}
		}
		
		
		mail.setContent(multipart);
		javamailsender.send(mail);			
		}
	
}

