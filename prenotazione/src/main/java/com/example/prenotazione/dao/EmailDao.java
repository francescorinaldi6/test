package com.example.prenotazione.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Utente;

public interface EmailDao extends CrudRepository<Utente, String> {

	@Query(value = "SELECT e_mail FROM utente u WHERE u.e_mail = :mail", nativeQuery = true)
	List<String> e_mailExists(@Param("mail") String e_mail);
   
	@Query(value = "SELECT * FROM utente u WHERE u.id_utente = :id_utente", nativeQuery = true)
	Utente getEmail(@Param("id_utente") int id_utente);
	
	@Query(value = "SELECT codice FROM recupera_password u WHERE u.id_utente = :id_utente", nativeQuery = true)
	String getCodice(@Param("id_utente") int id_utente);
	
	@Query(value = "SELECT id_utente FROM utente u where u.e_mail = :mail" ,nativeQuery = true)
	Integer IdutentedaMail(@Param("mail") String e_mail);
	
	@Query(value = "SELECT password FROM utente u WHERE u.e_mail = :e_mail", nativeQuery = true)
	String passwUtenteLogin(@Param("e_mail") String e_mail);
}
