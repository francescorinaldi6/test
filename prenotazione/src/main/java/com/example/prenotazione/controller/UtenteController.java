package com.example.prenotazione.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.EmailDao;
import com.example.prenotazione.dao.PrenotaDao;
import com.example.prenotazione.dao.UtenteDao;
import com.example.prenotazione.model.Mail;
import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Prenota;
import com.example.prenotazione.model.Utente;
import com.example.prenotazione.service.NotificationService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/Utente")

public class UtenteController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UtenteDao dao;
	@Autowired
	private PrenotaDao prenota;
	@Autowired
	private EmailDao email;
	
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@PostMapping("/signup")
	

	public String addUtente(@RequestBody List<Utente> utenti) {
		
		for (int i=0; i<utenti.size(); i++) {

	utenti.get(i).setPassword(encoder.encode(utenti.get(i).getPassword())); //conversione da chiaro a criptato

}
	
		for (int i=0; i<utenti.size(); i++) {

			if(!dao.aziendaExists(utenti.get(i).id_azienda).isEmpty()) {
			
			dao.save(utenti.get(i));
		
		return  "Utente "+(utenti.get(0)).getId_utente()+" aggiunto";
			} else {
				return "L'azienda inserita non è stata registrata";
			}
		}
		return "ok";
	}

	

	
	@PostMapping("/ForgotPassword")
	public String ForgotPassword(@RequestBody List<Mail> e_mail) {
		
		for (int i=0; i<e_mail.size(); i++) {
			
			if(!email.e_mailExists(e_mail.get(i).e_mail).isEmpty()) {
//				send email
				try {
					notificationService.sendNotification(e_mail.get(0));
				} catch(MailException e) {
					//catch error
				}
				
		
		
		
			} else {
				return "L'email inserita non appartiene a nessun utente";
			}
			
		}
		
		
		
		
		return  "ok"+e_mail.get(0).getE_mail();
		
	}
	@GetMapping("/{id}/getPrenotazione")
	public Prenota getPrenotazione(@PathVariable("id") int id) {
		
		return prenota.getPrenotazione(id);
	}
	
	
	
	
}
