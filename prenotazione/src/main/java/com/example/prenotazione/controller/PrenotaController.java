package com.example.prenotazione.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.PostoDao;
import com.example.prenotazione.dao.PrenotaDao;
import com.example.prenotazione.dao.UfficioDao;
import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Ufficio;

@RestController
@RequestMapping("/Prenota")

public class PrenotaController {
	@Autowired
	private PrenotaDao dao;
	
	@PostMapping("/{id_utente}/{id_azienda}/{id_ufficio}")
	public String creaPrenotazione(@RequestBody int id_posto, @PathVariable("id_azienda") int id_azienda,@PathVariable("id_ufficio") int id_ufficio) {
		
		System.out.println("post "+id_posto);
//		dao.prenotazioneFatta(id_ufficio, id_posto.getId_posto());

		return  "Hai effettuato la tua prenotazione";
	}
}
