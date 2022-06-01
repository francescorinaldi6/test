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
import com.example.prenotazione.model.Prenota_conf;

public interface Prenota_confDao extends CrudRepository<Prenota_conf, Integer> {

	@Query(value = "SELECT * FROM prenota_conf p WHERE validato = 1 and id_ufficio = :id_ufficio", nativeQuery = true)
	List<Prenota_conf> GetPrenotazioniValidateUfficio(@Param("id_ufficio") int id_ufficio);
	
	@Query(value = "SELECT * FROM prenota_conf p WHERE validato = 0 and id_ufficio = :id_ufficio", nativeQuery = true)
	List<Prenota_conf> GetPrenotazioniNonValidateUfficio(@Param("id_ufficio") int id_ufficio);
	
}