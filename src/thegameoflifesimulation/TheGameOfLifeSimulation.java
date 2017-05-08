package thegameoflifesimulation;

/**
 * This class contains the main method and runs simulation
 *
 * @author Sharif Shaker
 * @version 4/6/2017
 */
public class TheGameOfLifeSimulation {

    /**
     * runs simulation of game of life
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // initialize and start the simulation
        LifeSimulationRunner runner = new LifeSimulationRunner();
        runner.startLife();

    }

}
