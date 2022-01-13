
package com.example.prenotazione.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.sql.DataSource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.example.prenotazione.model.info;
import com.example.prenotazione.service.ServiceForgotPassword;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Utente")



public class UtenteController {
	
	public info ritorno = new info();
	@Autowired
	private ServiceForgotPassword notificationService;

	@Autowired
	private DataSource dataSource;
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
	
	@PostMapping("/login")
	
	public info logUtente(@RequestBody Utente utenti) {
	
		
		int ruolo = 0;
		
		if(dao.idUtenteLogin(utenti.getE_mail()) != null) {
			boolean match = encoder.matches(utenti.getPassword(),email.passwUtenteLogin(utenti.getE_mail()));
			if(match == true) {
				ritorno.setMessaggio("Login effettuato con successo");
				ruolo = dao.ruoloUtente(dao.idUtenteLogin(utenti.getE_mail()));
			}else {
				ritorno.setMessaggio("Credenziali errate");
			}
		}
		
		ritorno.setSuccess(ruolo);
		return ritorno;
		
	}
	
@PostMapping("/infoutente")
	
	public Utente infoUtente(@RequestBody String mail) {
	
		return dao.utenteFromEmail(mail);
		
	}
	
	@PostMapping("/signup")

	public info addUtente(@RequestBody Utente utenti) {
		

			utenti.setPassword(encoder.encode(utenti.getPassword())); // conversione da chiaro a criptato

			if (!dao.aziendaExists(utenti.id_azienda).isEmpty()) {

				if (email.e_mailExists(utenti.getE_mail()).isEmpty()) {
				
				dao.save(utenti);
				
				dao.InsertSiteUser(utenti.getId_utente());
				ritorno.setSuccess(1);
				ritorno.setMessaggio("Ti sei regitrato con successo "+utenti.getNome());
				return ritorno;
				}else {
					ritorno.setSuccess(0);
					ritorno.setMessaggio("Esiste già un account con questa mail");
					
					return ritorno;
				}
			} else {
				ritorno.setSuccess(0);
				ritorno.setMessaggio("L'azienda inserita non è stata registrata");
				return ritorno;
			}
		
	}

	@PostMapping("/ForgotPassword")
	public info ForgotPassword(@RequestBody Mail e_mail) throws MessagingException {

		
			if (!email.e_mailExists(e_mail.e_mail).isEmpty()) {
//                send email
				try {
					notificationService.sendNotification(e_mail);
				} catch (MailException e) {
					// catch error
				}

			} else {
				ritorno.setMessaggio("L'email inserita non appartiene a nessun utente") ;
				ritorno.setSuccess(0);
				return ritorno;
			}

			ritorno.setSuccess(1);
			ritorno.setMessaggio("Email inviata");
		return ritorno;

	}

	@GetMapping("/{id}/getPrenotazione")
	public List<Prenota> getPrenotazione(@PathVariable("id") int id) {

		return prenota.getPrenotazione(id);
	}
	

	
	@PostMapping("/{mail}/ResetPassword")
	public String ResetPassword(@PathVariable("mail") String mail, @RequestBody Password password) {
		
	password.setPassword(encoder.encode(password.getPassword()));
	
		dao.ResetPassword(email.IdutentedaMail(mail),password.getPassword());
		
		return "password aggiornata per l'utente" + password.getPassword()+"  "+email.IdutentedaMail(mail);

	}
	
	

}