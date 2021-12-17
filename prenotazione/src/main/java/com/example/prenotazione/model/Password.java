package com.example.prenotazione.model;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Password {
	@Autowired
	BCryptPasswordEncoder encoder;

	public String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	/*public String getPasswordCriptata(String password) {
		
		String passwordCriptata = (encoder.encode(password));
		return passwordCriptata;
	}
	*/

	
}
