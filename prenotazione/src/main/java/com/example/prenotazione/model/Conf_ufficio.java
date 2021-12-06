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
}
