package conwayMqtt;

import unibo.basicomm23.utils.CommUtils;

public class Grid {

	private Cell[][] gridrep;
	private int rows, cols;
	
	public Grid( int rowsNum, int colsNum) {
		this.rows = rowsNum;
		this.cols = rowsNum;
		gridrep = new Cell[rowsNum][colsNum];	
		initGrid();
	}
	
	
	  protected void initGrid() {
		  CommUtils.outyellow("Grid | initGrid rows=" + rows + " cols=" + cols);
		  for (int i = 0; i < rows; i++) {
		     for (int j = 0; j < cols; j++) {
		    	 gridrep[i][j] = new Cell(i,j,false);
		     }
		  }
		  CommUtils.outyellow("Life | initGrid done");
	  }	
	  
	  public int getRowsNum() {
		  return rows;
	  }
	  public int getColsNum() {
		  return cols;
	  }

	  public Cell getCell(int x, int y) {
		  return gridrep[x][y] ;
	  }

	  public void setCellValue(int x, int y, boolean state) {
		  gridrep[x][y].setState(state);
	  }
	  
	  public boolean getCellValue(int x, int y) {
		  return gridrep[x][y].getState();
	  }
}
