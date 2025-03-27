package conwayMqtt;

import unibo.basicomm23.utils.CommUtils;

public class MainConwayMqtt  {

    public static void main(String[] args) {
    CommUtils.aboutThreads("MainConway | STARTS " ); //Richiede dipendenze
    	//configureTheSystem
        Life life           = new Life( 20,20 );
        LifeController cc   = new LifeController(life);   
        
    }

}