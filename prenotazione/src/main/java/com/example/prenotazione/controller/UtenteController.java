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

	public String addUtente(@RequestBody List<Utente> utenti) {

		for (int i = 0; i < utenti.size(); i++) {

			utenti.get(i).setPassword(encoder.encode(utenti.get(i).getPassword())); // conversione da chiaro a criptato

		}

		for (int i = 0; i < utenti.size(); i++) {

			if (!dao.aziendaExists(utenti.get(i).id_azienda).isEmpty()) {

				if (email.e_mailExists(utenti.get(i).getE_mail()).isEmpty()) {
				
				dao.save(utenti.get(i));
				
				//dao.InsertSiteUser(utenti.get(i).getId_utente());
				
				
				return "Utente " + (utenti.get(0)).getId_utente() + " aggiunto";
				}else {
					return "Esiste già un account con questa mail";
				}
			} else {
				return "L'azienda inserita non è stata registrata";
			}
		}
		return "ok";
	}

	@PostMapping("/ForgotPassword")
	public String ForgotPassword(@RequestBody List<Mail> e_mail) throws MessagingException {

		for (int i = 0; i < e_mail.size(); i++) {

			if (!email.e_mailExists(e_mail.get(i).e_mail).isEmpty()) {
//                send email
				try {
					notificationService.sendNotification(e_mail.get(0));
				} catch (MailException e) {
					// catch error
				}

			} else {
				return "L'email inserita non appartiene a nessun utente";
			}

		}

		return "ok" + e_mail.get(0).getE_mail();

	}

	@GetMapping("/{id}/getPrenotazione")
	public Prenota getPrenotazione(@PathVariable("id") int id) {

		return prenota.getPrenotazione(id);
	}
	

	
	@PostMapping("/{mail}/ResetPassword")
	public String ResetPassword(@PathVariable("mail") String mail, @RequestBody List<Password> password) {
		for (int i = 0; i < password.size(); i++) {
	password.get(i).setPassword(encoder.encode(password.get(i).getPassword()));
	
		dao.ResetPassword(email.IdutentedaMail(mail).get(i),password.get(i).getPassword());
		}
		return "password aggiornata per l'utente" + password.get(0).getPassword()+"  "+email.IdutentedaMail(mail).get(0);

	}
	
	
}