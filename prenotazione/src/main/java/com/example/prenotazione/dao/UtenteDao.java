package com.example.prenotazione.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.prenotazione.model.Mail;
import com.example.prenotazione.model.Utente;

public interface UtenteDao extends CrudRepository<Utente, Integer> {

	// SELECT * FROM prenotazione.auth_user_role INNER JOIN prenotazione.utente ON
	// prenotazione.auth_user_role.auth_user_id=prenotazione.utente.id_utente
	@Query(value = "SELECT id_azienda FROM azienda a WHERE a.id_azienda = :id", nativeQuery = true)
	List<Integer> aziendaExists(@Param("id") int id);
	
	
	@Query(value = "SELECT id_utente FROM utente u WHERE u.e_mail = :e_mail", nativeQuery = true)
	Integer idUtenteLogin(@Param("e_mail") String e_mail);
	
	@Query(value = "SELECT auth_role_id FROM auth_user_role a WHERE a.id_utente = :id", nativeQuery = true)
	Integer ruoloUtente(@Param("id") Integer id);

	@Query(value = "SELECT * FROM utente u ", nativeQuery = true)
	List<Utente> utenti();
	
	@Query(value = "SELECT * FROM utente u WHERE u.e_mail = :e_mail", nativeQuery = true)
	Utente utenteFromEmail(@Param("e_mail") String e_mail);
	
	@Transactional
	@Modifying
	@Query(value = "insert into auth_user_role (id_utente, auth_role_id) values (:id, 3)", nativeQuery = true)
	void InsertSiteUser(@Param("id") int id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE utente u  SET password = :password WHERE u.id_utente = :id", nativeQuery = true)
	void ResetPassword(@Param("id") int id, @Param("password") String password );
	
	@Transactional
	@Modifying
	@Query(value = "insert into recupera_password (id_utente, codice) values (:id_utente, :codice)", nativeQuery = true)
	void setCodiceReset(@Param("id_utente") Integer id_utente, @Param("codice") String codice);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM recupera_password p WHERE p.id_utente = :id_utente", nativeQuery = true)
	void setCodiceResetNull(@Param("id_utente") Integer id_utente);
	
	
	
}
