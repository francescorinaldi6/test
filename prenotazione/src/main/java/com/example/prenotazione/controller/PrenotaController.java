package com.example.prenotazione.controller;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.prenotazione.dao.AziendaDao;
import com.example.prenotazione.dao.EmailDao;
import com.example.prenotazione.dao.PostoDao;
import com.example.prenotazione.dao.PrenotaDao;
import com.example.prenotazione.dao.Prenota_confDao;
import com.example.prenotazione.dao.UfficioDao;
import com.example.prenotazione.model.Azienda;
import com.example.prenotazione.model.Mail;
import com.example.prenotazione.model.Posto;
import com.example.prenotazione.model.Prenota;
import com.example.prenotazione.model.Prenota_conf;
import com.example.prenotazione.model.Ufficio;
import com.example.prenotazione.model.info;
import com.example.prenotazione.service.QRCodeGenerator;
import com.example.prenotazione.service.ServiceForgotPassword;
import com.example.prenotazione.service.ServiceSendQr;
import com.google.zxing.WriterException;
@CrossOrigin(origins = "http://192.168.1.48:4200")
@RestController
@RequestMapping("/Prenota")

public class PrenotaController {
	
	public info ritorno = new info();
	@Autowired
	private PrenotaDao dao;
	@Autowired
	private EmailDao email;
	@Autowired
	private PostoDao posto;
	@Autowired
	private AziendaDao daoAzienda;
	@Autowired
	private UfficioDao daoUfficio;
	@Autowired
	private Prenota_confDao daoConf;
	
	
	@Autowired
	BCryptPasswordEncoder encoder;
	private static final String QR_CODE_IMAGE_PATH ="../prenotazione/QrCode.jpg";
	@Autowired
	private ServiceSendQr notificationService;

	
	
	public String generateRandomString() {
		 int leftLimit = 48; // numeral '0'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 20;
		    Random random = new Random();

		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();

		    System.out.println(generatedString);
		
	    return generatedString;
	}

	@GetMapping("/{id_posto}/getNumerazionePostoById")
	public List<Posto> getPrenotazione(@PathVariable("id_posto") int id_posto) {

		return posto.getNumerazionePostoById(id_posto);
	}
	
	@GetMapping("/{id_ufficio}/getPrenotazioneValidataUfficio")
	public int getPrenotazioneValidataUfficio(@PathVariable("id_ufficio") int id_ufficio) {
		return daoConf.GetPrenotazioniValidateUfficio(id_ufficio).size();
	}
	
	@GetMapping("/{id_ufficio}/getPrenotazioneNonValidataUfficio")
	public int getPrenotazioneNonValidataUfficio(@PathVariable("id_ufficio") int id_ufficio) {
		int size_1 = daoConf.GetPrenotazioniNonValidateUfficio(id_ufficio).size();
		List<Prenota> lista = dao.getPrenotazioneScadute(id_ufficio);
		LocalDate todaysDate = LocalDate.now();
		
		for(int i=0; i<lista.size(); i++ ) {
			if(todaysDate.isBefore(lista.get(i).data_prenotazione.toLocalDate()) || todaysDate.isEqual(lista.get(i).data_prenotazione.toLocalDate()) ) {
				lista.remove(i);
				i--;
			}
		}
		
		return lista.size()+size_1;
		
	}
	
	@GetMapping("/{id_ufficio}/{year}/getPrenotazioneNonValidataUfficioYear")
	public int getPrenotazioneValidataNonUfficioYear(@PathVariable("id_ufficio") int id_ufficio, @PathVariable("year") int year) {
		
		int size_1 = 0;
		List<Prenota_conf> lista_1 = daoConf.GetPrenotazioniNonValidateUfficio(id_ufficio);
		
		for(int j=0; j<lista_1.size(); j++ ) {
			
			if((lista_1.get(j).data_prenotazione.getYear()+1900) != year) {
				lista_1.remove(j);
				j--;
			}
		}
		
		
		List<Prenota> lista = dao.getPrenotazioneScadute(id_ufficio);
		LocalDate todaysDate = LocalDate.now();
		
		for(int i=0; i<lista.size(); i++ ) {
			
			if((todaysDate.isBefore(lista.get(i).data_prenotazione.toLocalDate()) || (lista.get(i).data_prenotazione.getYear()+1900) != year)) {
				lista.remove(i);
				i--;
			}
		}
		
		return lista.size()+lista_1.size();
		
	}
	@GetMapping("/{id_ufficio}/{year}/getPrenotazioneValidataUfficioYear")
	public int getPrenotazioneValidataUfficioYear(@PathVariable("id_ufficio") int id_ufficio , @PathVariable("year") int year) {
		int size_1 = 0;
		List<Prenota_conf> lista_1 = daoConf.GetPrenotazioniValidateUfficio(id_ufficio);
		
		for(int j=0; j<lista_1.size(); j++ ) {

			if((lista_1.get(j).data_prenotazione.getYear()+1900) != year) {
				lista_1.remove(j);
				j--;
			}
		}
		
		return lista_1.size();
		
	}
	
