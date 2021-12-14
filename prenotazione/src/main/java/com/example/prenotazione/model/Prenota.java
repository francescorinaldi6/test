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
@Table(name="prenota")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Prenota {
	@Id @GeneratedValue
	public int id_prenotazione; 
	public int id_posto;
	public int id_ufficio;
	public Date data_prenotazione;
	public int id_utente;
	public String qr_code;
	
	//getter e setter
	
	public int getId_prenotazione() {
		return id_prenotazione;
	}
	public String getQrCode() {
		return qr_code;
	}
	public void setQrCode(String qrCode) {
		qr_code = qrCode;
	}
	public int getId_utente() {
		return id_utente;
	}
	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	public void setId_prenotazione(int id_prenotazione) {
		this.id_prenotazione = id_prenotazione;
	}
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
	public Date getData_prenotazione() {
		return data_prenotazione;
	}
	public void setData_prenotazione(Date data_prenotazione) {
		this.data_prenotazione = data_prenotazione;
	}
	
	
}
