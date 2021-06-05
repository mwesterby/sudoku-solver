# sudoku-solver
![Code Coverage Badge](.github/badges/jacoco.svg)


A quick sudoku solver I created one afternoon. By no means is this the most optimal! Just thought it would be a fun problem to solve. Feel free to suggest improvements.

I created this to work in the same way I would solve a sudoku. i.e figure out what the possible values _could_ be in each square. If this is only one value, assign this to the square. Else, if this is the only square in the row, column and subgrid that can have that value, assign it.
