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

public class Azienda {
	@Id @GeneratedValue
	public int id_azienda;
	public int p_iva;
	public String nome;
	public String settore;

	
}
