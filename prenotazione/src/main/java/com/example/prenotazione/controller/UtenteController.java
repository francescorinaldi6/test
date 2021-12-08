package com.example.prenotazione.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.UtenteDao;
import com.example.prenotazione.model.Utente;

@RestController
@RequestMapping("/Utente")

public class UtenteController {

	@Autowired
	private UtenteDao dao;
	
	@PostMapping("/signup")
	public String addUtente(@RequestBody List<Utente> utenti) {
		
		for (int i=0; i<utenti.size(); i++)
			dao.save(utenti.get(i));
		return  "Utente "+(utenti.get(0)).getId_utente()+" aggiunto";
		
	}
	@PostMapping("/login")
	public String Login(@ModelAttribute("e_mail") List<Utente> utenti) {
		
		for (int i=0; i<utenti.size(); i++)
			dao.save(utenti.get(i));
		return  "Utente "+(utenti.get(0)).getId_utente()+" aggiunto";
		
	}
	
	
}
