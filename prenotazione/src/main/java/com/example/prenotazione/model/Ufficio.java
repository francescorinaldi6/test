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
@Table(name="azienda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//sdfdf

public class Ufficio {
	@Id @GeneratedValue
	public int id_ufficio;
	public String indirizzo;
	public int grandezza;
	public int id_azienda;
}
