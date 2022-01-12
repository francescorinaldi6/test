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

public interface UfficioDao extends CrudRepository<Ufficio, Integer> {

	@Query(value = "SELECT id_azienda FROM azienda a WHERE a.id_azienda = :id", nativeQuery = true)
	List<Integer> aziendaExists(@Param("id") int id);

	@Query(value = "SELECT * FROM ufficio u ORDER BY u.id_azienda", nativeQuery = true)
	List<Ufficio> getUfficiPerAziende();

	@Query(value = "SELECT * FROM ufficio u WHERE u.id_azienda = :id", nativeQuery = true)
	List<Ufficio> getUfficiPerAziende(@Param("id") int id);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM ufficio u WHERE u.id_ufficio = :id_ufficio", nativeQuery = true)
	void eliminaUfficio(@Param("id_ufficio") int id_ufficio);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM posto u WHERE u.id_ufficio = :id_ufficio", nativeQuery = true)
	void eliminaPostiUfficio(@Param("id_ufficio") int id_ufficio);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM prenota u WHERE u.id_ufficio = :id_ufficio", nativeQuery = true)
	void eliminaPrenotazioniUfficio(@Param("id_ufficio") int id_ufficio);
	
}
