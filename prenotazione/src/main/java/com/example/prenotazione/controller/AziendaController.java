package com.example.prenotazione.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.AziendaDao;
import com.example.prenotazione.model.Azienda;
import com.example.prenotazione.model.Ufficio;

@RestController
@RequestMapping("/Azienda")

public class AziendaController {
	@Autowired
	private AziendaDao dao;
	
	@PostMapping("/addAziende")
	public String addAziende(@RequestBody List<Azienda> aziende) {
		
		for (int i=0; i<aziende.size(); i++)
		dao.save(aziende.get(i));
		return  "Sono state aggiunte " + aziende.size() + " aziende";
	}
	
	@GetMapping("/getAziende")
	public List<Azienda> getAziende() {
		return (List<Azienda>) dao.findAll();
	}
	
	@GetMapping("/{id}/getUffici")
	public List<Object> getUfficiAziende(@PathVariable("id") int id) {

		return dao.getUfficiPerAziende(id);
	}
	
}
