package com.example.prenotazione.model;

import java.util.Arrays;
import java.util.List;

import com.example.prenotazione.dao.PostoDao;

public class ListaPosti {

	public List<Posto> posto;
    PostoDao dao;
	
	public List<Posto> getPosto() {
		return posto;
	}

	public void setPosto(List<Posto> posto) {
		this.posto = posto;
	}


	
}
