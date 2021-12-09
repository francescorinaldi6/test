package com.example.prenotazione.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

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

public class Utente  {
	@Id @GeneratedValue
	public int id_utente;
	public String cf;
	public String nome;
	public String cognome;
	public String e_mail;
	public String numero;  
	public char tipo;
	public String password;
	public int id_azienda;
	
	/*
	@ManyToMany(cascade = CascadeType.ALL) //la persistenza propagherà (a cascata) tutte le EntityManageroperazioni ( PERSIST, REMOVE, REFRESH, MERGE, DETACH) alle entità relative.
	@JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "id_utente"), inverseJoinColumns = @JoinColumn(name = "auth_role_id"))
	private Set<Role> roles;   //vettore di bit che aumenta se necessario, non ammette duplicati
*/

	
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
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	*/
	

	
	
}

