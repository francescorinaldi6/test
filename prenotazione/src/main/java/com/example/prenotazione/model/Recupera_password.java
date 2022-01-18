package com.example.prenotazione.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="recupera_password")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Recupera_password {
	@Id @GeneratedValue
	public int id_utente;
	public String codice;
	
	public int getId_utente() {
		return id_utente;
	}
	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	//getter e setter
	
	
	
}
