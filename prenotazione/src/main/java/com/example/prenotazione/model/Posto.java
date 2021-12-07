package com.example.prenotazione.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="posto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
@ToString
@IdClass(Posto.class)

public class Posto implements Serializable {
	@Id @GeneratedValue
	public int id_posto;
	@Id
	public int id_ufficio;
	public char prenotabile;
	public int numero_postazione;
	
	//getter e setter
	public int getId_posto() {
		return id_posto;
	}
	public void setId_posto(int id_posto) {
		this.id_posto = id_posto;
	}
	public int getId_ufficio() {
		return id_ufficio;
	}
	public void setId_ufficio(int id_ufficio) {
		this.id_ufficio = id_ufficio;
	}
	public char getPrenotabile() {
		return prenotabile;
	}
	public void setPrenotabile(char prenotabile) {
		this.prenotabile = prenotabile;
	}
	public int getNumero_postazione() {
		return numero_postazione;
	}
	public void setNumero_postazione(int numero_postazione) {
		this.numero_postazione = numero_postazione;
	}
	
	
}
