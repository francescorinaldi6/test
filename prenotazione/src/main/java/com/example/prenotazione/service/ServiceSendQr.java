package com.example.prenotazione.service;

	import java.io.File;

<<<<<<< HEAD
	import javax.mail.Message;
=======
import net.glxn.qrgen.core.image.ImageType;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.glxn.qrgen.javase.QRCode;

import javax.mail.Message;
>>>>>>> branch 'main' of git@github.com:francescorinaldi6/test.git
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
		
<<<<<<< HEAD
=======
//		public void qrGenerator(String string) {
//			 try {
//		            int size=300;
//		            File file = QRCode.from("IL TESTO CHE VUOI!")
//		                    .to(ImageType.PNG)
//		                    .withSize(size, size)
//		                    .file();
//
//		            String fileName = "/home/test/qrcode.png";
//
//		            Path path = Paths.get(fileName);
//		            if (Files.exists(path)) {
//		                Files.delete(path);
//		            }
//		            Files.copy(file.toPath(), path);
//		        } catch (IOException e) {
//		            System.out.println(e.getMessage());
//		        }
//		}
		
>>>>>>> branch 'main' of git@github.com:francescorinaldi6/test.git
		public void sendNotification(Mail Mail) throws MailException {

			SimpleMailMessage mail = new SimpleMailMessage();

			
	
			MimeMessage message = javaMailSender.createMimeMessage();
			 try{
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
						
					helper.setFrom("prenotazione22@gmail.com");
					helper.setTo(Mail.getE_mail());
					helper.setSubject("Qr Prenotazione"); 
					helper.setText("Qr della prenotazione: ");    //dare info sulla prenotazione passando parametri
<<<<<<< HEAD
						
					FileSystemResource file = new FileSystemResource("C:/Users/Francesco/git/test/prenotazione/QrCode.jpg");
=======
					int size=300;
					 File file = QRCode.from("IL TESTO CHE VUOI!")
			                    .to(ImageType.PNG)
			                    .withSize(size, size)
			                    .file();
					  try {
				            
				            String fileName = "/home/test/qrcode.png";

				            Path path = Paths.get(fileName);
				            if (Files.exists(path)) {
				                Files.delete(path);
				            }
				            Files.copy(file.toPath(), path);
				        } catch (IOException e) {
				            System.out.println(e.getMessage());
				        }
//					FileSystemResource file = new FileSystemResource(file);  //metti il file del qr
					helper.addAttachment(file.getName(), file);
>>>>>>> branch 'main' of git@github.com:francescorinaldi6/test.git

				     }catch (MessagingException e) {
					throw new MailParseException(e);
				     }
				     javaMailSender.send(message);
		
		}
		
	}