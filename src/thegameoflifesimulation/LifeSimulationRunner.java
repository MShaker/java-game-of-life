
package thegameoflifesimulation;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class to handle the actual running of the game of life simulation with 
 * various GUI components
 * 
 * @author Sharif Shaker
 * @version 4/6/2017
 */
public class LifeSimulationRunner {

    private static Grid grid;
    private static final JFrame window = new JFrame();
    private static boolean isRunning = true;
    private static int pause = 50;

    /**
     * constructs object and sets up the GUI components
     */
    public LifeSimulationRunner() {
        // construct an initial grid with preset values
        grid = new Grid(40, 80, Color.WHITE, Color.BLACK);
        
        // set up main JFrame that holds various the various gui components
        window.setSize(1800, 1000);
        window.setLayout(new BorderLayout());

        // set up simulation control panel
        JPanel startStopPanel = new JPanel();
        startStopPanel.setLayout(new FlowLayout());
        JLabel status = new JLabel("RUNNING...");
        JButton startBut = new JButton("START");
        JButton pauseBut = new JButton("PAUSE");
        JButton stepBut = new JButton("STEP");
        JButton clearGridBut = new JButton("CLEAR");
        JButton randomGridBut = new JButton("RANDOM");
        startStopPanel.add(status);
        startStopPanel.add(startBut);
        startStopPanel.add(pauseBut);
        startStopPanel.add(stepBut);
        startStopPanel.add(clearGridBut);
        startStopPanel.add(randomGridBut);
        // add control panel to JFrame
        window.add(startStopPanel, BorderLayout.SOUTH);

        // set up panel to reset grid options
        JPanel gridOptionsPanel = new JPanel();
        gridOptionsPanel.setLayout(new FlowLayout());
        JButton resetBut = new JButton("RESET");
        JTextField pauseTime = new JTextField();
        pauseTime.setColumns(5);
        pauseTime.setText("50");
        JTextField gridRows = new JTextField();
        gridRows.setColumns(4);
        gridRows.setText("40");
        JTextField gridCols = new JTextField();
        gridCols.setColumns(4);
        gridCols.setText("80");

        // create hashmap of colors for color selection spinner
        HashMap<String, Color> colorMap = new HashMap<>();
        colorMap.put("black", Color.BLACK);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("cyan", Color.CYAN);
        colorMap.put("dark gray", Color.DARK_GRAY);
        colorMap.put("gray", Color.GRAY);
        colorMap.put("green", Color.GREEN);
        colorMap.put("light gray", Color.LIGHT_GRAY);
        colorMap.put("magenta", Color.MAGENTA);
        colorMap.put("orange", Color.ORANGE);
        colorMap.put("pink", Color.PINK);
        colorMap.put("red", Color.RED);
        colorMap.put("white", Color.WHITE);
        colorMap.put("yellow", Color.YELLOW);

        // convert keys of map into list of strings
        Set<String> colorKeys = colorMap.keySet();
        String[] colorList = colorKeys.toArray(new String[colorKeys.size()]);

        // use list of color strings to create two color spinner list models
        SpinnerModel deadColorModel = new SpinnerListModel(Arrays.asList(colorList));
        SpinnerModel aliveColorModel = new SpinnerListModel(Arrays.asList(colorList));
        

        /* 
        create the spinners with the list models for alive cells 
        and dead cells
         */
        JSpinner deadColorSpinner = new JSpinner(deadColorModel);
        deadColorSpinner.setValue("white");
        Component deadEditor = deadColorSpinner.getEditor();
        JFormattedTextField deadTextField = ((JSpinner.DefaultEditor) deadEditor).getTextField();
        deadTextField.setColumns(6);

        JSpinner aliveColorSpinner = new JSpinner(aliveColorModel);
        aliveColorSpinner.setValue("black");
        Component aliveEditor = aliveColorSpinner.getEditor();
        JFormattedTextField aliveTextField = ((JSpinner.DefaultEditor) aliveEditor).getTextField();
        aliveTextField.setColumns(6);

        // add components to options panel
        gridOptionsPanel.add(resetBut);
        gridOptionsPanel.add(new JLabel("delay:"));
        gridOptionsPanel.add(pauseTime);
        gridOptionsPanel.add(new JLabel("rows:"));
        gridOptionsPanel.add(gridRows);
        gridOptionsPanel.add(new JLabel("columns:"));
        gridOptionsPanel.add(gridCols);
        gridOptionsPanel.add(new JLabel("dead cell color:"));
        gridOptionsPanel.add(deadColorSpinner);
        gridOptionsPanel.add(new JLabel("living cell color:"));
        gridOptionsPanel.add(aliveColorSpinner);

        // add panel to JFrame
        window.add(gridOptionsPanel, BorderLayout.NORTH);

        // add the JPanel representation of the grid to the JFrame
        window.add(grid.getGridPanel(), BorderLayout.CENTER);

        // set to visible
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
        start button to set isRnning boolean to true to begin running 
        generations
         */
        startBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText("RUNNING...");
                isRunning = true;
            }

        });

        /*
        pause button to set isRnning boolean to false to stop running 
        generations
         */
        pauseBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText("PAUSED");
                isRunning = false;
            }

        });

        /*
        button to step the simulation through 1 generation
         */
        stepBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // pause simulation if currently running
                if (isRunning) {
                    status.setText("PAUSED");
                    isRunning = false;
                }
                grid.runGeneration();
            }

        });

        /*
        button pauses simulation and then clears grid so all cells are dead
         */
        clearGridBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText("PAUSED");
                isRunning = false;
                grid.clear();
            }

        });

        /*
        button pauses simulation and then randomizes cell values
         */
        randomGridBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText("PAUSED");
                isRunning = false;
                grid.random();
            }

        });

        /*
        button pauses the simulation and then resets the grid using the input 
        delay, rows and columns values
         */
        resetBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check that dimensions are made up of non-negative integer values
                if(!gridRows.getText().matches("[0-9]+") || !gridCols.getText().matches("[0-9]+")){
                    JOptionPane.showMessageDialog(null, "Invalid row and columns input.  Must be positive integer value");
                    return;
                }
                // check that dimensions are at least 3x3
                if(Integer.parseInt(gridRows.getText()) < 3 || Integer.parseInt(gridCols.getText()) < 3) {
                    JOptionPane.showMessageDialog(null, "Invalid grid dimensions."
                            + " Grid must be at least 3x3");
                    return;
                }
                // check that delay time is a non-negative integer value
                if(!pauseTime.getText().matches("[0-9]+")){
                    JOptionPane.showMessageDialog(null, "Invalid delay time.  Must be positive integer input");
                    return;
                }
                
                isRunning = false;
                //removes the current grid from the JFrame
                window.remove(grid.getGridPanel());
                // set pause value
                pause = Integer.parseInt(pauseTime.getText());

                /* 
                reset grid to be a new grid with the input dimensions and colors
                 */
                grid = new Grid(Integer.parseInt(gridRows.getText()),
                        Integer.parseInt(gridCols.getText()),
                        colorMap.get((String) deadColorSpinner.getValue()),
                        colorMap.get((String) aliveColorSpinner.getValue()));
                // add the new grid to the JFrame
                window.add(grid.getGridPanel(), BorderLayout.CENTER);
                // set visibility to see the changes
                window.setVisible(true);
                status.setText("PAUSED");
                
            }

        });

    }

    public void startLife() {

        // while pogram is running
        while (true) {
            // if simulation is running update generation
            if (isRunning) {
                grid.runGeneration();
            }

            // sleep for the desired pause time
            try {
                Thread.sleep(pause);
            } catch (InterruptedException ex) {
                Logger.getLogger(TheGameOfLifeSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
