package com.example.prenotazione.service;

import java.io.File;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.example.prenotazione.model.Mail;

@Service
public class NotificationService {

	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendNotification(Mail Mail) throws MailException {
		//send mail
		SimpleMailMessage mail = new SimpleMailMessage();
		/*mail.setTo(Mail.getE_mail());
		mail.setFrom("prenotazione22@gmail.com");
		mail.setSubject("Prenotazione ResetPassword");
		mail.setText("http://localhost:9090/"+Mail.getE_mail()+"/ResetPassword");
		*/
		
		
		MimeMessagePreparator preparator = new MimeMessagePreparator()
		{
	        public void prepare(MimeMessage mimeMessage) throws Exception
	        {
	        	
	            String to=Mail.getE_mail();
				mimeMessage.setRecipients(Message.RecipientType.TO, Mail.getE_mail());
	            mimeMessage.setFrom("prenotazione22@gmail.com");
	            mimeMessage.setSubject("Prenotazione ResetPassword");
	            mimeMessage.setText("http://localhost:9090/"+Mail.getE_mail()+"/ResetPassword");

	          FileSystemResource file = new FileSystemResource(new File("C:/Users/Francesco/Desktop/Sample.jpg"));
	           //   MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	           //helper.addAttachment("Sample.jpg", file);
	        }
	    };


	    javaMailSender.send(preparator);
	    
		
		
	//	javaMailSender.send(mail);
	}
	
}
