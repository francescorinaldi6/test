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
@Table(name="utente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Utente {
	@Id @GeneratedValue
	public int id_utente;
	public String cf;
	public String nome;
	public String cognome;
	public String e_mail;
	public String numero;  
	public int id_azienda;
	
	
	//getter e setter
	public int getId_utente() {
		return id_utente;
	}
	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getId_azienda() {
		return id_azienda;
	}
	public void setId_azienda(int id_azienda) {
		this.id_azienda = id_azienda;
	}
	
}
