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
}
