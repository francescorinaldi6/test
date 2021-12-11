package com.example.prenotazione.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.example.prenotazione.model.Ufficio;
import com.example.prenotazione.model.Azienda;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Prenota;

public interface PrenotaDao extends CrudRepository<Prenota, Integer> {
	@Transactional
	@Modifying
	@Query(value = "UPDATE posto p SET prenotabile = false  WHERE p.id_ufficio= :id_ufficio AND p.id_posto = :id_posto ", nativeQuery = true)
	void prenotazioneFatta(@Param("id_ufficio") int id_ufficio, @Param("id_posto") int id_posto);

	@Query(value = "SELECT * FROM prenota p WHERE p.id_utente = :id", nativeQuery = true)
	Prenota getPrenotazione(@Param("id") int id);

}
