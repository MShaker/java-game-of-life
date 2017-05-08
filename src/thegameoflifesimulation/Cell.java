
package thegameoflifesimulation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
/**
 * object to represent a cell in the grid game of life that extends JPanel
 * 
 * @author Sharif Shaker
 * @version 4/6/2017
 */
public class Cell extends JPanel{
    
    private boolean living;
    private final Random random = new Random();
    
    private Color aliveColor = Color.BLACK; 
    /**
     * constructor for a cell adds a click listener and sets a living value
     * @param dColor color of a cell that is dead
     * @param aColor color of a cell that is alive
     */
    public Cell(Color dColor, Color aColor){
        /*
        adds a mouse listener to the panel so that a cell can be switched
        to living or dead by the user
        */
        MouseListener listener = new MouseAdapter() {
            /**
             * if the mouse is pressed over the cell it switches states
             * @param e mouse event object
             */
            public void mousePressed(MouseEvent e){
                living = !living;
                repaint();
            }
            
            /**
             * if the mouse is pressed and cursor passes into the cell it
             * switches states
             * @param e mouse event object
             */
            public void mouseEntered(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    living = !living;
                    repaint();
                }
            }
        };
        
        // add mouse listner
        this.addMouseListener(listener);
        // set the background of the grid to the dead cell color
        setBackground(dColor);
        // set the alive color variable
        aliveColor = aColor;
        // set living to a random value when initialized
        living = random.nextBoolean();
    }
    
    
   /**
    * determines if the given cell should be alive based on the number of 
    * adjacent living cells
    * @param neighbors the number of adjacent living cells 
    * @return true if the cell should be alive and false otherwise
    */
    public boolean isAlive(int neighbors){
        
        /* 
        if there are 3 living neighbors and this cell is dead
        this set becomes alive to return true
        */
        if(neighbors == 3 && !living) {
           
            return true;
        }
        
        /*
        return true if cell is already alive and has 2 or 3 neighbors
        and false otherwise
        */
        return neighbors > 1 && neighbors < 4 && living;
    }
    
    /**
     * set whether cell is living or dead
     * @param alive boolean to determine living or dead
     */
    public void setAlive(boolean alive){
        living = alive;
    }
    
    /**
     * 
     * @return return true if this cell is alive 
     */
    public boolean isLiving(){
        return living;
    }
    
    /**
     * paints the cell to reflect if it is living or dead
     * @param g graphic object for painting
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // set fill color to aliveColor
        g.setColor(aliveColor);
        if (living) {
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        } else {
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
    
}
