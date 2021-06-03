package solver;

import java.util.ArrayList;
import java.util.HashSet;

public class SubGrid {

	private ArrayList<Square> squares;
	
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
	
	public ArrayList<Square> getEmptySquares() {
		ArrayList<Square> emptySquares = new ArrayList<Square>();
		for(Square square : squares) {
			if(square.getValue() == 0) {
				emptySquares.add(square);
			}
		}
		return emptySquares;
	}
	
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
	
	public Square getSquare(int index) {
		return squares.get(index);
	}
	
	public void removePossibility(int value) {
		for(Square square: squares) {
			square.removePossibility(value);
		}
	}
	
}
