package solver;

import java.util.ArrayList;
import java.util.HashSet;

public class Solver {

	public static void main(String[] args) {
		System.out.println("Sudoku Solver!");

		int[][] startingGrid = new int[][] { 
			{0, 0, 9}, {0, 4, 0}, {0, 0, 0}, 
			{0, 0, 0}, {0, 0, 5}, {3, 1, 0},
			{0, 6, 1}, {0, 0, 8}, {0, 5, 0},

			{0, 0, 5}, {4, 0, 0}, {2, 0, 3},
			{0, 1, 0}, {0, 0, 7}, {0, 0, 8},
			{0, 8, 0}, {0, 0, 0}, {7, 6, 0},

			{3, 0, 6}, {0, 1, 9}, {4, 0, 0},
			{7, 0, 0}, {0, 0, 0}, {0, 0, 0},
			{0, 0, 4}, {0, 5, 0}, {6, 2, 7},
			
		};

		Grid grid = new Grid(startingGrid);
		solve(grid);
		System.out.println("Done!");

	}

	public static Grid solve(Grid grid) {
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
								break;
							}
						}
					}
				}

			}
		}

		System.out.println("Solution: ");
		grid.printGrid();
		return grid;
	}

}
