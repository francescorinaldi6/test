package com.example.prenotazione.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String addUtente(@RequestBody Utente utente) {
		
		
		dao.save(utente);
		return  "Utente "+utente.getId_utente()+" aggiunto";
		
	}
	
	
}
