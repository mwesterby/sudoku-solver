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
	
//	public Square getSquare(int row, int col) {
//		SubGrid subGrid = getContainingSubgrid(row, col);
//		
//		/*
//		 * C  R    C  R
//		 * 4, 3 == 1, 0
//		 * 5, 4 == 6, 1
//		 */
//		
//		int theRow = (row % 3) * 3;
//		int theCol = col % 3;
//		
//		Square square = subGrid.getSquare(theRow + theCol);
//		
//		return square;
//	}
//	
//	private SubGrid getContainingSubgrid(int row, int col) {
//		int subgridrow = (row / 3) * 3;
//		int subgridcol = col / 3;
//		
//		return subGrids.get(subgridrow + subgridcol);
//	}
	

	private void initaliseSubGrids() {
		subGrids = new ArrayList<SubGrid>();
		for (int i = 0; i < 9; i++) {
			subGrids.add(new SubGrid(i));
		}
	}
	
	public ArrayList<SubGrid> getSubgrids() {
		return subGrids;
	}
	
	
	public boolean gridComplete() {
		for(int i = 0; i < 9; i++) {
			ArrayList<Square> row = getRow(i);
			for(Square square: row) {
				if(square.getValue() == 0) {
					return false;
				}
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
		
		
		
//		int square1 = colNumber % 3;
//		int square2 = square1 + 3;
//		int square3 = square2 + 3;
		
		/*
		 * 0 == 0, 3, 6
		 * 1 == 0, 3, 6
		 * 2 == 0, 3, 6
		 * 3 == 1, 4, 7
		 * 4 == 1, 4, 7
		 * 5 == 1, 4, 7
		 * 6 == 2, 5, 8
		 * 7 == 2, 5, 8
		 * 8 == 2, 5, 8
		 */
//		int subGrid1 = colNumber / 3;
//		int subGrid2 = subGrid1 + 3;
//		int subGrid3 = subGrid2 + 3;
		
		
		
		
		ArrayList<Square> column = new ArrayList<>();
		
		for(int subGrid = colNumber / 3; subGrid < 9; subGrid += 3) {
			for(int square = colNumber % 3; square < 9; square += 3) {
				column.add(subGrids.get(subGrid).getSquare(square));
			}
		}

		
		return column;
	}
	
	
	/**
	 * Rows 0-8
	 * @param rowNumber
	 * @return
	 */
	public ArrayList<Square> getRow(int rowNumber) {
		
		/*
		 * Subgrids
		 * 0 == 0, 1, 2
		 * 1 == 0, 1, 2
		 * 2 == 0, 1, 2
		 * 3 == 3, 4, 5
		 * 4 == 3, 4, 5
		 * 5 == 3, 4, 5
		 * 6 == 6, 7, 8
		 * 7 == 6, 7, 8
		 * 8 == 6, 7, 8
		 */
		
		/*
		 * Squares
		 * 0 == 0, 1, 2
		 * 1 == 3, 4, 5
		 * 2 == 6, 7, 8
		 * 3 == 0, 1, 2
		 * 4 == 3, 4, 5
		 * 5 == 6, 7, 8
		 * 6 == 0, 1, 2
		 * 7 == 3, 4, 5
		 * 8 == 6, 7, 8
		 */
		
		ArrayList<Square> row = new ArrayList<>();
		
		int subgrid = (rowNumber / 3) * 3;
		int square = (rowNumber % 3) * 3;
		
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				row.add(subGrids.get(subgrid).getSquare(square));
				square++;
			}
			square = (rowNumber % 3) * 3;
			subgrid++;
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
		ArrayList<Square> thisCol = getColumn(col);
		ArrayList<Square> thisRow = getRow(row);
		Square square = thisCol.get(row);
		square.setValue(value);
		
		for(Square squareToUpdate: thisCol) {
			squareToUpdate.removePossibility(value);
		}
		
		for(Square squareToUpdate: thisRow) {
			squareToUpdate.removePossibility(value);
		}
		
		
		/*
		 * 0 - 00, 01, 02, 10, 11, 12, 20, 21, 22
		 * 1
		 * 2
		 * 3
		 * 4
		 * 5
		 * 6
		 * 7
		 * 8
		 * 
		 * 
		 * 
		 */
		
		int subgridrow = (row / 3) * 3;
		int subgridcol = col / 3;
		
		subGrids.get(subgridrow + subgridcol).removePossibility(value);
	}
	
	
	public void printGrid() {
		
		for(int i = 0; i < 9; i++) {
			ArrayList<Square> row = getRow(i);
			if(i % 3 == 0) {
				System.out.println("----- ------ ------");
			}
			for(Square square : row) {
				System.out.print(square.getValue() + " ");
				if(square.getCol() == 2 || square.getCol() == 5) {
					System.out.print("|");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