	@GetMapping("/{id_ufficio}/{year}/{month}/getPrenotazioneNonValidataUfficioMonth")
	public int getPrenotazioneNonValidataUfficioMonth(@PathVariable("id_ufficio") int id_ufficio, @PathVariable("year") int year, @PathVariable("month") int month) {
		
		int size_1 = 0;
		List<Prenota_conf> lista_1 = daoConf.GetPrenotazioniNonValidateUfficio(id_ufficio);
		
		for(int j=0; j<lista_1.size(); j++ ) {
			if((lista_1.get(j).data_prenotazione.getYear()+1900) != year || lista_1.get(j).data_prenotazione.getMonth()+1!= month ) {
				lista_1.remove(j);
				j--;
			}
		}
		
		
		List<Prenota> lista = dao.getPrenotazioneScadute(id_ufficio);
		LocalDate todaysDate = LocalDate.now();
		
		for(int i=0; i<lista.size(); i++ ) {
			
			if((todaysDate.isBefore(lista.get(i).data_prenotazione.toLocalDate()) || (lista.get(i).data_prenotazione.getYear()+1900) != year) || lista.get(i).data_prenotazione.getMonth()+1!= month) {
				lista.remove(i);
				i--;
			}
		}
		
		return lista.size()+lista_1.size();
		
	}
	@GetMapping("/{id_ufficio}/{year}/{month}/getPrenotazioneValidataUfficioMonth")
	public int getPrenotazioneValidataUfficioMonth(@PathVariable("id_ufficio") int id_ufficio ,  @PathVariable("year") int year, @PathVariable("month") int month) {
		int size_1 = 0;
		List<Prenota_conf> lista_1 = daoConf.GetPrenotazioniValidateUfficio(id_ufficio);
		
		for(int j=0; j<lista_1.size(); j++ ) {

			if((lista_1.get(j).data_prenotazione.getYear()+1900) != year || lista_1.get(j).data_prenotazione.getMonth()+1!= month) {
				lista_1.remove(j);
				j--;
			}
		}
		
		return lista_1.size();
		
	}
	@GetMapping("/{id_ufficio}/{data}/getPrenotazioneNonValidataUfficioDay")
	public int getPrenotazioneNonValidataUfficioDay(@PathVariable("id_ufficio") int id_ufficio, @PathVariable("data") Date data) {
		
		int size_1 = 0;
		List<Prenota_conf> lista_1 = daoConf.GetPrenotazioniNonValidateUfficio(id_ufficio);
		
		for(int j=0; j<lista_1.size(); j++ ) {
			if(data.compareTo(lista_1.get(j).data_prenotazione) != 0) {
				lista_1.remove(j);
				j--;
			}
		}
		
		
		List<Prenota> lista = dao.getPrenotazioneScadute(id_ufficio);
		
		for(int i=0; i<lista.size(); i++ ) {
			if(data.compareTo(lista.get(i).data_prenotazione) != 0) {
				lista.remove(i);
				i--;
			}
		}
		
		return lista.size()+lista_1.size();
		
	}
	
	@GetMapping("/{id_ufficio}/{data}/getPrenotazioneValidataUfficioDay")
	public int getPrenotazioneValidataUfficioDay(@PathVariable("id_ufficio") int id_ufficio, @PathVariable("data") Date data) {
		int size_1 = 0;
		List<Prenota_conf> lista_1 = daoConf.GetPrenotazioniValidateUfficio(id_ufficio);
		
		for(int j=0; j<lista_1.size(); j++ ) {
			if(data.compareTo(lista_1.get(j).data_prenotazione) != 0) {
				lista_1.remove(j);
				j--;
			}
		}
		
		return lista_1.size();
		
	}
	
