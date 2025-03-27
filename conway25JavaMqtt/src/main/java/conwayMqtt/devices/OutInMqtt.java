package conwayMqtt.devices;
import conwayMqtt.Cell;
import conwayMqtt.IOutDev;
import conwayMqtt.LifeController;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;



public class OutInMqtt implements IOutDev{

	private Interaction mqttConn;  	
	//private LifeController lifeontroller;
	private final String name = "outinmqttlife";
	private LifeController gameControl;

 	public OutInMqtt( LifeController LifeController ) {
 		this.gameControl = LifeController;
		String broker = System.getenv("MQTTBROKER_URL");
		if( broker == null ) {
			broker = "tcp://localhost:1883";   //in locale outof docker
			//broker = "tcp://192.168.1.132:1883";
			CommUtils.outgreen( name + " |  outside docker " + broker );
		}
		else {
			CommUtils.outgreen( name + " |  in docker to " + broker ); 	
		}					

  		mqttConn = new MqttInteraction( name , broker, "lifein", "guiin" );
  		CommUtils.outcyan(name + " | mqtt connection done " + mqttConn);
  		activateReceive();
	}
 	
	@Override
	public void display(String msg) {
		try {
//			CommUtils.outcyan("OutInMqtt| display forward "+ msg);
			mqttConn.forward(msg);  
			//giunge alla topic guiin su cui attende WSConwayguiLifeMqtt 
			//che poi fa broadcastToWebSocket.
			//L'uso di OutWs elimina questo passaggio attraverso MQTT
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}


	@Override
	public void displayCell(Cell cell) {
		int value = cell.getState() ? 1 : 0;
		int x = cell.getX() + 1;  //mapping to GUI coords
		int y = cell.getY() + 1;
		String msg = "cell(" + y + "," + x + ","+ value + ")";		
		//CommUtils.outcyan("OutInMqtt| displayCell "+ msg);
		display( msg );		
	}
	
	
	public void activateReceive() {
		CommUtils.outcyan(name + " | OutInMqtt activateReceive   "  );
		new Thread() {
			public void run() {
				try {
					while (true) {
						String msg = mqttConn.receiveMsg();
						System.out.println(name + " |  receivs: " + msg);
						elabMsg(msg);
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();		
	}

	public void elabMsg(String message) {
		CommUtils.outgreen(name + " | elabMsggg:" + message + " gameControl=" + gameControl);
		if( gameControl == null ) return;
		if( message.equals("start")) {
			gameControl.startTheGame();
		}else if( message.equals("stop")) {
			gameControl.stopTheGame();
		}else if( message.equals("exit")) {
			gameControl.exitTheGame();
		}else if( message.equals("clear")) {	
			gameControl.clearTheGame();
//			display("clearmsglist");   //per eliminare la historuìy dei messaggi su "msgslist
 		}
		else if( message.startsWith("cell(")) {
			CommUtils.outyellow("ignoro cell(");
		}
		else if( message.startsWith("cell")) {
			String[] parts = message.split("-");  //cell-3-2
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			gameControl.swithCellState(x-1, y-1);  //La GUI comincia da (1,1)
		}
	}


}
