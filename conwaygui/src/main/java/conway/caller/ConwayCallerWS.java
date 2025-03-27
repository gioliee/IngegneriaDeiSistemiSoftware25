package conway.caller;


import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import unibo.basicomm23.utils.CommUtils;
/*
 * @ClientEndpoint
 * indica al compilatore che una classe Java è destinata a fungere da endpoint client 
 * per un'applicazione WebSocket.
 * 
 * L'annotazione @ClientEndpoint è uno strumento fondamentale per sviluppare 
 * applicazioni WebSocket in Java. Essa semplifica notevolmente la creazione 
 * di client WebSocket e permette di concentrarsi sulla logica di business 
 * piuttosto che sui dettagli di basso livello della comunicazione WebSocket.
 */
@ClientEndpoint
public class ConwayCallerWS {
    private Session session;

    /*
     * Questa demo ha bisgono di un server WebSocket in ascolto sulla porta 7110
     */
    
    public ConwayCallerWS() {
        try {
	        connectToServer(new URI("ws://localhost:7110/wsupdates"));
	        CommUtils.outyellow("ConwayCallerWS on 7110" );
        } catch (Exception e) {
        	CommUtils.outred("ConwayCallerWs | ERROR:" +e.getMessage());
        }    	   	
    }

    public void connectToServer(URI endpointURI) throws Exception {
    	CommUtils.outyellow("ConwayCallerWs | connectToServer URI=" + endpointURI);
        session = ContainerProvider.getWebSocketContainer().connectToServer(this, endpointURI);
    	CommUtils.outyellow("ConwayCallerWs | connectToServer session=" + session);
    }
     
    public void workWithGame( ) {
        try {
        	
//        	sendMessageOnWs("clear");
//        	
//        	CommUtils.delay(2000);
        	
            sendMessageOnWs("cell-1-2");
            sendMessageOnWs("cell-2-2");
            sendMessageOnWs("cell-3-2");

            
            sendMessageOnWs("start");
            CommUtils.delay(3000);
            sendMessageOnWs("stop");
            
            
        } catch (Exception e) {
        	CommUtils.outred("ConwayCallerWs | ERROR:" +e.getMessage());
        }    	
    }
    
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        CommUtils.outyellow("ConwayCallerWs | Connessione al WebSocket stabilita!");
    }

    @OnMessage
    public void onMessage(String message) {
    	CommUtils.outgreen("ConwayCallerWs | riceve: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
    	CommUtils.outyellow("Connessione al WebSocket chiusa: " + closeReason);
    }



    public void sendMessageOnWs(String message)  {
        try {
        	CommUtils.outcyan("ConwayCallerWs | sendMessage " + message);
			this.session.getBasicRemote().sendText(message);
  		} catch (Exception e) {
			CommUtils.outred("ConwayCallerWs | sendMessage ERROR:" +e.getMessage());
		}
    }

    public static void main(String[] args) {
    	ConwayCallerWS caller = new ConwayCallerWS();
    	caller.workWithGame(); 
    	CommUtils.delay(10000); //To chcek broadcasted messages
    	CommUtils.outmagenta("ConwayCallerWs | BYE" );
    }
}
