package com.jesperapps.test.Emailservice;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
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

	
	public void sendnotification(User user)throws MailException {
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom("arun.thril@gmail.com");
		mail.setSubject("test");
		mail.setText("hi da bro");
		javamailsender.send(mail);
		
		
		Multipart multipart=new MimeMultipart();
		
	
		multipart.addBodyPart(attachpart);
		
		MimeBodyPart attachpart=new MimeBodyPart();
		attachpart.attachFile("E:\\photos\\download.jfif");
		multipart.addBodyPart(attachpart);
		attachpart.setContent(multipart);
		Transport.send(msg);
		
		
			
			
		}
	
}

