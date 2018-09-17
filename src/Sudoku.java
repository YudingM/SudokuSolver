public class Sudoku {

    Cell[][] puzzle = new Cell[9][9];

    public Sudoku() {

    }

    public Cell getCell(int row, int col) {

        return puzzle[row][col];

    }

    public void inputCell(Cell cell, int row, int col) {

        puzzle[row][col] = cell;

    }

    public void removeFromList() {

        for (int i = 0; i < puzzle[0].length; i++) {

            for (int j = 0; j < puzzle.length; j++) {
                
                if(puzzle[i][j].returnActualVal() != 0) {

                    for (int k = 0; k < ; k++) {
                        
                    }
                    
                }
                
            }
            
        }

    }

}
