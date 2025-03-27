package conway;
import unibo.basicomm23.utils.CommUtils;

public class Grid {
	
	private Cell[][] grid;
	private int rows;
	private int cols;
	
	
	public Grid(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		grid = new Cell[rows][cols];	
		initGrid();
	}
	
	public int getRowsNum() {
		return rows;
	}
	
	public int getColsNum() {
		  return cols;
	}
	
	
	protected void initGrid() {
		CommUtils.outyellow("Grid | initGrid rows=" + rows + " cols=" + cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = new Cell(i,j,false);
		     }
		}
		CommUtils.outyellow("Life | initGrid done");
	 }	
	

	
	
	public Cell getCell(int row, int col) {
		return grid[row][col];
	}
	
	
	  public void setCellValue(int x, int y, boolean state) {
		  grid[x][y].setState(state);
	  }
	  
	  public boolean getCellValue(int x, int y) {
		  return grid[x][y].isState();
	  }
	
//	public int countNeighborsLive(int row, int col) {
//		
//		int count = 0;
//        if (row-1 >= 0) {
//            if (!grid[row-1][col].isDead()) count++;
//        }
//        if (row-1 >= 0 && col-1 >= 0) {
//            if (!grid[row-1][col-1].isDead()) count++;
//        }
//        if (row-1 >= 0 && col+1 < cols) {
//            if (!grid[row-1][col+1].isDead()) count++;
//        }
//        if (col-1 >= 0) {
//            if (!grid[row][col-1].isDead()) count++;
//        }
//        if (col+1 < cols) {
//            if (!grid[row][col+1].isDead()) count++;
//        }
//        if (row+1 < rows) {
//            if (!grid[row+1][col].isDead()) count++;
//        }
//        if (row+1 < rows && col-1 >= 0) {
//            if (!grid[row+1][col-1].isDead()) count++;
//        }
//        if (row+1 < rows && col+1 < cols) {
//            if (!grid[row+1][col+1].isDead()) count++;
//        }
//        return count;
//	}
//	
//	
//	public void applyRules(int row, int col, int numNeighbors, Grid originalGrid) {
//        //int numNeighbors = countNeighborsLive(row, col);
//        //CELLA VIVA
//        if (!originalGrid.getCell(row, col).isDead()) {
//            if (numNeighbors < 2) { //muore per isolamento
//                this.getCell(row, col).setDead();
//            } else if (numNeighbors == 2 || numNeighbors == 3) { //sopravvive
//            	this.getCell(row, col).setAlive();
//            } else if (numNeighbors > 3) { //muore per sovrappopolazione
//            	this.getCell(row, col).setDead();
//            }
//        }
//        //CELLA MORTA
//        else if (originalGrid.getCell(row, col).isDead()) {
//            if (numNeighbors == 3) { //riproduzione
//            	this.getCell(row, col).setAlive();
//            }
//        }
//        //CommUtils.outgreen("Life applyRules " + nextGrid   );
//    }
//	
//	
//	
//    protected void copytGrid(Grid nextGrid) {
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
////                this[i][j] = nextGrid[i][j];
//               this.getCell(i, j).setState(nextGrid.getCell(i, j).isState());
//            }
//        }
//    }
//    
//    
//    
//    public void switchCellState(int i, int j){
//        if( this.getCell(i, j).isDead()) this.getCell(i, j).setAlive();
//        else if(!this.getCell(i, j).isDead()) this.getCell(i, j).setDead();  
//    }

}
