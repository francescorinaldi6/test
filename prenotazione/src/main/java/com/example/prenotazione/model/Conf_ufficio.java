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
@Table(name="conf_ufficio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Conf_ufficio {
	@Id @GeneratedValue
	public int id_ufficio;
	public int posti_disponibili;
	public int distanza_minima;
	
	//getter e setter
	public int getId_ufficio() {
		return id_ufficio;
	}
	public void setId_ufficio(int id_ufficio) {
		this.id_ufficio = id_ufficio;
	}
	public int getPosti_disponibili() {
		return posti_disponibili;
	}
	public void setPosti_disponibili(int posti_disponibili) {
		this.posti_disponibili = posti_disponibili;
	}
	public int getDistanza_minima() {
		return distanza_minima;
	}
	public void setDistanza_minima(int distanza_minima) {
		this.distanza_minima = distanza_minima;
	}
	
	
}
