package conway;


public class MainConway  {

    public static void main(String[] args) {
        Life life           = new Life( 3,3 );
        LifeController cc   = new LifeController(life); 
    }

}