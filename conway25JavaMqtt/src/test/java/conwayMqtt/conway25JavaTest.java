package conwayMqtt;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


//By default, JUnit comes with a bundled copy of hamcrest-core

public class conway25JavaTest {
private static Life life;
private static LifeController cc;
    
	//@BeforeClass
	@Before
	public void setup() {
		System.out.println("setup");
    	//configureTheSystem
        life  = new Life( 3,3 );
        cc    = new LifeController(life);   
	}
	
	@After
	public void down() {
		System.out.println("down");
	}
	
	@Test
	public void test1() {
		System.out.println("ok test1");
		//Set una configurazione inziale
        life.switchCellState( 1, 0 );
		life.switchCellState( 1, 1 );
		life.switchCellState( 1, 2 );
		
		life.computeNextGen();
		
		Grid grid = life.getGrid();
		cc.displayGrid();
		//assert ??
	}
	
	@Test
	public void yyy() {
		System.out.println("ok yyy");
	}
}

//Con gradlew test, controllare - logs/apptest.log - build/reports/tests/test/index.html

