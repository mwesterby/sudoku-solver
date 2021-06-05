package main.java.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Grid {
	
	private ArrayList<SubGrid> subGrids;
	
	/**
	 * Given a starting grid, initialise the sudoku.
	 * @param startingGrid an int[][] array representing a sudoku grid. 
	 * Each row is represented by three arrays of length three.
	 * This is then followed by the next row's arrays etc. In total,
	 * there are 27 arrays which represent the overall staring grid
	 */
	public Grid(int[][] startingGrid) {
		initaliseSubGrids();
		
		int startingGridIndex = 0;
		
		for (int row = 0; row < 9; row++) {
			for (int subGridInRow = 0; subGridInRow < 3; subGridInRow++) {
				for (int subGridCol = 0; subGridCol < 3; subGridCol++) {
					int col = (subGridInRow * 3) + subGridCol;
					if(startingGrid[startingGridIndex][subGridCol] != 0) {
						addValue(col, row, startingGrid[startingGridIndex][subGridCol]);
					}
					
				}
				startingGridIndex++;
			}

		}
	}
	
	/**
	 * Creates 9 blank SubGrids to represent the sudoku grid.
	 */
	private void initaliseSubGrids() {
		subGrids = new ArrayList<SubGrid>();
		for (int i = 0; i < 9; i++) {
			subGrids.add(new SubGrid(i));
		}
	}
	
	/**
	 * Gets the square at a given row and column
	 * @param row The row that contains the square (0-8)
	 * @param col The column that contains the square (0-8)
	 * @return The square at row, column
	 */
	public Square getSquare(int row, int col) {
		SubGrid subGrid = getContainingSubgrid(row, col);
		Square square = subGrid.getSquare(((row % 3) * 3) + (col % 3));
		return square;
	}
	
	/**
	 * Gets the SubGrid which would contain a square at a given row and column
	 * @param row The row the square is in (0-8)
	 * @param col The column the square is in (0-8)
	 * @return The SubGrid object which holds the square at the supplied row and column
	 */
	private SubGrid getContainingSubgrid(int row, int col) {
		return subGrids.get(((row / 3) * 3) + (col / 3));
	}
	
	/**
	 * Gets all SubGrids which have at least one unsolved square
	 * @return List of incomplete SubGrids
	 */
	public ArrayList<SubGrid> getIncompleteSubgrids() {
		ArrayList<SubGrid> incompleteSubGrids = new ArrayList<>();
		for(SubGrid subGrid : subGrids) {
			if(!subGrid.isComplete()) {
				incompleteSubGrids.add(subGrid);
			}
		}
		return incompleteSubGrids;
	}
	
	/**
	 * If the grid has been solved
	 * @return True if all values on the grid have be solved
	 */
	public boolean gridComplete() {
		for(SubGrid subGrid: subGrids) {
			if(!subGrid.isComplete()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gets all squares in a given column
	 * @param colNumber (0-8)
	 * @return A list of squares in that column
	 */
	public ArrayList<Square> getColumn(int colNumber) {	
		ArrayList<Square> column = new ArrayList<>();
		
		for(int row = 0; row < 9; row++) {
			column.add(getSquare(row, colNumber));
		}
		return column;
	}
	
	
	/**
	 * Gets all squares in a given row
	 * @param rowNumber (0-8)
	 * @return A list of squares in that row
	 */
	public ArrayList<Square> getRow(int rowNumber) {
		
		ArrayList<Square> row = new ArrayList<>();
		
		for (int col = 0; col < 9; col++) {
			row.add(getSquare(rowNumber, col));
		}
		
		return row;
	}

	/**
	 * Excluding the supplied square, returns a set of values that may still be valid for
	 * any other square in the same row
	 * @param currentSquare To sample
	 * @return A set of values that could be valid for other squares in the row
	 */
	public HashSet<Integer> remainingPossibilitiesInRow(Square currentSquare) {
		ArrayList<Square> row = getRow(currentSquare.getRow());
		HashSet<Integer> remainingPossibilities = new HashSet<>();
		
		for(Square square: row) {
			if(square.getCol() != currentSquare.getCol()) {
				remainingPossibilities.addAll(square.getPossibilities());
			}
		}
		
		return remainingPossibilities;
	}
	
	/**
	 * Excluding the supplied square, returns a set of values that may still be valid for
	 * any other square in the same column
	 * @param currentSquare To sample
	 * @return A set of values that could be valid for other squares in the column
	 */
	public HashSet<Integer> remainingPossibilitiesInColumn(Square currentSquare) {
		ArrayList<Square> col = getColumn(currentSquare.getCol());
		HashSet<Integer> remainingPossibilities = new HashSet<>();
		
		for(Square square: col) {
			if(square.getRow() != currentSquare.getRow()) {
				remainingPossibilities.addAll(square.getPossibilities());
			}
		}
		
		return remainingPossibilities;
	}
	

	/**
	 * Assign the supplied value to the square at the provided column and row
	 * This will also remove this value from the set of possibilities for all squares
	 * which are in the same row, column and SubGrid of the square at the provided coordinates
	 * @param col Column of the square to assign the value
	 * @param row Row of the square to assign the value
	 * @param value The value to assign to the square
	 */
	public void addValue(int col, int row, int value) {
		Square square = getSquare(row, col);
		square.setValue(value);
		
		// Remove possibility of supplied value for squares in the same column
		ArrayList<Square> wholeColumn = getColumn(col);
		for(Square squareToUpdate: wholeColumn) {
			squareToUpdate.removePossibility(value);
		}
		
		// Remove possibility of supplied value for squares in the same row
		ArrayList<Square> wholeRow = getRow(row);
		for(Square squareToUpdate: wholeRow) {
			squareToUpdate.removePossibility(value);
		}

		// Remove possibility of supplied value for squares in the same SubGrid
		getContainingSubgrid(row, col).removePossibility(value);
	}
	
	/**
	 * Prints the grid to the console
	 */
	public void printGrid() {
		
		final String VERTICAL_LINE = "+-------+-------+-------+";
		
		System.out.println(VERTICAL_LINE); // Top
		for(int i = 0; i < 9; i++) {
			ArrayList<Square> row = getRow(i);
			if(i % 3 == 0 && i != 0) {
				System.out.println(VERTICAL_LINE); // Between Sub Grids
			}
			System.out.print("| "); // Left edge
			for(Square square : row) {
				System.out.print(square.getValue() + " ");
				if(square.getCol() == 2 || square.getCol() == 5) {
					System.out.print("| "); // Between Sub Grids
				}
			}
			System.out.println("|"); // Right edge
		}
		System.out.println(VERTICAL_LINE); // Bottom
		System.out.println();
	}
	
	
	public int[][] gridToArray() {
		
		int[][] grid = new int[27][3];
		
		int startingIndex = 0;
		
		for (int row = 0; row < 9; row++) {
			ArrayList<Square> wholeRow = getRow(row);
			int[] left = new int[3];
			int[] centre = new int[3];
			int[] right = new int[3];
			
			for (int i = 0; i < 3; i++) {
				left[i] = wholeRow.get(i).getValue();
			}
			for (int i = 3; i < 6; i++) {
				centre[i - 3] = wholeRow.get(i).getValue();
			}
			for (int i = 6; i < 9; i++) {
				right[i - 6] = wholeRow.get(i).getValue();
			}
			grid[startingIndex] = left;
			grid[startingIndex + 1] = centre;
			grid[startingIndex + 2] = right;
			startingIndex = startingIndex + 3;
		}
		
		return grid;
		
	}
	
}
