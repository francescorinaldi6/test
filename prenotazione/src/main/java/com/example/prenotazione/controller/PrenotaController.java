package com.example.prenotazione.controller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.PostoDao;
import com.example.prenotazione.dao.PrenotaDao;
import com.example.prenotazione.dao.UfficioDao;
import com.example.prenotazione.model.Azienda;
import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Prenota;
import com.example.prenotazione.model.Ufficio;



@RestController
@RequestMapping("/Prenota")


public class PrenotaController {
	@Autowired
	private PrenotaDao dao;

	
	@PostMapping("/{id_utente}/{id_azienda}/{id_ufficio}")
//	public String creaPrenotazione(@RequestBody List<postoid> p, @PathVariable("id_azienda") int id_azienda,@PathVariable("id_ufficio") int id_ufficio) {
		public String creaPrenotazione(@RequestBody List<Prenota> p, @PathVariable("id_utente") int id_utente,@PathVariable("id_ufficio") int id_ufficio) {
		
		p.get(0).setId_utente(id_utente);
		p.get(0).setId_ufficio(id_ufficio);
		dao.save(p.get(0));
		dao.prenotazioneFatta(id_ufficio,p.get(0).getId_posto() );

		return  "Hai effettuato la tua prenotazione al posto "+p.get(0).getId_posto();
	}



}