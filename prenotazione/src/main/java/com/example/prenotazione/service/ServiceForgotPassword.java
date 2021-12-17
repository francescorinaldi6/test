package com.example.prenotazione.service;

import java.io.File;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.example.prenotazione.model.Mail;

@Service
public class ServiceForgotPassword {

	private JavaMailSender javaMailSender;
	private SimpleMailMessage simpleMailMessage;
	
	@Autowired
	public ServiceForgotPassword(JavaMailSender javaMailSender) {
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
		
		
	
		MimeMessage message = javaMailSender.createMimeMessage();
		 try{
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
					
				helper.setFrom("prenotazione22@gmail.com");
				helper.setTo(Mail.getE_mail());
				helper.setSubject("Prenotazione ResetPassword");
				helper.setText("http://localhost:9090/"+Mail.getE_mail()+"/ResetPassword");
					
				
			//	FileSystemResource file = new FileSystemResource("../prenotazione/QrCode.jpg");
		//		FileSystemResource file = new FileSystemResource("C:/Users/Francesco/git/test/prenotazione/QrCode.jpg");
			//	helper.addAttachment(file.getFilename(), file);

			     }catch (MessagingException e) {
				throw new MailParseException(e);
			     }
			     javaMailSender.send(message);
		
	//	javaMailSender.send(mail);
	}
	
}
