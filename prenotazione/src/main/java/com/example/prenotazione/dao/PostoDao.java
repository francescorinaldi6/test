package com.example.prenotazione.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Ufficio;

public interface PostoDao extends CrudRepository<Posto, Integer> {

	@Query(value = "SELECT * FROM posto u WHERE u.id_ufficio = :id", nativeQuery = true)
	List<Posto> getPostiTotali(@Param("id") int id);

	@Query(value = "SELECT * FROM posto u WHERE u.id_ufficio = :id and u.prenotabile = true", nativeQuery = true)
	List<Posto> getPostiDisponibili(@Param("id") int id);

}
