package conway;


/*
 * Il core di game of life
 * Non ha dipendenze da dispositivi di input/output
 * Non ha regole di controllo del gioco 
 */

public class Life {
    //La struttura
    private int rows=0;
    private int cols=0;
    private Grid grid;
    private Grid nextGrid;
    
    
    
    public Life( int rowsNum, int colsNum ) {
        this.rows   = rowsNum;
        this.cols   = colsNum;
        createGrids();   //crea la struttura a griglia
    }

    public int getRowsNum(){
        return rows;
    }
    public int getColsNum(){
        return cols;
    }

    protected void  createGrids() { 
    	grid = new Grid(rows, cols);
    	nextGrid = new Grid(rows, cols);
        //CommUtils.outyellow("Life | initializeGrids done");
    }

    protected void resetGrids() {
    	grid.resetGrid();
    	nextGrid.resetGrid();
        //CommUtils.outyellow("Life | initGrids done");
    }


    protected int countNeighborsLive(int row, int col) {
    	return grid.countNeighborsLive(row, col);
    }



    protected void computeNextGen( IOutDev outdev ) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int n = countNeighborsLive(i,j);
                applyRules(i, j, n);
                outdev.displayCell( ""+grid.getCell(j, i));
            }
            outdev.displayCell("\n");  //Va tolta nel caso della GUI?
        }
        copyAndResetGrid( outdev );
        outdev.displayCell("\n");
    }

    protected void copyAndResetGrid( IOutDev outdev ) {
    	grid.copytGrid(nextGrid);
    	nextGrid.resetGrid();
    }

    protected void applyRules(int row, int col, int numNeighbors) {
    	nextGrid.applyRules(row, col, numNeighbors, grid);
    }

    public void switchCellState(int i, int j){
    	grid.switchCellState(i, j);
    }
    
    public Cell getCell(int i, int j) {
    	return grid.getCell(i, j);
    }
 


}
