package com.example.prenotazione.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Ufficio;

public interface PostoDao extends CrudRepository<Posto, Integer> {

	@Query(value = "SELECT * FROM posto u WHERE u.id_ufficio = :id", nativeQuery = true)
	List<Posto> getPostiTotali(@Param("id") int id);

	@Query(value = "SELECT * FROM posto u WHERE u.id_ufficio = :id and u.id_posto NOT IN ( SELECT id_posto FROM prenota p WHERE p.id_ufficio = :id and p.data_prenotazione = :data)", nativeQuery = true)
	List<Posto> getPostiDisponibili(@Param("id") int id, @Param("data") Date data);
	
//	@Query(value = "SELECT * FROM posto u WHERE u.id_ufficio = :id", nativeQuery = true)
//	List<Posto> getPostiPrenotati(@Param("id") int id, @Param("data") Date data);

}
