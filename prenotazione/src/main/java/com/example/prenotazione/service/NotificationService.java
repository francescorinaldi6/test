package com.example.prenotazione.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
		mail.setTo(Mail.getE_mail());
		mail.setFrom("lalibreria2020@gmail.com");
		mail.setSubject("Prenotazione ResetPassword");
		mail.setText("http://localhost:9090/"+Mail.getE_mail()+"/ResetPassword");
		
		javaMailSender.send(mail);
	}
	
}
