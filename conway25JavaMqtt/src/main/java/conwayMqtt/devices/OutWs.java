	package conwayMqtt.devices;


import conwayMqtt.Cell;
import conwayMqtt.IOutDev;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

/*
 * Comunicazione diretta con WS
 * Viene creato in LifeController
 */
public class OutWs implements IOutDev{
private Interaction connwws;

	public OutWs() {
		
		String wsaddr = System.getenv("WSADDR");
		if( wsaddr == null ) {
			CommUtils.outgreen( "OutWs | wsaddr local= " + wsaddr ); 	
			wsaddr = "localhost:7110";
		}
		else {
			CommUtils.outgreen( "OutWs | wsaddr remote= " + wsaddr ); 	
		}					
		connwws = ConnectionFactory.createClientSupport(ProtocolType.ws, wsaddr, "wsupdates");
		CommUtils.outblue("OutWs | connection done");
	}
	@Override
	public void displayCell(Cell cell) {
		try {
//			CommUtils.outmagenta("OutWs | displayCell " + cell);
			int value = cell.getState() ? 1 : 0;
			int x = cell.getX() + 1;  //mapping to GUI coords
			int y = cell.getY() + 1;
			String msg = "cell(" + y + "," + x + ","+ value + ")";		
			//CommUtils.outcyan("OutInMqtt| displayCell "+ msg);
			display( msg );					
		} catch (Exception e) {
			CommUtils.outred("OutWs | displayCell ERROR");
  		}		
	}
	@Override
	public void display(String msg) {
		try {
//			CommUtils.outyellow("OutWs | display:" + msg);
			connwws.forward(msg);
		} catch (Exception e) {
			CommUtils.outred("OutWs | display ERROR");
		}		
	}


}
