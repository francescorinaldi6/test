package com.example.prenotazione.controller;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	BCryptPasswordEncoder encoder;

	
	
	public String generateRandomString() {
		 int leftLimit = 48; // numeral '0'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 20;
		    Random random = new Random();

		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();

		    System.out.println(generatedString);
		
	    return generatedString;
	}

	@PostMapping("/{id_utente}/{id_azienda}/{id_ufficio}")
	public String creaPrenotazione(@RequestBody List<Prenota> p, @PathVariable("id_utente") int id_utente,@PathVariable("id_ufficio") int id_ufficio) {

	
		
		
		for(int i=0; i<p.size();i++) {
			
			if(!dao.postoExists(p.get(i).getId_posto()).isEmpty()) {
				if(dao.checkGiaPrenotato(p.get(i).getData_prenotazione(), id_utente).isEmpty()) {
					 if(dao.checkPrenotabile(p.get(i).getData_prenotazione(), p.get(i).getId_posto()).isEmpty()) {
						 String criptata = encoder.encode(generateRandomString());
							p.get(i).setId_utente(id_utente);
							p.get(i).setId_ufficio(id_ufficio);
							p.get(i).setQrCode(criptata);
							dao.save(p.get(i));
							
							
							return "Hai effettuato la tua prenotazione al posto " + p.get(i).getId_posto()+ "     "+criptata;
						}else {
							return "deve scegliere un altro posto, quello da lei inserito è già occupato per la data "+p.get(i).getData_prenotazione();
						}
				}  else {
					return "Hai gia prenotato un posto per la data "+p.get(i).getData_prenotazione();
				}
			}else {
				return "Non esiste il posto "+p.get(i).getId_posto();
			}
		}
		return "";
	}

}