package conway.devices;

import conway.Cell;
import conway.IOutDev;

//
public class ConwayOutput implements IOutDev{
 
	
private WSIoDev ws;
	
	public ConwayOutput(WSIoDev ws) {
		this.ws=ws;
	}
	
	public void displayCell(Cell cell) {
		//CommUtils.outblue("ConwayOutput| displayCell "+ cell);
		int value = cell.isState() ? 1 : 0;
		int x = cell.getX() + 1;  //mapping to GUI coords
		int y = cell.getY() + 1;
		String msg = "cell(" + y + "," + x + ","+ value + ")";		
		//CommUtils.outcyan("ConwayOutput| displayCell "+ msg);
		display( msg );
	}


	public void display(String msg) {
		//CommUtils.outcyan("ConwayOutput| display "+msg);
		ws.broadcastToWebSocket(msg); 
	} 
	
}
