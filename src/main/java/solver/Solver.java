package main.java.solver;

import java.util.ArrayList;
import java.util.HashSet;

public class Solver {

	public void solve(Grid grid) {
		System.out.println("Starting Grid:");
		grid.printGrid();
		
		while (!grid.gridComplete()) {
			ArrayList<SubGrid> incompleteSubGrids = grid.getIncompleteSubgrids();

			for (SubGrid subGrid : incompleteSubGrids) {
				ArrayList<Square> squares = subGrid.getEmptySquares();
				for (Square square : squares) {
					HashSet<Integer> possibilities = square.getPossibilities();
					// If only one possibility
					if (possibilities.size() == 1) {
						for (int possibility : possibilities) {
							// Add the value via the grid and not directly on the square, so other squares are updated
							grid.addValue(square.getCol(), square.getRow(), possibility);
							System.out.println("Added " + possibility + " to (" + square.getRow() + ", " + square.getCol() + ")");
						}
					} else {
						for (int possibility : possibilities) {
							// If only square in row to have that value
							HashSet<Integer> rowPossibilities = grid.remainingPossibilitiesInRow(square);

							// If only square in column to have that value
							HashSet<Integer> colPossibilities = grid.remainingPossibilitiesInColumn(square);

							// If only square in subgrid to have that value
							HashSet<Integer> subGridPossibilities = subGrid.getRemainingValues();

							// If only square in row and column that can have this number
							// and number is not in subgrid, add it
							if (!rowPossibilities.contains(possibility) && !colPossibilities.contains(possibility)
									&& subGridPossibilities.contains(possibility)) {

								// Add value to grid and update other squares with this
								grid.addValue(square.getCol(), square.getRow(), possibility);
								System.out.println("Added " + possibility + " to (" + square.getRow() + ", " + square.getCol() + ")");
								break; // The grid has changed, break out the loop to start again
							}
						}
					}
				}
			}
		}
		
		System.out.println("Solution: ");
		grid.printGrid();
	}

}
