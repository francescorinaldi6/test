package com.example.prenotazione.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Azienda;
import com.example.prenotazione.model.Ufficio;

public interface AziendaDao extends CrudRepository<Azienda, Integer>{

	@Query(value = "SELECT * FROM azienda a WHERE a.p_iva = :p_iva", nativeQuery = true)
	List<Azienda> pivaAlreadyInsert(@Param("p_iva") int p_iva);
	
}