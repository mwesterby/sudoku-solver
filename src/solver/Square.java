package solver;

import java.util.HashSet;

public class Square {

	private int value;
	private HashSet<Integer> possibilities;
	private int row;
	private int col;
	
	public Square(int row, int col) {
		this.row = row;
		this.col = col;
		
		possibilities = new HashSet<Integer>();
		for(int i = 1; i <= 9; i++) {
			possibilities.add(i);
		}
		value = 0;
	}
	
	public int getValue() {
		return value;
	}
	
	public HashSet<Integer> getPossibilities() {
		return possibilities;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setValue(int value) {
		this.value = value;
		possibilities.clear();
	}
	
	public void removePossibility(int value) {
		possibilities.remove(value);
	}
}
