package com.example.prenotazione.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.prenotazione.model.Azienda;
import com.example.prenotazione.model.Ufficio;

public interface AziendaDao extends CrudRepository<Azienda, Integer>{

	@Query(value = "SELECT * FROM azienda a WHERE a.p_iva = :p_iva", nativeQuery = true)
	List<Azienda> pivaAlreadyInsert(@Param("p_iva") int p_iva);
	
//	@Transactional
//	@Modifying
//	@Query(value = "DELETE FROM azienda u WHERE u.id_azienda = :id_azienda", nativeQuery = true)
//	void eliminaAzienda(@Param("id_azienda") int id_azienda);
//	
//	@Transactional
//	@Modifying
//	@Query(value = "DELETE FROM ufficio u WHERE u.id_azienda = :id_azienda", nativeQuery = true)
//	void eliminaUfficiAzienda(@Param("id_azienda") int id_azienda);
//	
//	@Transactional
//	@Modifying
//	@Query(value = "DELETE FROM posti u WHERE u.id_ufficio IN(SELECT FROM ufficio p WHERE p.id_azienda = :id_azienda) ", nativeQuery = true)
//	void eliminaPostiUfficioAzienda(@Param("id_azienda") int id_azienda);
//	
//	@Transactional
//	@Modifying
//	@Query(value = "DELETE FROM prenota u WHERE u.id_ufficio IN(SELECT FROM ufficio p WHERE p.id_azienda = :id_azienda) ", nativeQuery = true)
//	void eliminaPrenotazioniUfficioAzienda(@Param("id_azienda") int id_azienda);
}