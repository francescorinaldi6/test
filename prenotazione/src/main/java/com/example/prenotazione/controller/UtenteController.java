
package com.example.prenotazione.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.prenotazione.model.Password;
import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Prenota;
import com.example.prenotazione.model.Utente;
import com.example.prenotazione.service.ServiceForgotPassword;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Utente")



public class UtenteController {
	@Autowired
	private ServiceForgotPassword notificationService;

	@Autowired
	private UtenteDao dao;
	@Autowired
	private PrenotaDao prenota;
	@Autowired
	private EmailDao email;

	@Autowired
	BCryptPasswordEncoder encoder;

	
	@RequestMapping("utenti")
	//@CrossOrigin(origins = "http://localhost:4200")
	public List<Utente> getUtenti() {
		
		return dao.utenti();
		
	}
	
	
	@PostMapping("/signup")

	public String addUtente(@RequestBody Utente utenti) {
		
		String message;

			utenti.setPassword(encoder.encode(utenti.getPassword())); // conversione da chiaro a criptato

			if (!dao.aziendaExists(utenti.id_azienda).isEmpty()) {

				if (email.e_mailExists(utenti.getE_mail()).isEmpty()) {
				
				dao.save(utenti);
				
				dao.InsertSiteUser(utenti.getId_utente());
				
				
				return "Utente " + (utenti).getId_utente() + " aggiunto";
				}else {
					message = "Esiste già un account con questa mail";
					return message;
				}
			} else {
				return "L'azienda inserita non è stata registrata";
			}
		
	}

	@PostMapping("/ForgotPassword")
	public String ForgotPassword(@RequestBody Mail e_mail) throws MessagingException {

		
			if (!email.e_mailExists(e_mail.e_mail).isEmpty()) {
//                send email
				try {
					notificationService.sendNotification(e_mail);
				} catch (MailException e) {
					// catch error
				}

			} else {
				return "L'email inserita non appartiene a nessun utente";
			}


		return "ok" + e_mail.getE_mail();

	}

	@GetMapping("/{id}/getPrenotazione")
	public Prenota getPrenotazione(@PathVariable("id") int id) {

		return prenota.getPrenotazione(id);
	}
	

	
	@PostMapping("/{mail}/ResetPassword")
	public String ResetPassword(@PathVariable("mail") String mail, @RequestBody Password password) {
		
	password.setPassword(encoder.encode(password.getPassword()));
	
		dao.ResetPassword(email.IdutentedaMail(mail),password.getPassword());
		
		return "password aggiornata per l'utente" + password.getPassword()+"  "+email.IdutentedaMail(mail);

	}
	
	

}