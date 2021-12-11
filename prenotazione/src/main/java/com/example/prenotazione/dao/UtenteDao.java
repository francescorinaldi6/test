package com.example.prenotazione.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Utente;

public interface UtenteDao extends CrudRepository<Utente, Integer>{
	
	//SELECT * FROM prenotazione.auth_user_role INNER JOIN prenotazione.utente ON prenotazione.auth_user_role.auth_user_id=prenotazione.utente.id_utente
	@Query(value="SELECT id_azienda FROM azienda a WHERE a.id_azienda = :id", nativeQuery = true) List<Integer> aziendaExists(@Param("id") int id);
	
	
	@Query(value="SELECT e_mail FROM utente u WHERE u.e_mail = :mail", nativeQuery = true) List<Integer> e_mailExists(@Param("mail") String e_mail);
	
}
