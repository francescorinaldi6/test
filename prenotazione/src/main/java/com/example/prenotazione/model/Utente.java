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
	public String numero;  //vedere se cambiare in String visto che un numero a 10 cifre è pesante
	public int id_azienda;
	
}
