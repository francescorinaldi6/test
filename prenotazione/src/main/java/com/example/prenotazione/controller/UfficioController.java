package com.example.prenotazione.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.UfficioDao;
import com.example.prenotazione.model.Ufficio;

@RestController
@RequestMapping("/Ufficio")

public class UfficioController {
	@Autowired
	private UfficioDao dao;
	
	@PostMapping("/addUffici")
	public String addUffici(@RequestBody List<Ufficio> uffici) {
		
		for (int i=0; i<uffici.size(); i++) {
			
		if(!dao.aziendaExists(uffici.get(i).id_azienda).isEmpty()) {
		dao.save(uffici.get(i));
		return  "Sono stati aggiunti " + uffici.size() + " uffici";
		}else {
			return "L'azienda inserita non è stata registrata";
		}
		}
		return "Inserimento completato";
	}
	
	@GetMapping("/getUffici")
	public List<Ufficio> getUffici() {
		return (List<Ufficio>) dao.findAll();
	}
}
