/**
 *
 * @author Zackary Misso
 * 
 */
package core;
public class GlobalController {
    // TODO :: Implement functionality for some of these
    public static String aiFileName;
    public static String levelFile;
    public static final double MAX_WEIGHT_VALUE=10;
    public static final double MIN_WEIGHT_VALUE=-10;
    public static final int MAX_NEURONS=200;
    public static final int MIN_NEURONS=3;
    public static final int MAX_CONNECTIONS=10000;
    public static final int MIN_CONNECTIONS=1;
    public static int individuals;
    public static int generations;
    public static boolean evolving; // if the player is playing or not
    public static boolean aiRun; // if a single AI is playing the game
    public static boolean debug; // if debug features should be included
    public static boolean headless; // if graphics should not be drawn
    public static boolean gameRunning; // this really should not be needed but just in case
    public static boolean running;
    public static boolean interupt; // an interupt that allows the evolution to stop early
    public static boolean waitingOn;
    public static boolean testInput; // unit test TestInputLevel
    // Depreciated :: Zack
    //public static boolean tilePhysics; // if tile Physics are being used
    //public static boolean lolsPhysics; // broken physics
    //public static int hack; // this is not needed anymore
    
    // set default values for global variables
    public static void init(){
        aiFileName="TOTALbestRun_280975.0";
        levelFile="jumpingtest.txt";
        //levelFile = "firstLevel.txt";
        individuals = 50;
        generations = 100;
        evolving = false;
        aiRun = false;
        debug = false; // currently causes the rects of objects to be drawn
        headless = false;
        gameRunning = true;
        running = true;
        testInput = true;
        // Depreciated :: Zack
        //tilePhysics = false;
        //lolsPhysics = false; // LEAVE TRUE UNTIL ACTUAL PHYSICS RUNS (ALL HELL WILL BREAK LOOSE OTHERWISE)
        //hack=1;
    }

    // I dont think the two below are needed
    
    //public static void transferControlToGame(NeuralNetwork net){
    //    // implement
    //}
    
    //public static void transferControlToEvolution(){
    //    // implement
    //} 
}