
package com.example.prenotazione.controller;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

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
import com.example.prenotazione.dao.PostoDao;
import com.example.prenotazione.dao.PrenotaDao;
import com.example.prenotazione.dao.UtenteDao;
import com.example.prenotazione.model.Mail;
import com.example.prenotazione.model.Password;
import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Prenota;
import com.example.prenotazione.model.Utente;
import com.example.prenotazione.model.info;
import com.example.prenotazione.model.reset_password;
import com.example.prenotazione.service.ServiceForgotPassword;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@CrossOrigin(origins = "http://192.168.1.48:4200")
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
	private PostoDao posto;

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
					
					String codice = getRandomString(6);
					dao.setCodiceResetNull(dao.idUtenteLogin(e_mail.e_mail));
					dao.setCodiceReset(dao.idUtenteLogin(e_mail.e_mail), codice);
					notificationService.sendNotification(e_mail, codice);
					
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
			List<Prenota> lista = prenota.getPrenotazione(id);
			LocalDate todaysDate = LocalDate.now();
			int size = lista.size();
			for(int i=0; i<lista.size(); i++ ) {
				if(todaysDate.isBefore(lista.get(i).data_prenotazione.toLocalDate()) || todaysDate.isEqual(lista.get(i).data_prenotazione.toLocalDate()) ) {
					lista.get(i).setId_posto(posto.getNumerazionePostoById(lista.get(i).id_posto).get(0).numero_postazione);
				}else {
					lista.remove(i);
					i--;
				}
			}
		return lista;
	}
	

	
	@PostMapping("/{mail}/ResetPassword")
	public info ResetPassword(@PathVariable("mail") String mail, @RequestBody reset_password reset) {
		
		Password pass = new Password();
		
		if(reset.codice.equalsIgnoreCase(email.getCodice(email.IdutentedaMail(mail)))) {
			if(reset.conf_password.equalsIgnoreCase(reset.password)) {
				
				pass.setPassword(encoder.encode(reset.password));
				
				dao.ResetPassword(email.IdutentedaMail(mail),pass.getPassword());
				dao.setCodiceResetNull(email.IdutentedaMail(mail));
				
				ritorno.setSuccess(1);
				ritorno.setMessaggio("password aggiornata per l'utente "+email.IdutentedaMail(mail));
			}else {
				ritorno.setSuccess(0);
				ritorno.setMessaggio("Le password inserite non corrispondono");
			}
		}else {
				ritorno.setSuccess(0);
				ritorno.setMessaggio("Il codice inserito non corrisponde");
		}
		
		return ritorno;

	}
	
	 static String getRandomString(int i) 
	    { 
		     byte[] bytearray;
	        // bind the length 
	        bytearray = new byte[256];         
	        String mystring;
	        StringBuffer thebuffer;
	        String theAlphaNumericS;

	        new Random().nextBytes(bytearray); 

	        mystring 
	            = new String(bytearray, Charset.forName("UTF-8")); 
	            
	        thebuffer = new StringBuffer();
	        
	        //remove all spacial char 
	        theAlphaNumericS 
	            = mystring 
	                .replaceAll("[^A-Z0-9]", ""); 

	        //random selection
	        for (int m = 0; m < theAlphaNumericS.length(); m++) { 

	            if (Character.isLetter(theAlphaNumericS.charAt(m)) 
	                    && (i > 0) 
	                || Character.isDigit(theAlphaNumericS.charAt(m)) 
	                    && (i > 0)) { 

	                thebuffer.append(theAlphaNumericS.charAt(m)); 
	                i--; 
	            } 
	        } 

	        // the resulting string 
	        return thebuffer.toString(); 
	    } 
	
	

}