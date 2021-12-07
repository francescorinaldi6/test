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
	
	//getter e setter
	public int getId_azienda() {
		return id_azienda;
	}
	public void setId_azienda(int id_azienda) {
		this.id_azienda = id_azienda;
	}
	public int getP_iva() {
		return p_iva;
	}
	public void setP_iva(int p_iva) {
		this.p_iva = p_iva;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
}
