package test.java.solver;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import main.java.solver.Grid;
import main.java.solver.Solver;

public class SudokuTest {

	@Test
	public void canSolve() {
		
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
		
		// Solve the sudoku
		grid.solve();
		
		// Get the solved grid
		int[][] actual = grid.gridToArray();
		
		int[][] expected = new int[][] { 
			{5, 3, 9}, {1, 4, 6}, {8, 7, 2}, 
			{8, 4, 7}, {9, 2, 5}, {3, 1, 6},
			{2, 6, 1}, {3, 7, 8}, {9, 5, 4},

			{6, 7, 5}, {4, 8, 1}, {2, 9, 3},
			{9, 1, 2}, {6, 3, 7}, {5, 4, 8},
			{4, 8, 3}, {5, 9, 2}, {7, 6, 1},

			{3, 2, 6}, {7, 1, 9}, {4, 8, 5},
			{7, 5, 8}, {2, 6, 4}, {1, 3, 9},
			{1, 9, 4}, {8, 5, 3}, {6, 2, 7},
			
		};
		
		assertArrayEquals(expected, actual);
		
	}
	
}
