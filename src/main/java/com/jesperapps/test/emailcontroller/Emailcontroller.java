package com.jesperapps.test.emailcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jesperapps.test.Emailservice.Emailservice;
import com.jesperapps.test.User.User;

@RestController
public class Emailcontroller {
	
	@Autowired
	private Emailservice emailservice;
	
	private Logger logger=LoggerFactory.getLogger(Emailcontroller.class);
	
	@RequestMapping("/signup")
	public String signup() {
		return "thank you for signing with us";
	}   
	
	@RequestMapping("/signup-success")
	public String signupsuccess() {
	
		//list of cc
		String[] ccList = {"arunkumar88967@gmail.com","samson.donbosco@gmail.com"};
		
		//create a user
		User user=new User();
		user.setFirstName("arun");
		user.setLastName("kumar");
		user.setEmail(ccList);
		
		//send a notification
		try {
		emailservice.sendnotification(user);
		}
		catch(Exception e) {
			//catch error
			logger.info("Error sending message:" +e.getMessage());
			return "Error sending mail message =>"+e.getMessage();
		}
		return "thanks for regisrating with us";
	}
}
