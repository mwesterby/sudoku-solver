package solver;

import java.util.ArrayList;
import java.util.HashSet;

public class SubGrid {

	private ArrayList<Square> squares;
	
	/**
	 * Creates a new SubGrid and populates it with the correct squares.
	 * @param subGridIndex The index of the SubGrid on the board (0-8)
	 * Starting from the top left to the bottom right.
	 */
	public SubGrid(int subGridIndex) {
		
		int startingRow = (subGridIndex / 3) * 3;
		int startingCol = (subGridIndex % 3) * 3;
		
		squares = new ArrayList<Square>();
		
		for(int row = startingRow; row < (startingRow + 3); row++) {
			for(int col = startingCol; col < (startingCol + 3); col ++) {
				squares.add(new Square(row, col));
			}
		}
	}
	
	/**
	 * Gets the empty squares in the SubGrid
	 * @return a list of squares in the SubGrid that have no value yet
	 */
	public ArrayList<Square> getEmptySquares() {
		ArrayList<Square> emptySquares = new ArrayList<Square>();
		for(Square square : squares) {
			if(square.getValue() == 0) {
				emptySquares.add(square);
			}
		}
		return emptySquares;
	}
	
	/**
	 * @return A set of values which have yet to be used in the SubGrid
	 */
	public HashSet<Integer> getRemainingValues() {
		
		HashSet<Integer> remainingValues = new HashSet<Integer>();
		
		// Add all possible values
		for(int i = 1; i <= 9; i++) {
			remainingValues.add(i);
		}
		
		// Remove values in subgrid
		for (Square square: squares) {
			remainingValues.remove(square.getValue());
		}
		
		// Return remaining
		return remainingValues;
	}
	
	/**
	 * Returns true if all squares in subgrid have a value
	 * @return
	 */
	public boolean isComplete() {
		for(Square square : squares) {
			if (square.getValue() == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gets the square at a supplied index in the SubGrid (0-8, top left ot bottom right)
	 * @param index (0-8)
	 * @return A square at the gievn index in the SubGrid
	 */
	public Square getSquare(int index) {
		return squares.get(index);
	}
	
	/**
	 * For all squares in the SubGrid, remove the supplied value as a possibility
	 * @param value Value to remove as possibility for squares in the SubGrid
	 */
	public void removePossibility(int value) {
		for(Square square: squares) {
			square.removePossibility(value);
		}
	}
	
}
