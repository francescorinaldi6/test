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
import com.example.prenotazione.dao.UfficioDao;
import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Ufficio;

@RestController
@RequestMapping("/Ufficio")

public class UfficioController {
	@Autowired
	private UfficioDao dao;
	@Autowired
	private PostoDao posto;

	@PostMapping("/addUffici")
	public String addUffici(@RequestBody Ufficio uffici) {
		Posto postazione = new Posto();

			if (!dao.aziendaExists(uffici.id_azienda).isEmpty()) {
				dao.save(uffici);

				for (int j = 1; j < ((uffici).getN_posti()) + 1; j++) {

					postazione.setNumero_postazione(j);
					postazione.setId_ufficio((uffici).getId_ufficio());
					posto.save(postazione);
				}

				return "L'ufficio è stato aggiunto";
				
			} else {
				
				return "L'azienda inserita non è stata registrata";
				
			}
		
	}

	@GetMapping("/getUffici")
	public List<Ufficio> getUffici() {
		return (List<Ufficio>) dao.getUfficiPerAziende();
	}

	@GetMapping("/{id}/getPostiTot")
	public List<Posto> getPostiTot(@PathVariable("id") int id) {

		return posto.getPostiTotali(id);
	}

	@GetMapping("/{id}/getPostiDisp")
	public List<Posto> getPostiDisp(@PathVariable("id") int id) {

		return posto.getPostiDisponibili(id);
	}
}
