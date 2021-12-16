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
	public class ServiceSendQr {

		private JavaMailSender javaMailSender;
		private SimpleMailMessage simpleMailMessage;
		
		@Autowired
		public ServiceSendQr(JavaMailSender javaMailSender) {
			this.javaMailSender = javaMailSender;
		}
		
		public void sendNotification(Mail Mail) throws MailException {

			SimpleMailMessage mail = new SimpleMailMessage();

			
	
			MimeMessage message = javaMailSender.createMimeMessage();
			 try{
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
						
					helper.setFrom("prenotazione22@gmail.com");
					helper.setTo(Mail.getE_mail());
					helper.setSubject("Qr Prenotazione"); 
					helper.setText("Qr della prenotazione: ");    //dare info sulla prenotazione passando parametri
						
					FileSystemResource file = new FileSystemResource("C:/Users/Francesco/git/test/prenotazione/QrCode.jpg");

				     }catch (MessagingException e) {
					throw new MailParseException(e);
				     }
				     javaMailSender.send(message);
		
		}
		
	}

	

