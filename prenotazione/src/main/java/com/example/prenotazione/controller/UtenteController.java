package com.example.prenotazione.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.PrenotaDao;
import com.example.prenotazione.dao.UtenteDao;
import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Prenota;
import com.example.prenotazione.model.Utente;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/Utente")

public class UtenteController {
	
	@Autowired
	private UtenteDao dao;
	@Autowired
	private PrenotaDao prenota;
	
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
		return "";
	}

	@PostMapping("/login")
	public String Login(@ModelAttribute("e_mail") List<Utente> utenti) {
		
		for (int i=0; i<utenti.size(); i++)
			dao.save(utenti.get(i));
		return  "Utente "+(utenti.get(0)).getId_utente()+" aggiunto";
		
	}
	@GetMapping("/{id}/getPrenotazione")
	public Prenota getPrenotazione(@PathVariable("id") int id) {
		
		return prenota.getPrenotazione(id);
	}
	
	
}
