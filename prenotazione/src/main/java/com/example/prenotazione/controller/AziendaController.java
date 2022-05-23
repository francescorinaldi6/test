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
import com.example.prenotazione.model.info;
@CrossOrigin(origins = "http://192.168.1.25:4200")
@RestController
@RequestMapping("/Azienda")


public class AziendaController {
	@Autowired
	private AziendaDao dao;
	@Autowired
	private UfficioDao dao_uff;
	
	public info ritorno = new info();
	public Azienda azienda = new Azienda();
	
	@PostMapping("/addAziende")
	public info addAziende(@RequestBody Azienda aziende) {

			if(dao.pivaAlreadyInsert(aziende.getP_iva()).isEmpty()) {
				dao.save(aziende);
			}else {
				ritorno.setSuccess(0);
				ritorno.setMessaggio("partita iva già registrata");
				return ritorno;
			}
			ritorno.setSuccess(1);
			ritorno.setMessaggio("Azienda inserita");
			return ritorno;
	}
	
//	@PostMapping("/{id}/eliminaAzienda")
//	public info eliminaAzienda(@PathVariable("id") int id) {
//		
//		dao.eliminaPostiUfficioAzienda(id);
//		dao.eliminaPrenotazioniUfficioAzienda(id);
//		dao.eliminaUfficiAzienda(id);
//		dao.eliminaAzienda(id);
//		
//		
//		ritorno.setMessaggio("L'ufficio e i relativi posti sono stati rimossi");
//		ritorno.setSuccess(1);
//		return ritorno;
//	}
	
	@GetMapping("/{id}/getNome")
	public Azienda getNomeAzienda(@PathVariable("id") int id) {
		azienda.setNome(dao.getNomeById(id));
		return azienda;
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
