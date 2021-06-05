package main.java.solver;

import java.util.HashSet;

public class Square {

	private int value;
	private HashSet<Integer> possibilities;
	private int row;
	private int col;
	
	/**
	 * Creates a square
	 * @param row Row the square is located in
	 * @param col Column the square is located in
	 */
	public Square(int row, int col) {
		this.row = row;
		this.col = col;
		
		possibilities = new HashSet<Integer>();
		for(int i = 1; i <= 9; i++) {
			possibilities.add(i);
		}
		value = 0;
	}
	
	/**
	 * Returns the value of the square
	 * @return Value (Note: 0 is unsolved)
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * @return A set of possible values this square could be
	 */
	public HashSet<Integer> getPossibilities() {
		return possibilities;
	}
	
	/**
	 * @return The row the square is located on
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * @return The column the square is located on
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Assigns a value to the square. Once the value is set, the set of
	 * possibilities is cleared.
	 * @param value Value to assign
	 */
	public void setValue(int value) {
		this.value = value;
		possibilities.clear();
	}
	
	/**
	 * Removes a possibility from the set
	 * @param value The possible value this square can no longer be
	 */
	public void removePossibility(int value) {
		possibilities.remove(value);
	}
}
