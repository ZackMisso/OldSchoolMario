/**
 *
 * @author Zackary Misso
 * 
 */
package core;
public class GlobalController {
    // TODO :: Implement functionality for some of these
    public static String aiFileName;
    public static final double MAX_WEIGHT_VALUE=5;
	public static final double MIN_WEIGHT_VALUE=-5;
    public static final int MAX_NEURONS=200;
    public static final int MIN_NEURONS=9;
    public static final int MAX_CONNECTIONS=10000;
    public static final int MIN_CONNECTIONS=6;
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
    
    // set default values for global variables
    public static void init(){
        aiFileName="";
        individuals=100;
        generations=50;
        evolving=true;
        aiRun=false;
        debug=false;
        headless=false;
        gameRunning=false;
        running=true;
    }

    // I dont think the two below are needed
    
    //public static void transferControlToGame(NeuralNetwork net){
    //    // implement
    //}
    
    //public static void transferControlToEvolution(){
    //    // implement
    //}
}