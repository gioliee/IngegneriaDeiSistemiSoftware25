package unibo.disi.conwaygui.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import conway.Life;
import conway.LifeController;
import conway.devices.WSIoDev;
import unibo.basicomm23.utils.CommUtils;
 
 
@Controller
public class ConwayGuiControllerLifeLocal {
	
	
	/*
	 * Controller che gestisce la home del gioco e le info del server
	 * 
	 * 
	 * 
	 * */
	
	

	//Inietta i valori da application.properties
	@Value("${spring.application.name}")
	String appName;
	
	@Value("${server.port}")
	String serverport;

	//istanze necessarie al controller
	public static LifeController lifeController;
	private Life life;
	private boolean started = false;
	
	//costruttore del controller
	public ConwayGuiControllerLifeLocal() {
		initLifeApplication();
	}
	
	//
	protected void initLifeApplication() {
		CommUtils.outyellow("ConwayGuiControllerLifeLocal CREATING ... "  );
		//griglia 20x20
        life             = new Life( 20,20 );
        lifeController   = new LifeController(life);   	
		CommUtils.outyellow("ConwayGuiControllerLifeLocal injects  lifeController in wsiodev"  );
		
		
        WSIoDev.getInstance().setLifeCotrol(lifeController); //injections
	}

	//endpoint homePage
	@GetMapping("/")
	public String homePage(Model model) {
		/*
		 * Model in spring è un'interfaccia che permette di passare dati dal controller alla 
		 * view (ovvero la pagina html nel nostro caso)
		 * 
		 * 
		 * */
		
		
		CommUtils.outmagenta("ConwayGuiControllerLifeLocal homePage");
		//ModelAndView modelAndView = new ModelAndView();
	    //modelAndView.setViewName("guipage");
		
		//se il gioco non è iniziato
		if( ! started ) {
			life.resetGrids();
			model.addAttribute("arg", appName );
			//ora dico che il gioco ha inizio
			started = true;
		}
		//quando un utente si collega viene restituita la pagina HTML guipage
	    return "guipage";  
	}
	
	
	
	//endpoint di test delle chiamate http al server
	@RequestMapping("/testHTTPCallResponseBody")
	@ResponseBody
	public String testHTTPCallResponseBody( ) {
		CommUtils.outblue("ConwayGuiControllerLifeLocal testHTTPCallResponseBody"  );
		return "{\"message\": \"ConwayGuiControllerLifeLocal Test chiamte HTTP al server\"}";
	}
	
	
	//endpoint per ottenere l'ip del server
	@RequestMapping("/getserverip")
	@ResponseBody
	public String getserverip() {
		System.out.println("ConwayGuiControllerLifeLocal doing getserverip"  );
		
		//proviamo a ottenere l'ip da una varibaile d'ambiente (utile se si usa docker)
		String p    = CommUtils.getEnvvarValue("HOST_IP"); // in docker-compose
		
		//se abbiamo ottenuto l'ip restituiamo un JSON con indirizzo ip
		if( p != null ) {
			String s = "{\"host\":\"P\"}".replace("P",p );
			System.out.println("ConwayGuiControllerLifeLocal con HOST_IP=" + s);		      
			return s;				
		}
		
		//se non abbiamo ottenuto ip da varibaile d'ambiente, usiamo la funzione che abbiamo fatto
		// per ottenre l'ip locale
		String myip = getServerLocalIp();
		System.out.println("ConwayGuiControllerLifeLocal getserverip: myip=" + myip);
		
		//se non trovo ip locale restituisco localhost (non abbiamo rete)
		if( myip == null ) {
			String s = "{\"host\":\"localhost\"}";
			System.out.println("ConwayGuiControllerLifeLocal senza myip=" + s);		      
			return s;				
		}
 		else {
 			//se ho trovato il locale, posso ottenere anche quello pubblico
 			String mypubip = getMyPublicip();
 			System.out.println("ConwayGuiControllerLifeLocal getserverip: mypubip=" + mypubip);

 			
 			String s = "{\"host\":\"P\"}".replace("P",myip );
			System.out.println("ConwayGuiControllerLifeLocal con myip=" + s);
			
			//restituisco ip locale
			return s;				
 		}
	}
 
	
	
	//funzione per ottenere l'ip pubblico del server
	protected  String getMyPublicip(){
		try {
			// URL di un servizio che restituisce l'indirizzo IP pubblico
			String serviceUrl = "https://checkip.amazonaws.com"; //se apro questo sito posso vedere che indirizzo mi è stato dato

			// Creazione della connessione HTTP
			URL url = new URL(serviceUrl); // creo url con indirizzo del servizio
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //apro una connessione all'url di prima
			connection.setRequestMethod("GET"); // specifico che voglio invuare una get

			// Lettura della risposta
			//il sito restituirà l'indirizzo ip come testo
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); //leggo risposta riga per riga
			String inputLine;
			StringBuilder response = new StringBuilder(); 

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);// salvo la risposta
			}

			in.close();
			
			//String localip = getServerLocalIp();
			
			// Stampa dell'indirizzo IP pubblico
			String myip = response.toString().trim(); 
			//System.out.println("Il tuo indirizzo IP pubblico è: " +  myip);
		    return  myip; // restituisco la risposta
		    //return localip;
		} catch (Exception e) {
			System.out.println("Errore nell'ottenere l'indirizzo IP: " + e.getMessage());
			return null;
		}
	}
	
	
	//funzione per ottenere ip locale del server
	protected String getServerLocalIp() {
		
        try {
        	
        	//ottengo tutte le interfacce di rete disponibili nel sistema
            Enumeration<NetworkInterface> interfacce = NetworkInterface.getNetworkInterfaces();
            
            //itero sulle interfacce
            while (interfacce.hasMoreElements()) {
            	//considero una interfaccia
                NetworkInterface interfaccia = interfacce.nextElement();
                
                //ne ottengo tutti gli indirizzi associati
                Enumeration<InetAddress> indirizzi = interfaccia.getInetAddresses();
                
                //itero sugli indirizzi di una interfaccia
                while (indirizzi.hasMoreElements()) {
                	
                	//recupero il singolo indirizzo
                    InetAddress indirizzo = indirizzi.nextElement();
                    
                    if (!indirizzo.isLoopbackAddress()) { // Esclude l'indirizzo loopback (127.0.0.1)
                        //System.out.println(interfaccia.getDisplayName() + ": " + indirizzo.getHostAddress());   
                    	
                    	// cerco un indirizzo di una rete privata
                        if( indirizzo.getHostAddress().startsWith("192.168")) {
                        	System.out.println("ConwayGuiControllerLifeLocal ==== " + indirizzo.getHostAddress());
                        	//restituisco ip trovato
                        	return indirizzo.getHostAddress();
                        }
                    }
                }
            }
            return null; // se nessun indirizzo trovato mando null
        } catch (SocketException e) {
            System.err.println("Errore durante la ricerca degli indirizzi IP: " + e.getMessage());
            return null;
        }			
 	
	}
	 
}
