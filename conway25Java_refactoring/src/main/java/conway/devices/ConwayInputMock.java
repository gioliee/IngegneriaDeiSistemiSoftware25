package conway.devices;

import conway.Life;
import conway.LifeController;

public class ConwayInputMock {
	protected Life life;
	protected LifeController control;
	
	public ConwayInputMock(LifeController control, Life life) {
		this.control = control;
		this.life    = life;
	}

    public void simulateUserControl(){
		//USER CONTROL HERE ...
        life.switchCellState( 1, 0 );
		life.switchCellState( 1, 1 );
		life.switchCellState( 1, 2 );		
//		System.out.println("---------Initial----------");
//		outdev.displayGrid();
//		play(); 		   	
		control.start();
    }

}
