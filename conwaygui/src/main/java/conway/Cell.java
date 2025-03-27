package conway;

public class Cell {
	
	//true = Viva
	//false = Morta
	private boolean state;
	
	//servono per le coordinate nella griglia
	private int x,y;
	
	//costruttori
	public Cell() {
		super();
		this.state = false;
	}
	
	public Cell( int x, int y, boolean state) {
		this.x = x;
		this.y = y;
		this.state = state;
	}

	//getters e setters
	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	//Altri metodi
	//indipendentemente dal tipo dello stato la griglia le può settare vive o morte
	public void setAlive() {
		this.state = true;
	}
	
	public void setDead() {
		this.state = false;
	}
	
	
	//metodo che userà la griglia per capire se la cella è viva o morta
	public boolean isDead() {
		return this.state ? false : true;
	}
	
	public void switchCellState() {
		state = !state;
	}

	@Override
	public String toString() {
		return state ? "1" : "0";
	}
	
	

}
