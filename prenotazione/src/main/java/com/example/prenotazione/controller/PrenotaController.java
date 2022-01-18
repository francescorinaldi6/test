package com.example.prenotazione.controller;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.EmailDao;
import com.example.prenotazione.dao.PostoDao;
import com.example.prenotazione.dao.PrenotaDao;
import com.example.prenotazione.dao.UfficioDao;
import com.example.prenotazione.model.Azienda;
import com.example.prenotazione.model.Mail;
import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Prenota;
import com.example.prenotazione.model.Ufficio;
import com.example.prenotazione.model.info;
import com.example.prenotazione.service.QRCodeGenerator;
import com.example.prenotazione.service.ServiceForgotPassword;
import com.example.prenotazione.service.ServiceSendQr;
import com.google.zxing.WriterException;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Prenota")

public class PrenotaController {
	
	public info ritorno = new info();
	@Autowired
	private PrenotaDao dao;
	@Autowired
	private EmailDao email;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	private static final String QR_CODE_IMAGE_PATH ="../prenotazione/QrCode.jpg";
	@Autowired
	private ServiceSendQr notificationService;

	
	
	public String generateRandomString() {
		 int leftLimit = 48; // numeral '0'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 20;
		    Random random = new Random();

		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();

		    System.out.println(generatedString);
		
	    return generatedString;
	}

	
	@PostMapping("/{id_utente}/{id_azienda}/{id_ufficio}")
	public info creaPrenotazione(@RequestBody Prenota p, @PathVariable("id_utente") int id_utente,@PathVariable("id_ufficio") int id_ufficio) {

			
			if(!dao.postoExists(p.getId_posto()).isEmpty()) {
				
				if(dao.checkGiaPrenotato(p.getData_prenotazione(), id_utente).isEmpty()) {
					
					 if(dao.checkPrenotabile(p.getData_prenotazione(), p.getId_posto()).isEmpty()) {
						 
						 String criptata = encoder.encode(generateRandomString());
							p.setId_utente(id_utente);
							p.setId_ufficio(id_ufficio);
							p.setQrCode(criptata);
							dao.save(p);

					        byte[] image = new byte[0];
					        try {

					            // Generate and Return Qr Code in Byte Array
					            image = QRCodeGenerator.getQRCodeImage(p.getQrCode(),250,250);

					            // Generate and Save Qr Code Image in static/image folder
					            QRCodeGenerator.generateQRCodeImage(p.getQrCode(),250,250,QR_CODE_IMAGE_PATH);

					        } catch (WriterException | IOException e) {
					            e.printStackTrace();
					        }
					        // Convert Byte Array into Base64 Encode String
					        String qrcode = Base64.getEncoder().encodeToString(image);
					        
					        System.out.println(email.getEmail(id_utente).getE_mail());
					        Mail mail = new Mail();
					        mail.setE_mail(email.getEmail(id_utente).getE_mail());
					        String text = ("Prenotazione effettuata per il giorno: "+p.getData_prenotazione()+" al posto: "+p.getId_posto()+" nell'ufficio: "+id_ufficio);
					        
					       notificationService.sendNotification(mail,text);
					        
							
							ritorno.setMessaggio( "Hai effettuato la tua prenotazione al posto " + p.getId_posto());
							ritorno.setSuccess(1);
							return ritorno;
						}else {
							ritorno.setMessaggio("deve scegliere un altro posto, quello da lei inserito è già occupato per la data "+p.getData_prenotazione());
							ritorno.setSuccess(0);
							return ritorno;
						}
				}  else {
					ritorno.setMessaggio("Hai gia prenotato un posto per la data "+p.getData_prenotazione());
					ritorno.setSuccess(0);
					return ritorno;
				}
			}else {
				ritorno.setMessaggio("Non esiste il posto "+p.getId_posto());
				ritorno.setSuccess(0);
				return ritorno;
			}

	}
	//
	
	@PostMapping("/{id_utente}/{id_azienda}/{id_ufficio}/elimina")
	public info eliminaPrenotazione(@RequestBody Prenota p, @PathVariable("id_utente") int id_utente,@PathVariable("id_ufficio") int id_ufficio) {
		
		dao.eliminaPrenotazione(id_utente, id_ufficio, p.getData_prenotazione());
		ritorno.setMessaggio("La prenotazione in data "+p.getData_prenotazione()+ " è stata annullata");
		ritorno.setSuccess(1);
		return ritorno;
	}

    @GetMapping("/")
    public String getQRCode(Model model){
 


    
        return "qrcode";
    }
	

}