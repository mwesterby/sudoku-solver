# sudoku-solver
![Code Coverage Badge](.github/badges/jacoco.svg)

A quick Sudoku solver I created one afternoon. By no means is this the most optimal! Just thought it would be a fun problem to solve. Feel free to suggest improvements.

I created this to work in the same way I would solve a sudoku. _i.e._ figure out what the possible values _could_ be in each square. If this is only one value, assign this to the square. Else, if this is the only square in the row, column and subgrid that can have that value, assign it.

### Running the Tests
The tests are located in [`/src/test/java/solver`](./src/main/test/java/solver). Use maven to run the tests with:
```
mvn test
```

### How it works

Unsolved Sudoku boards are represented as an `int[27][3]` array, with blank squares filled in as `0`. I felt this provided the most human readable way of entering the values while still being able to see the row, column and subgrid arrangement. e.g.:

```java
{0, 0, 9}, {0, 4, 0}, {0, 0, 0}, 
{0, 0, 0}, {0, 0, 5}, {3, 1, 0},
{0, 6, 1}, {0, 0, 8}, {0, 5, 0},

{0, 0, 5}, {4, 0, 0}, {2, 0, 3},
{0, 1, 0}, {0, 0, 7}, {0, 0, 8},
{0, 8, 0}, {0, 0, 0}, {7, 6, 0},

{3, 0, 6}, {0, 1, 9}, {4, 0, 0},
{7, 0, 0}, {0, 0, 0}, {0, 0, 0},
{0, 0, 4}, {0, 5, 0}, {6, 2, 7},
```

This `int[27][3]` array can be used to instantiate a `Grid` object (`Grid(int[][] unsolvedBoard)`). This will create an empty Sudoku grid and then add all the values to it which are present on the unsolved board. Every square on the board has a list of possible values it _could_ be. Each time a value is added to a square, squares which are in the same row, column or subgrid will have that value removed from their list of possibilities.

When you solve the grid using `grid.solve()`, we look at this list of possibilities. If there is only one number a square could be, it is assigned that value. Additionally, if a square is the only square in its row and column which _could_ have that value, and that value is not currently in the same subgrid as the square, it is assigned that value.

The `solve()` method will print the solved board to the console (along with a running log of each value and the coordinate it is added to). e.g. for the unsolved board above, you may see somthing like this:

```
Starting Grid:
+-------+-------+-------+
| 0 0 9 | 0 4 0 | 0 0 0 |
| 0 0 0 | 0 0 5 | 3 1 0 |
| 0 6 1 | 0 0 8 | 0 5 0 |
+-------+-------+-------+
| 0 0 5 | 4 0 0 | 2 0 3 |
| 0 1 0 | 0 0 7 | 0 0 8 |
| 0 8 0 | 0 0 0 | 7 6 0 |
+-------+-------+-------+
| 3 0 6 | 0 1 9 | 4 0 0 |
| 7 0 0 | 0 0 0 | 0 0 0 |
| 0 0 4 | 0 5 0 | 6 2 7 |
+-------+-------+-------+

Added 8 to (0, 6)
Added 7 to (0, 7)

...

Added 9 to (1, 3)
Added 2 to (1, 4)

Solution: 
+-------+-------+-------+
| 5 3 9 | 1 4 6 | 8 7 2 |
| 8 4 7 | 9 2 5 | 3 1 6 |
| 2 6 1 | 3 7 8 | 9 5 4 |
+-------+-------+-------+
| 6 7 5 | 4 8 1 | 2 9 3 |
| 9 1 2 | 6 3 7 | 5 4 8 |
| 4 8 3 | 5 9 2 | 7 6 1 |
+-------+-------+-------+
| 3 2 6 | 7 1 9 | 4 8 5 |
| 7 5 8 | 2 6 4 | 1 3 9 |
| 1 9 4 | 8 5 3 | 6 2 7 |
+-------+-------+-------+
```

The solved board can also be returned as a `int[27][3]` array using the `gridToArray()` method.

### Limitations

There is currenlty no validation of the unsolved board which is passed in. This means the program may get stuck if it's trying to solve an invalid board.
