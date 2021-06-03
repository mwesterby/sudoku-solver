package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Solver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Sudoku Solver!");
		
		Grid grid = new Grid();
				   // C  R  V
		grid.addValue(0, 0, 5);
		grid.addValue(1, 0, 3);
		grid.addValue(4, 0, 7);
		
		grid.addValue(0, 1, 6);
		grid.addValue(3, 1, 1);
		grid.addValue(4, 1, 9);
		grid.addValue(5, 1, 5);
		
		grid.addValue(1, 2, 9);
		grid.addValue(2, 2, 8);
		grid.addValue(7, 2, 6);
		
		grid.addValue(0, 3, 8);
		grid.addValue(0, 4, 4);
		grid.addValue(0, 5, 7);
		
		grid.addValue(3, 4, 8);
		grid.addValue(4, 3, 6);
		grid.addValue(4, 5, 2);
		grid.addValue(5, 4, 3);
		
		grid.addValue(8, 3, 3);
		grid.addValue(8, 4, 1);
		grid.addValue(8, 5, 6);
		
		grid.addValue(1, 6, 6);
		grid.addValue(3, 7, 4);
		grid.addValue(4, 7, 1);
		grid.addValue(5, 7, 9);
		grid.addValue(4, 8, 8);
		
		grid.addValue(6, 6, 2);
		grid.addValue(7, 6, 8);
		grid.addValue(8, 7, 5);
		grid.addValue(7, 8, 7);
		grid.addValue(8, 8, 9);
		
		grid.printGrid();
		
		solve(grid);
		grid.printGrid();
		System.out.println("Done!");
		
	}
	
	
	public static void solve(Grid grid) {
		
		while(!grid.gridComplete()) {
			ArrayList<SubGrid> subGrids = grid.getSubgrids();
			
			for (SubGrid subGrid: subGrids) {
				ArrayList<Square> squares = subGrid.getSquares();
				for (Square square: squares) {
					if (square.getValue() == 0) { // Value not yet found
						 HashSet<Integer> possibilities = square.getPossibilities();	
						 
						 // If only one possibility
						 if(possibilities.size() == 1) {
							 for (int possibility: possibilities) {	
							 		grid.addValue(square.getCol(), square.getRow(), possibility);
							 		System.out.println("Here!");
							 		break;
							 }
						 } else {
							 for (int possibility: possibilities) {							 
									// If only square in row to have that value
								 	HashSet<Integer> rowPossibilities = grid.remainingPossibilitiesInRow(square);
								 
									// If only square in column to have that value
								 	HashSet<Integer> colPossibilities = grid.remainingPossibilitiesInColumn(square);
									
									// If only square in subgrid to have that value
								 	HashSet<Integer> subGridPossibilities = subGrid.getRemainingValues();
								 
								 	// If only square in row and column that can have this number
								 	// and number is not in subgrid, add it
								 	if(!rowPossibilities.contains(possibility) 
								 			&& !colPossibilities.contains(possibility)
								 			&& subGridPossibilities.contains(possibility)) {
								 		
								 		// Add value to grid and update other squares with this
								 		grid.addValue(square.getCol(), square.getRow(), possibility);
								 		break;
								 	}
							 }
						 }
						 
						 
						
						
					}
					
					
				}
			}
		}
	}

}
