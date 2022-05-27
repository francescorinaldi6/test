
package com.example.prenotazione.service;



	import java.io.File;
import java.nio.charset.Charset;
import java.util.Random;

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
		
		public void sendNotification(Mail Mail, String text, String title, String location, String date) throws MailException {

			SimpleMailMessage mail = new SimpleMailMessage();

			
	
			MimeMessage message = javaMailSender.createMimeMessage();
			 try{
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
						
					String UrlCalendar = "https://www.google.com/calendar/render?action=TEMPLATE&text="+title+"&location="+location+"&dates="+(date.replace("-", ""))+"T192300Z%2F"+(date.replace("-", ""))+"T192300Z";
					
					
					helper.setFrom("prenotazione22@gmail.com");
					helper.setTo(Mail.getE_mail());
					helper.setSubject("Qr Prenotazione"); 
					
					//helper.setText(text);    //dare info sulla prenotazione passando parametri
					helper.setText("<html> <body><b>"+title+" in "+location+" il giorno "+date+" </b>   </body> <form>\r\n" 
						       + "    <a href=\""+UrlCalendar+"\">\r\n" 
						       + "        <input type=\"button\" value=\"Clicca qui \" style=\"border-radius: 10px 10px 10px 10px; background-color: rgb(27, 30, 230); color: #fff;\">\r\n" 
						       + "    </a>\r\n" 
						       + " <a>per aggiungere l'evento al tuo calendario </a>\r\n" 
						       + "</form> </html> ",true);
					
						
					FileSystemResource file = new FileSystemResource("../prenotazione/QrCode.jpg");
							helper.addAttachment(file.getFilename(), file);
							//message.setContent("<h1>Questo è il corpo del messaggio HTML</h1>", "text/html");
				     }catch (MessagingException e) {
					throw new MailParseException(e);
				     }
				     javaMailSender.send(message);
		
		}
		
		
	}

	

