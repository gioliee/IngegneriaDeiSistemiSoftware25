package conwayMqtt;

import unibo.basicomm23.utils.CommUtils;

/*
 * Il core di game of life
 * Non ha dipendenze da dispositivi di input/output
 * Non ha regole di gestione del gioco 
 */

public class Life {
    //La struttura
    private int rows=0;
    private int cols=0;
    private static Grid grid;
    private static Grid nextGrid;
    
    private boolean gridStable = false;
 
    public Life( int rowsNum, int colsNum ) {
        this.rows   = rowsNum;
        this.cols   = colsNum;
        createGrids();   //crea la struttura a griglia
    }

    protected void  createGrids() {
        grid     = new Grid(rows,cols);
        nextGrid = new Grid(rows,cols);   
        CommUtils.outyellow("Life | createGrids done");
    }
   
    public Grid getGrid() {
    	return grid;
    }
    public Grid getNextGrid() {
    	return nextGrid;
    }
    
    //Called by ConwayGuiControllerLifeLocal
    public void resetGrids() {
         for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid.setCellValue(i,j,false);
                nextGrid.setCellValue(i,j,false);            }
        }
        CommUtils.outyellow("Life | resetGrids done");
    }


    protected int countNeighborsLive(int row, int col) {
        int count = 0;
        if (row-1 >= 0) {
        	if( grid.getCellValue(row-1,col) ) count++;
        }
        if (row-1 >= 0 && col-1 >= 0) {
        	if( grid.getCellValue(row-1,col-1) ) count++;
        }
        if (row-1 >= 0 && col+1 < cols) {
        	if( grid.getCellValue(row-1,col+1) ) count++;
        }
        if (col-1 >= 0) {
        	if( grid.getCellValue(row,col-1) ) count++;
         }
        if (col+1 < cols) {
        	if( grid.getCellValue(row,col+1) ) count++;
       }
        if (row+1 < rows) {
        	if( grid.getCellValue(row+1,col) ) count++;
         }
        if (row+1 < rows && col-1 >= 0) {
        	if( grid.getCellValue(row+1,col-1) ) count++;
        }
        if (row+1 < rows && col+1 < cols) {
        	if( grid.getCellValue(row+1,col+1) ) count++;
       }
        return count;
    }

    public void computeNextGen(   ) {  //invocato dal Controller
    	gridStable = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int n = countNeighborsLive(i,j);
                applyRules(i, j, n);
             }
         }
        copyAndResetGrid(   );
    }
    
    public boolean gridEmpty() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	if( grid.getCell(i, j).getState() ) return false;
            }
        }
        return true;
    }

    public boolean gridStable() {
      	return gridStable;
    }
    
    protected void copyAndResetGrid(   ) {
    	gridStable = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	Cell curCell = grid.getCell(i, j);
            	boolean newVal = nextGrid.getCellValue(i,j);
            	if( curCell.getState() != newVal ){  //Ottimizzazione
            		curCell.switchCellState(); 
            		gridStable = false;
            	}
            	nextGrid.setCellValue(i,j,false);
            }
        }
    }

    protected void applyRules(int row, int col, int numNeighbors) {
    	//CELLA VIVA
        if( grid.getCellValue(row, col)) {
        	if (numNeighbors < 2) { //muore per isolamento
        		nextGrid.setCellValue(row, col, false);
        	}else if (numNeighbors == 2 || numNeighbors == 3) { //sopravvive
        		nextGrid.setCellValue(row, col, true);
        	}else if (numNeighbors > 3) { //muore per sovrappopolazione
        		nextGrid.setCellValue(row, col, false);
        	}
        }
        //CELLA MORTA
        else {
        	if (numNeighbors == 3) { //riproduzione
        		nextGrid.setCellValue(row, col, true);
        	}
        }
        //CommUtils.outgreen("Life applyRules " + nextGrid   );
    }

    //Chiamata da LifeController 
    public void switchCellState(int i, int j){
    	grid.getCell(i, j).switchCellState();   	
    }
    
    public Cell getCell(int i, int j){
    	return grid.getCell(i, j);
    }


    public  boolean getCellState( int i, int j  ) {
    	CommUtils.outcyan( "call state " + i + "," +j + " " +  grid.getCellValue(i, j));
        return grid.getCellValue(i, j);
    }
 


}
