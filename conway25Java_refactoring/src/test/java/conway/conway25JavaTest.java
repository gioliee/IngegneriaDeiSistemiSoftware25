package conway;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import conway.devices.ConwayInputMock;

public class conway25JavaTest {
	private static ConwayInputMock cim;
	private static LifeController cc;
	private static Life life;

	@BeforeClass
	public static void setup() {
		System.out.println("setup");
        life = new Life( 3,3 );
        cc   = new LifeController(life);   
        cim = new ConwayInputMock(cc,life);		
	}
	
	@After
	public void down() {
		System.out.println("down");
	}
	
	@Test
	public void test1() {
		System.out.println("ok test1");

		life.switchCellState(1, 0);
		life.switchCellState(1, 1);
		life.switchCellState(1, 2);
		
		life.computeNextGen();
		
		cc.displayGrid();
		
	}
	
	@Test
	public void yyy() {
		System.out.println("ok yyy");
	}
}

