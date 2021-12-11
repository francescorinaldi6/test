package com.example.prenotazione.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Utente;

public interface EmailDao extends CrudRepository<Utente, String> {

	@Query(value = "SELECT e_mail FROM utente u WHERE u.e_mail = :mail", nativeQuery = true)
	List<String> e_mailExists(@Param("mail") String e_mail);

}
