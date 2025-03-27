package conway.caller;

import java.util.Observable;
import javax.websocket.ClientEndpoint;
import unibo.basicomm23.interfaces.IObserver;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
import unibo.basicomm23.ws.WsConnection;

@ClientEndpoint
public class ConwayCallerWSInteraction implements IObserver{
	 private Interaction conn;
	    
	    public ConwayCallerWSInteraction() {
	        try {     	
	        	conn = ConnectionFactory.createClientSupport(ProtocolType.ws, "localhost:7110", "wsupdates");
		        CommUtils.outyellow("ConwayCallerWSInteraction on 7110" );        
		        ((WsConnection) conn).addObserver(this);
	        } catch (Exception e) {
	        	CommUtils.outred("ConwayCallerWsInteraction | ERROR:" +e.getMessage());
	        }    	   	
	    }

	     
	    public void workWithGame( ) {
	        try {
	        	
	        	//conn.forward("clear");
	        	
	        	//CommUtils.delay(2000);
	        	
//	        	conn.forward("stop");
	        	
	        	conn.forward("cell-1-2");
	        	conn.forward("cell-2-2");
	        	conn.forward("cell-3-2");

	            
//	            CommUtils.delay(1000);
//	           	conn.forward("start");
	          //conn.forward("stop");
	            
	            
	        } catch (Exception e) {
	        	CommUtils.outred("ConwayCallerWsInteraction | ERROR:" +e.getMessage());
	        }    	
	    }
	    
		@Override 
		public void update(Observable o, Object arg) {
			CommUtils.outyellow("ConwayCallerWs | riceve da observale: " + o + " la info:" + arg);		
			update(arg.toString() );
		}


		@Override
		public void update(String message) {
			CommUtils.outgreen("ConwayCallerWs | update elabora: " + message);
		}
	 

	    public static void main(String[] args) {
	    	ConwayCallerWSInteraction caller = new ConwayCallerWSInteraction();
	    	caller.workWithGame(); 
	     	CommUtils.delay(10000); //To chcek broadcasted messages
	    	CommUtils.outmagenta("ConwayCallerWsInteraction | BYE" );
	    }

}
