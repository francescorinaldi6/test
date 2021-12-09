package com.example.prenotazione.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Azienda;
import com.example.prenotazione.model.Ufficio;

public interface AziendaDao extends CrudRepository<Azienda, Integer>{

	
	
	@Query(name = "prova", value="SELECT * FROM ufficio u WHERE u.id_azienda = :id", nativeQuery = true) List<Object> getUfficiPerAziende(@Param("id") int id);
	
}