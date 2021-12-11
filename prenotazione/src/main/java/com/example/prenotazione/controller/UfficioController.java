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
	public String addUffici(@RequestBody List<Ufficio> uffici) {
		Posto postazione = new Posto();
		for (int i = 0; i < uffici.size(); i++) {

			if (!dao.aziendaExists(uffici.get(i).id_azienda).isEmpty()) {
				dao.save(uffici.get(i));

				for (int j = 1; j < ((uffici.get(i)).getN_posti()) + 1; j++) {

					postazione.setNumero_postazione(j);
					postazione.setPrenotabile(true);
					postazione.setId_ufficio((uffici.get(i)).getId_ufficio());
					posto.save(postazione);
				}
				postazione.setNumero_postazione(20);
				postazione.setPrenotabile(false);
				postazione.setId_ufficio((uffici.get(i)).getId_ufficio());
				posto.save(postazione);

				return "Sono stati aggiunti " + uffici.size() + " uffici";
			} else {
				return "L'azienda inserita non è stata registrata";
			}
		}

		return "";
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
