package conway;
import java.util.concurrent.TimeUnit;

import conway.devices.ConwayOutput;
import conway.devices.WSIoDev;
import unibo.basicomm23.utils.CommUtils;

public class LifeController {
    private int generationTime = 1000;
    private  Life life;
    private IOutDev outdev;
    
    protected boolean running = false;
    protected int epoch = 0;

    
    public LifeController(Life game){  
        this.life = game;
        outdev = WSIoDev.getInstance();
        CommUtils.outyellow("LifeController CREATED ... "  + outdev);
        
		//this.stop = false;
//        configureTheSystem();
     }
    
    
    //Funzioni di controllo del gioco
   
    public void switchCellState(int x, int y) {
    	//recupero la cella alla pos x,y
    	Cell c = life.getCell(x, y);
    	//cambio lo stato di vita
    	c.switchCellState();
    	//mando a dispositivo di io per inviare ai client
    	outdev.displayCell(c);
    	
    }
    
    
    public void startTheGame() {
    	if(running) return;
    	running = true;
    	play();
    	
    } 
    
    public void stopTheGame() {
    	running = false;
    }
    
    public void clearTheGame() {
    	//prima stoppo il gioco
    	stopTheGame();
    	CommUtils.delay(500);   //prima fermo e poi ...
    	//resetto le epoche
		epoch = 0;
		//pulisco la griglia
		resetAndDisplayGrids();
		//dico all gui di pulire la griglia
		outdev.display("clear");
    }
    
    
    public void exitTheGame() {
    	System.exit(0);
    }
    
    
    protected void play() {
    	// il gioco così viene eseguito in parallelo rispetto al resto del programma
    	new Thread() {
			public void run() {			
				outdev.display("game started"); 
				//continua finchè il proprietario non blocca il gioco
				while(running) {
					try {
						TimeUnit.MILLISECONDS.sleep(generationTime);
						life.computeNextGen();
						
						//Come si riduce il traffico di rete?
						//Troppi messaggi con questo metodo?   						
						displayGrid();

						CommUtils.outblue("---------Epoch ---- "+epoch++ );
						
						//vediamo se la griglia è completaente morta oppure no
						boolean gridEmpty  = life.gridEmpty();
						// verifichiamo se la è stabile, ovvero se non è cambiata la configurazione
						boolean gridStable = life.gridStable();
						if( gridEmpty || gridStable ) {
				    		running = false;
				    		String reason = gridStable ? "stable" : "empty";
				    		outdev.display("grid GAME ENDED after " + epoch + 
				    				" Epochs since empty=" + gridEmpty + " stable="+ gridStable);
				    		epoch = 0;
				    	}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}//while
				outdev.display("game stopped"); 
			}
			}.start();
    }

    public void displayGrid(   ) {
		Grid grid     = life.getGrid();
 		int rows = grid.getRowsNum();
		int cols = grid.getColsNum();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = grid.getCell(i,j);
				outdev.displayCell(cell); 
 			}			
		}
	}
	

	public void resetAndDisplayGrids(   ) {
		life.resetGrids();
		displayGrid();
	}
	

	

}
