package conway;

public class Cell {
	
	//true = Viva
	//false = Morta
	private boolean state;
	
	
	public Cell() {
		super();
		this.state = false;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
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

	@Override
	public String toString() {
		return state ? "1" : "0";
	}
	
	

}
