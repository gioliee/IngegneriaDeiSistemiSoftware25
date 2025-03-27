package conway.devices;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import conway.Cell;
import conway.IOutDev;
import conway.LifeController;
import jakarta.websocket.server.ServerEndpoint;
import unibo.basicomm23.utils.CommUtils;
import unibo.disi.conwaygui.controller.ConwayGuiControllerLifeLocal;


/*
 * 
 * facciamo un WebSocket server per interagire con la GUI di Conway in tempo reale.
 * 
 * Gestisce connessioni WebSocket per inviare e ricevere messaggi dalla GUI
 * 
 * */


// annotazione per dire che stiamo usando un server webSocket accessibile all'url indicato
@ServerEndpoint("/wsupdates")
public class WSIoDev extends AbstractWebSocketHandler implements IOutDev{  //AbstractWebSocketHandler ne ereditiamo la gestione delle websocket
	
	//in questo modo implementiamo il pattern Singleton in modo da avere una sola istanza della classe
	private static WSIoDev myself   = null; 
	
	private final String name = "WSIoDev";
	
	// contiene tutte le sessioni websocket attive
	private Set<WebSocketSession> clients = new HashSet<>();
	
	//indica se la prima connessione è già avvenuta
	private boolean firstConnection = true;
	
	//indica se il proprietario può inviare comandi
	private boolean ownerOn = true;
	
	//riferimento al controller del gioco
	private LifeController gameControl;
	
	//sessione del primo a connettersi (proprietario)
	private WebSocketSession owner;
 
	// serve per implementare il patter singleton restituendo l'istanza se già esiste o creandola se non esiste
	public static WSIoDev getInstance( ) {
		CommUtils.outcyan(  "wslifegui | getInstance  "  );
		if (myself == null) {
			myself = new WSIoDev( );
		}
		return myself;
	}  
	
	//costruttore
	public WSIoDev() {
		CommUtils.outmagenta(  "wslifegui | CREATING ... "  );		
	}
	
	//serve per mettere il controllo del gioco e così comandarlo tramite websocket
	public  void setLifeCotrol( LifeController gameControl ) {
		CommUtils.outblue( name + " | injected gameControl=" + gameControl );
		this.gameControl = gameControl;
	}
 
	
	
	/*
	 * metodo che viene chimato automaticamente quando un nuovo client si connette alla websocket
	 * 
	 * */
	@Override //from AbstractWebSocketHandler
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//Chiama la versione del metodo definita in AbstractWebSocketHandler, che serve per la gestione interna delle sessioni WebSocket
		super.afterConnectionEstablished(session);	
		
		CommUtils.outblue(name + " | afterConnectionEstablished as first:" +  firstConnection + " " + clients.size());
		
		//se è la prima connessione
		if( firstConnection ) {
			//viene salvata la sessione del primo a connettersi
			owner = session;
			//si dice che è avvenuta la prima connessione
			firstConnection = false;
		}
		//aggiungo il nuovo cliente alla lista
        clients.add(session);
	}
	
	
	
	/*
	 * 
	 * serve a inviare un messaggio a tutti i client connessi tramite WebSocket
	 * 
	 * Invio comandi alle pagine HTML connesse
	 */
	public void broadcastToWebSocket(String message) {
		//CommUtils.outyellow(name + " broadcastToWebSocket " + message  );
		
		//iteriamo sui clienti connessi 
        for (WebSocketSession client : clients) {
        	// controlliamo se la websocket è ancora aperta
            if (client.isOpen()) {
                try {
                	// se ancora aperta mando il messaggio
                    client.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    CommUtils.outred(name + "ERROR: COULD NOT SEND TEXT THROUGH WEBSOCKET TO " + client.getId() + "!");
                }
            }
        } 
    }
	
	
	
	
	
	
	
	/*
	 * viene chiamato ogni volta che un client WebSocket invia un messaggio al server.
	 * 
	 * Obiettivo: ricevere i comandi dalla GUI (interfaccia utente) e passarli al controller del gioco, cioè LifeController
	 * 
	 * Gestione comandi provenienti dalla GUI
	 */
	
	
	
	//input:
	// - session -> rappresenta il client che ha inviato il messaggio
	// - message -> contiene il messaggio inviato dal client
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		CommUtils.outgreen(name + " | receives: " + message + " wirh ownerOn=" + ownerOn );		
		
		//recupero il testo del messaggio ricevuto da GUI
		String cmd = message.getPayload();
		
		// disattiviamo il controllo dell'owner
		if( cmd.contains("owneroff")) ownerOn = false;
		
		//attivaimo il controllo dell'owner
		if( cmd.contains("owneron"))  ownerOn = true;
		
		
		// se il client cha ha inviato il messaggio non è l'owner e si è ricevuto di attivare il controller, si ignora il comando  
		if( ! session.equals(owner) && ownerOn) {
			CommUtils.outmagenta(name + " | received from a non-owner "   );
			return;
		}
		
		//ConwayGuiControllerLifeLocal.lifeController.elabMsg(cmd);
		
		//eseguiamo il comando ricevuto, lo invia come stringa
		elabMsg( cmd );		 
	}

	
	
	
	
	/*
	 * 
	 * Il metodo serve per elaborare i comandi ricevuti dalla GUI e comunicare con 
	 * il controller del gioco (LifeController) per eseguire le azioni corrispondenti
	 * 
	 * 
	 * Mappa messaggi ricevuti in chiamate al controller del gioco
	 */
	public void elabMsg(String message) {
		//CommUtils.outgreen(name + " | elabMsg:" + message + " gameControl=" + gameControl);
		
		// se il LifeController è null  restituisce null
		if( gameControl == null ) return;
		
		//se il comando è start avvio il gioco
		if( message.equals("start")) {
			gameControl.startTheGame();
		//se il comando è stop fermo il gioco
		}else if( message.equals("stop")) {
			gameControl.stopTheGame();
		//se il comando è exit il proprietario molla il controllo del gioco
		}else if( message.equals("exit")) {
			gameControl.exitTheGame();
		//se il comando è clear resettiamo la griglia
		}else if( message.equals("clear")) {	
			gameControl.clearTheGame();
			display("clearmsglist");   //per eliminare la historuìy dei messaggi su "msgslist
 		}
		//se il comando inizia per cell si sta indicando la volontà di 
		//modificare lo stato di una cella (il messaggio è nel formato 
		//cell-x-y dove x e y sono le coordinate della cella)
		else if( message.startsWith("cell")) {
			String[] parts = message.split("-");  //cell-3-2
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			gameControl.switchCellState(x-1, y-1);  //La GUI comincia da (1,1)
		}
	}

	
	/*
	 * si occupa di inviare i messaggi alla gui che dovrà far vedere
	 * */
	@Override
	public void display(String msg) {
		// inviamo a tutti i client connessi il messaggi tramite websocket
		broadcastToWebSocket(msg); 		
	}

	/*
	 * si occupa di inviare un messaggio specifico sulla modifica 
	 * dello stato di una cella nel gioco
	 * 
	 * 
	 * */
	@Override
	public void displayCell(Cell cell) {
		int value = cell.isState() ? 1 : 0;
		int x = cell.getX() + 1;  //mapping to GUI coords
		int y = cell.getY() + 1;
		//nuovo messaggio per la gui
		String msg = "cell(" + y + "," + x + ","+ value + ")";		
		//CommUtils.outcyan(name +  " | displayCell "+ msg);
		//invio del nuovo messaggio
		display( msg );
	}
	
	
}
