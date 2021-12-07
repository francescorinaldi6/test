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
	public int id_prenotazione; //controlla su db se è chiave primaria
	public int id_posto;
	public int id_ufficio;
	public Date data_inizio;  //date tipo non so se giusto
	public Date data_fine;
	public Date data_prenotazione;
}
