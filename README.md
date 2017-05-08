# java-game-of-life

Author: Sharif Shaker

Date of Creation: 5/8/2017

DESCRIPTION: Small simulation of Conway's Game of Life. 

BASIC INFO: Files for running a graphical representation of Conway's Game of Life.  Uses a grid of squares representing living and
dead cells.  The user can edit the size of the board and what colors to use for representing dead and living cells.  The board is 
initialized to randomly fill with living and dead cells, with white cells being dead and black cells being alive.  The user can 
then do various operations such as starting, stopping or stepping through the simulation and clearing or randomizing the board.  

Cells can be switched on and off manually be clicking the cursor over a cell to alter its state.  Also, holding and draging the cursor 
will switch the states of all the cells that are passed over. 

ALGORITHM: The game of life is a zero player game in which cells in a grid are either living or dead and the current state of the grid
determines the next state.  A living cell switches to dead if it has less than 2 or more than 3 neighboring cells that are alive.  A living
cell remains alive if it has 2 or 3 adjacent living cells. A dead cell become alize if it has exaclty 3 living neighbors. 

This implementation of Life wraps edges of the grid around to crate a closed system, so the rightmost cells are considered adjacent to 
the leftmost cells and the leftmost cells are likewise adjacent to the rightmost.  The top row of cells is considered adjacent to the 
bottom row of cells and vice versa.  For this reason the minumum grid size is 3x3 so as to prevent a single cell being adjacent to the 
same node from differnet directions.


