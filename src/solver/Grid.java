package solver;

import java.util.ArrayList;
import java.util.HashSet;

public class Grid {
	
	private ArrayList<SubGrid> subGrids;
	
	public Grid() {
		initaliseSubGrids();
	}
	
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
	
	private void initaliseSubGrids() {
		subGrids = new ArrayList<SubGrid>();
		for (int i = 0; i < 9; i++) {
			subGrids.add(new SubGrid(i));
		}
	}
	
	
	public Square getSquare(int row, int col) {
		SubGrid subGrid = getContainingSubgrid(row, col);
		Square square = subGrid.getSquare(((row % 3) * 3) + (col % 3));
		return square;
	}
	
	private SubGrid getContainingSubgrid(int row, int col) {
		return subGrids.get(((row / 3) * 3) + (col / 3));
	}
	
	public ArrayList<SubGrid> getIncompleteSubgrids() {
		ArrayList<SubGrid> incompleteSubGrids = new ArrayList<>();
		for(SubGrid subGrid : subGrids) {
			if(!subGrid.isComplete()) {
				incompleteSubGrids.add(subGrid);
			}
		}
		return incompleteSubGrids;
	}
	
	public boolean gridComplete() {
		for(SubGrid subGrid: subGrids) {
			if(!subGrid.isComplete()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Columns 0-8
	 * @param colNumber
	 * @return
	 */
	public ArrayList<Square> getColumn(int colNumber) {	
		ArrayList<Square> column = new ArrayList<>();
		
		for(int row = 0; row < 9; row++) {
			column.add(getSquare(row, colNumber));
		}
		return column;
	}
	
	
	/**
	 * Rows 0-8
	 * @param rowNumber
	 * @return
	 */
	public ArrayList<Square> getRow(int rowNumber) {
		
		ArrayList<Square> row = new ArrayList<>();
		
		for (int col = 0; col < 9; col++) {
			row.add(getSquare(rowNumber, col));
		}
		
		return row;
	}

	
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
	 * 
	 * @param col
	 * @param row
	 * @param value
	 */
	public void addValue(int col, int row, int value) {
		ArrayList<Square> wholeColumn = getColumn(col);
		ArrayList<Square> wholeRow = getRow(row);
		Square square = getSquare(row, col);
		
		square.setValue(value);
		
		for(Square squareToUpdate: wholeColumn) {
			squareToUpdate.removePossibility(value);
		}
		
		for(Square squareToUpdate: wholeRow) {
			squareToUpdate.removePossibility(value);
		}

		getContainingSubgrid(row, col).removePossibility(value);
	}
	
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
}
