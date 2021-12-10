package com.example.prenotazione;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

//Sovrascriveremo le configurazioni predefinite di Spring Security
//per utilizzare l'autenticazione e l'autorizzazione basate su JDBC(java database connectivity)
	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
												//Queries from application.properties
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	

		http.authorizeRequests()
				// URLs matching for access rights
				.antMatchers("/").permitAll()
				//utente
				.antMatchers("/Utente").permitAll()	
				.antMatchers("/Utente/signup").permitAll()	
				.antMatchers("/Utente/login").permitAll()	
//				//prenota
				.antMatchers("/Prenota").permitAll()	
				.antMatchers("/Prenota/posto").permitAll()	
				.antMatchers("/Prenota/{id_azienda}/{id_ufficio}").permitAll()	
//				ufficio
				.antMatchers("/Ufficio").permitAll()	
				.antMatchers("/Ufficio/addUffici").permitAll()	
				.antMatchers("/Ufficio/getUffici").permitAll()	
				.antMatchers("/Ufficio/{id}/getPostiTot").permitAll()	
				.antMatchers("/Ufficio/{id}/getPostiDisp").permitAll()	
				//azienda
				.antMatchers("/Azienda").permitAll()	
				.antMatchers("/Azienda/addAziende").permitAll()	
				.antMatchers("/Azienda/getAziende").permitAll()	
				.antMatchers("/Azienda/{id}/getUffici").permitAll()	
				.antMatchers("/login").permitAll() //permitAll = tutti possonoa accedere
				.antMatchers("/register").permitAll()
				.antMatchers("/home/**").hasAnyAuthority("SUPER_USER", "ADMIN_USER", "SITE_USER")
				.anyRequest().authenticated()
				.and()
				// form login
				.csrf().disable().formLogin()
				.loginPage("/login")
				.failureUrl("/login?error=true")
				.defaultSuccessUrl("/home")
				.usernameParameter("email")
				.passwordParameter("password")
				.and()
				// logout
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and()
				.exceptionHandling()
				.accessDeniedPage("/access-denied");
				
				
				
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
}