	@PostMapping("/{id_utente}/{id_azienda}/{id_ufficio}")
	public info creaPrenotazione(@RequestBody Prenota p, @PathVariable("id_utente") int id_utente,@PathVariable("id_ufficio") int id_ufficio) {

			
			if(!dao.postoExists(p.getId_posto()).isEmpty()) {
				
				if(dao.checkGiaPrenotato(p.getData_prenotazione(), id_utente).isEmpty()) {
					
					 if(dao.checkPrenotabile(p.getData_prenotazione(), p.getId_posto()).isEmpty()) {
						 
						 String criptata = encoder.encode(generateRandomString());
							p.setId_utente(id_utente);
							p.setId_ufficio(id_ufficio);
							p.setQrCode(criptata);
							dao.save(p);

					        byte[] image = new byte[0];
					        try {

					            // Generate and Return Qr Code in Byte Array
					            image = QRCodeGenerator.getQRCodeImage(p.getQrCode(),250,250);

					            // Generate and Save Qr Code Image in static/image folder
					            QRCodeGenerator.generateQRCodeImage(p.getQrCode(),250,250,QR_CODE_IMAGE_PATH);

					        } catch (WriterException | IOException e) {
					            e.printStackTrace();
					        }
					        // Convert Byte Array into Base64 Encode String
					        String qrcode = Base64.getEncoder().encodeToString(image);
					        
					        System.out.println(email.getEmail(id_utente).getE_mail());
					        Mail mail = new Mail();
					        mail.setE_mail(email.getEmail(id_utente).getE_mail());
					        
					        List<Ufficio> uff;
					        uff = daoUfficio.getUfficiDaID(id_ufficio);
					       
					        
					        String text = ("Prenotazione effettuata per il giorno: "+p.getData_prenotazione()+" al posto: "+p.getId_posto()+" nell'ufficio: "+id_ufficio);
					        String data = p.getData_prenotazione().toString();
					        String title ="Prenotazione+" + daoAzienda.getNomeById(uff.get(0).id_azienda);
					        String location=uff.get(0).indirizzo + "+Posto+n%C2%B0+"+(posto.getNumerazionePostoById(p.id_posto)).get(0).numero_postazione;
					        location.replace(' ','+');
					        System.out.println(data);
					        System.out.println(title);
					        System.out.println(location.replace(' ','+'));
					        
					       
					        
					       notificationService.sendNotification(mail,text, title, (location.replace(' ','+')), data);
					        
							
							ritorno.setMessaggio( "Hai effettuato la tua prenotazione al posto " + posto.getNumerazionePostoById(p.getId_posto()).get(0).numero_postazione);
							ritorno.setSuccess(1);
							return ritorno;
						}else {
							ritorno.setMessaggio("deve scegliere un altro posto, quello da lei inserito è già occupato per la data "+p.getData_prenotazione());
							ritorno.setSuccess(0);
							return ritorno;
						}
				}  else {
					ritorno.setMessaggio("Hai gia prenotato un posto per la data "+p.getData_prenotazione());
					ritorno.setSuccess(0);
					return ritorno;
				}
			}else {
				ritorno.setMessaggio("Non esiste il posto "+p.getId_posto());
				ritorno.setSuccess(0);
				return ritorno;
			}

	}
	//
	
	@PostMapping("/{id_utente}/{id_azienda}/{id_ufficio}/elimina")
	public info eliminaPrenotazione(@RequestBody Prenota p, @PathVariable("id_utente") int id_utente,@PathVariable("id_ufficio") int id_ufficio) {
		
		dao.eliminaPrenotazione(id_utente, id_ufficio, p.getData_prenotazione());
		ritorno.setMessaggio("La prenotazione in data "+p.getData_prenotazione()+ " è stata annullata");
		ritorno.setSuccess(1);
		return ritorno;
	}

    @GetMapping("/")
    public String getQRCode(Model model){
 


    
        return "qrcode";
    }
    
	@PostMapping("/ControlloQrCode")
	public info ControlloQrCode(@RequestBody Prenota QrCode) {
		
		 Prenota prenotazione = new Prenota();
		 prenotazione = dao.CeckQrCode(QrCode.getQrCode());
		 if(prenotazione!= null) {
			 LocalDate todaysDate = LocalDate.now();
			String Dataprenotazione = prenotazione.getData_prenotazione().toString();
			
			if (todaysDate.toString().equals(Dataprenotazione)) {
				ritorno.setMessaggio("prenotazione valida");
				dao.putIntoPrenotaConf(prenotazione.getId_prenotazione(), prenotazione.getId_posto(), prenotazione.getId_ufficio(), prenotazione.getId_utente(), prenotazione.getData_prenotazione(), 1);
				dao.eliminaPrenotazioneScaduta(prenotazione.getId_prenotazione());
				ritorno.setSuccess(1);

			}
			else {
				if(todaysDate.isAfter(prenotazione.getData_prenotazione().toLocalDate())) {
					ritorno.setMessaggio("prenotazione scaduta");
					dao.putIntoPrenotaConf(prenotazione.getId_prenotazione(), prenotazione.getId_posto(), prenotazione.getId_ufficio(), prenotazione.getId_utente(), prenotazione.getData_prenotazione(), 0);
					dao.eliminaPrenotazioneScaduta(prenotazione.getId_prenotazione());
					ritorno.setSuccess(-1);
					

				} else {
					ritorno.setMessaggio("prenotazione in data successiva a quella odierna");
					ritorno.setSuccess(0);

				}
				
			}
			
			 
			 
		 }
		 else {
			 ritorno.setSuccess(-2);
			 ritorno.setMessaggio("Prenotazione non trovata   ");
		 }
		
		
			 
			
			 
		return ritorno;
	}
    
    
	

}