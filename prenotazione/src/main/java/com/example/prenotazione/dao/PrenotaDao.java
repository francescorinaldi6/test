package com.example.prenotazione.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
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
//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE posto p SET prenotabile = false  WHERE p.id_ufficio= :id_ufficio AND p.id_posto = :id_posto ", nativeQuery = true)
//	void prenotazioneFatta(@Param("id_ufficio") int id_ufficio, @Param("id_posto") int id_posto);
	
	@Query(value = "SELECT id_prenotazione FROM prenota p WHERE p.data_prenotazione = :data_prenotazione AND p.id_posto = :id_posto", nativeQuery = true)
	List<Integer> checkPrenotabile(@Param("data_prenotazione") Date data_prenotazione, @Param("id_posto") int id_posto);
	
	@Query(value = "SELECT id_prenotazione FROM prenota p WHERE p.data_prenotazione = :data_prenotazione AND p.id_utente= :id_utente", nativeQuery = true)
	List<Integer> checkGiaPrenotato(@Param("data_prenotazione") Date data_prenotazione, @Param("id_utente") int id_utente);

	@Query(value = "SELECT * FROM prenota p WHERE p.id_utente = :id", nativeQuery = true)
	List<Prenota> getPrenotazione(@Param("id") int id);
	
	
	@Query(value = "SELECT * FROM prenota p WHERE qr_code = :qr", nativeQuery = true)
	Prenota CeckQrCode(@Param("qr") String qr);
	
	
	@Query(value = "SELECT id_posto FROM posto p WHERE p.id_posto = :id", nativeQuery = true)
	List<Integer> postoExists(@Param("id") int id);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM prenota p WHERE p.id_utente = :id_utente and p.id_ufficio = :id_ufficio and p.data_prenotazione = :data", nativeQuery = true)
	void eliminaPrenotazione(@Param("id_utente") int id_utente, @Param("id_ufficio") int id_ufficio, @Param("data") Date data);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM prenota p WHERE p.id_prenotazione = :id", nativeQuery = true)
	void eliminaPrenotazioneScaduta(@Param("id") int id);
}
