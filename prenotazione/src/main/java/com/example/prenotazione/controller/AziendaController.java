package com.example.prenotazione.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.AziendaDao;
import com.example.prenotazione.dao.UfficioDao;
import com.example.prenotazione.model.Azienda;
import com.example.prenotazione.model.Ufficio;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Azienda")


public class AziendaController {
	@Autowired
	private AziendaDao dao;
	@Autowired
	private UfficioDao dao_uff;

	@PostMapping("/addAziende")
	public String addAziende(@RequestBody Azienda aziende) {

		
	
			if(dao.pivaAlreadyInsert(aziende.getP_iva()).isEmpty()) {
				dao.save(aziende);
			}else {
				return "Hai gia inserito questa azienda";
			}
		
		return "Sono state aggiunte " + aziende + " aziende";
	}
	
	@GetMapping("/getAziende")
	public List<Azienda> getAziende() {
		return (List<Azienda>) dao.findAll();
	}

	@GetMapping("/{id}/getUffici")
	public List<Ufficio> getUfficiAziende(@PathVariable("id") int id) {

		return dao_uff.getUfficiPerAziende(id);
	}

}
