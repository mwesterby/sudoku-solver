package solver;

import java.util.ArrayList;
import java.util.HashSet;

public class Solver {

	public static void main(String[] args) {
		System.out.println("Sudoku Solver!");
	
		int[][] startingGrid = new int[][] {
			{0, 0, 9}, {0, 4, 0}, {0, 0 ,0},
			{0, 0, 0}, {0, 0, 5}, {3, 1 ,0},
			{0, 6, 1}, {0, 0, 8}, {0, 5 ,0},
			
			{0, 0, 5}, {4, 0, 0}, {2, 0 ,3},
			{0, 1, 0}, {0, 0, 7}, {0, 0 ,8},
			{0, 8, 0}, {0, 0, 0}, {7, 6 ,0},

			{3, 0, 6}, {0, 1, 9}, {4, 0 ,0},
			{7, 0, 0}, {0, 0, 0}, {0, 0 ,0},
			{0, 0, 4}, {0, 5, 0}, {6, 2 ,7},
		};
		
		try {
			Grid grid2 = new Grid(startingGrid);
			grid2.printGrid();
			solve(grid2);
			grid2.printGrid();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
