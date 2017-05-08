
package thegameoflifesimulation;

import java.awt.*;
import javax.swing.*;
import java.util.Random;


/**
 * Object to represent the grid running the game of life
 * 
 * @author Sharif Shaker
 * @version 4/6/2017
 */
public class Grid {

    private int numRows;
    private int numCols;
    private JPanel grid;
    private Cell[][] cells;

    /**
     * takes the number of rows and columns of the grid as a parameter
     * @param rows number of rows in the grid
     * @param cols number of columns in the grid
     * @param deadColor color of a dead cell
     * @param aliveColor color of a live cell
     */
    public Grid(int rows, int cols, Color deadColor, Color aliveColor) {
        numRows = rows;
        numCols = cols;
        /* initializes a JPanel set up as a grid layout of the 
        specified number of rows and columns*/
        grid = new JPanel(new GridLayout(rows, cols, 1, 1));
        cells = new Cell[rows][cols]; // represents a table of cells

        /*
        for each location in the grid layout create and add a cell
        then add that cell to the specified location of the cells table
        */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // create new cell with given colors
                Cell cell = new Cell(deadColor, aliveColor);
                cell.setPreferredSize(new Dimension(10, 10));
                grid.add(cell);
                cells[i][j] = cell;
            }
        }

    }

    /**
     * 
     * @return JPanel representation of the grid
     */
    public JPanel getGridPanel() {
        return grid;
    }

    /**
     * runs through one generation of the game of life 
     * discovering which cells should be alive and which should
     * die and then repaints the JPanel
     */
    public void runGeneration() {

        int neighbors;
        boolean[][] livingTable = new boolean[numRows][numCols];
        int top;
        int bot;
        int rgt;
        int lft;
        /*
        for each cell, check the cells around it and if 
        an adjacent cell is alive, increment the number of 
        neighbors
        */
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                top = (j > 0 ? j - 1 : numCols - 1);
                bot = (j < numCols - 1 ? j + 1 : 0);
                rgt = (i < numRows - 1 ? i + 1 : 0);
                lft = (i > 0 ? i - 1 : numRows - 1);
                neighbors = 0;
                if (cells[i][top].isLiving()) {
                    neighbors++;
                }
                if (cells[rgt][top].isLiving()) {
                    neighbors++;
                }
                if (cells[rgt][j].isLiving()) {
                    neighbors++;
                }
                if (cells[rgt][bot].isLiving()) {
                    neighbors++;
                }
                if (cells[i][bot].isLiving()) {
                    neighbors++;
                }
                if (cells[lft][bot].isLiving()) {
                    neighbors++;
                }
                if (cells[lft][j].isLiving()) {
                    neighbors++;
                }
                if (cells[lft][top].isLiving()) {
                    neighbors++;
                }

                /*
                using the number of neighbors, determine if the cell at the 
                given location is alive or dead and set the value in the 
                livingTable accordingly
                */
                livingTable[i][j] = cells[i][j].isAlive(neighbors);

            }
        }

        /*
        for each cell in the cells table set whether it is alive based on the 
        corresponding value in the livingTable
        */
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cells[i][j].setAlive(livingTable[i][j]);
            }
        }

        // repaint the grid to represent the new generation
        grid.repaint();

    }
    
    /**
     * goes through every cell and sets them all to dead
     */
    public void clear(){
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cells[i][j].setAlive(false);
            }
        }

        // repaint the cleared grid
        grid.repaint();
    }
    
    /**
     * goes through every cell assigning each a random living or dead 
     * value
     */
    public void random(){
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cells[i][j].setAlive(random.nextBoolean());
            }
        }

        // repaint randomized grid 
        grid.repaint();
    }

}
