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

	@Transactional
	@Modifying
	@Query(value = "insert into prenota_conf (id_prenotazione, id_posto, id_ufficio, id_utente, data_prenotazione) values (:id_prenotazione, :id_posto, :id_ufficio, :id_utente, :data_prenotazione)", nativeQuery = true)
	void putIntoPrenotaConf(@Param("id_prenotazione") int id_prenotazione, @Param("id_posto") int id_posto, @Param("id_ufficio") int id_ufficio, @Param("id_utente") int id_utente, @Param("data_prenotazione") Date data_prenotazione);
	
}