package com.example.prenotazione.model;

import java.io.Serializable;

import javax.persistence.Id;

public class postoid implements Serializable {
	@Id
    public int id_posto;
    // getters and setters

	public Integer getId_posto() {
		return id_posto;
	}

	public void setId_posto(int id_posto) {
		this.id_posto = id_posto;
	}
}