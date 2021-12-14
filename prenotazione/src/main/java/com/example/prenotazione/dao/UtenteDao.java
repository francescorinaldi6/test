package com.example.prenotazione.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Utente;

public interface UtenteDao extends CrudRepository<Utente, Integer> {

	// SELECT * FROM prenotazione.auth_user_role INNER JOIN prenotazione.utente ON
	// prenotazione.auth_user_role.auth_user_id=prenotazione.utente.id_utente
	@Query(value = "SELECT id_azienda FROM azienda a WHERE a.id_azienda = :id", nativeQuery = true)
	List<Integer> aziendaExists(@Param("id") int id);

	
	@Query(value = "SELECT * FROM utente u ", nativeQuery = true)
	List<Utente> utenti();
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE utente u  SET password = :password WHERE u.id_utente = :id", nativeQuery = true)
	void ResetPassword(@Param("id") int id, @Param("password") String password );
	
}
