package conwayMqtt;

import unibo.basicomm23.utils.CommUtils;

public class Cell {

	private int x,y;
	private boolean state    = false;  //dead
 	
	public Cell( int x, int y, boolean state) {
		this.x = x;
		this.y = y;
		this.state = state;
	}
	
	public void setState( boolean state ) {		
		this.state = state;
	}

	//Chiamata da ListController
    public void switchCellState( ){
    	//CommUtils.outmagenta("Cell | switchCellState " + x + "," + y);
        state = ! state; 
    }
	public boolean getState(   ) {
		return state;
	}
	public int getX(   ) {
		return x;
	}	
	public int getY(   ) {
		return y;
	}		

}
