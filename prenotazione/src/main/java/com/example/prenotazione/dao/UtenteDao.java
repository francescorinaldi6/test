package com.example.prenotazione.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.prenotazione.model.Utente;

public interface UtenteDao extends CrudRepository<Utente, Integer>{
	
	//SELECT * FROM prenotazione.auth_user_role INNER JOIN prenotazione.utente ON prenotazione.auth_user_role.auth_user_id=prenotazione.utente.id_utente

}
