package com.example.prenotazione.model;

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
@Table(name="ufficio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Ufficio {
	@Id @GeneratedValue
	public int id_ufficio;
	public String indirizzo;
	public int grandezza;
	public int id_azienda;
	
	//getter e setter
	public int getId_ufficio() {
		return id_ufficio;
	}
	public void setId_ufficio(int id_ufficio) {
		this.id_ufficio = id_ufficio;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public int getGrandezza() {
		return grandezza;
	}
	public void setGrandezza(int grandezza) {
		this.grandezza = grandezza;
	}
	public int getId_azienda() {
		return id_azienda;
	}
	public void setId_azienda(int id_azienda) {
		this.id_azienda = id_azienda;
	}
	
}
