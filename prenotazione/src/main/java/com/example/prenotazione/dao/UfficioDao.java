package com.example.prenotazione.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.example.prenotazione.model.Ufficio;
import com.example.prenotazione.model.Azienda;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface UfficioDao extends CrudRepository<Ufficio, Integer>{
	
	@Query(value="SELECT id_azienda FROM azienda a WHERE a.id_azienda = :id", nativeQuery = true) List<Integer> aziendaExists(@Param("id") int id);
	
	@Query(value="SELECT * FROM ufficio u ORDER BY u.id_azienda", nativeQuery = true) List<Ufficio> getUfficiPerAziende();
	
	@Query(value="SELECT * FROM ufficio u WHERE u.id_azienda = :id", nativeQuery = true) List<Ufficio> getUfficiPerAziende(@Param("id") int id);
	
	
}
