package com.jesperapps.test.Emailservice;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
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
		Message mail=new MimeMessage(this.initializeSession());
		mail.setFrom(new InternetAddress("arun.thril@gmail.com", false));
		
		mail.setRecipients(RecipientType.TO, InternetAddress.parse(String.join(",",user.getEmail())));
		mail.setSubject("Mail with attachment from Spring");

		
		MimeBodyPart messagebodypart=new MimeBodyPart();
		messagebodypart.setContent("hi how are you man,hope you are busy"
				, "text/html");
		
		Multipart multipart=new MimeMultipart();
		multipart.addBodyPart(messagebodypart);
		MimeBodyPart attachpart=new MimeBodyPart();
		attachpart.attachFile("E:\\photos\\download.jfif");
		multipart.addBodyPart(attachpart);
		mail.setContent(multipart);
		Transport.send(mail);
		
		
			
			
		}
	
}

