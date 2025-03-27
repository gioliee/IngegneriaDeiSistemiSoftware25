package conwayMqtt;
/*
 * Contratto definito dalla business logic
 */
public interface IOutDev {
	public void display(String msg);      //For HMI
	public void displayCell(Cell cell);   //ADDED

}
