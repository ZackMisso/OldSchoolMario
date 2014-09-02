/**
 *
 * @author Zackary Misso
 * 
 */
package core;
public class GlobalController {
    // TODO :: Implement functionality for some of these
    public static String aiFileName;
    public static final double MAX_WEIGHT_VALUE=10;
	public static final double MIN_WEIGHT_VALUE=-10;
    public static final int MAX_NEURONS=8;
    public static final int MIN_NEURONS=3;
    public static final int MAX_CONNECTIONS=100;
    public static final int MIN_CONNECTIONS=2;
    public static int individuals;
    public static int generations;
    public static boolean evolving; // if the player is playing or not
    public static boolean aiRun; // if a single AI is playing the game
    public static boolean debug; // if debug features should be included
    public static boolean headless; // if graphics should not be drawn
    public static boolean gameRunning; // this really should not be needed but just in case
    public static boolean interupt; // an interupt that allows the evolution to stop early
    
    // set default values for global variables
    public static void init(){
        aiFileName="";
        individuals=0;
        generations=0;
        evolving=false;
        aiRun=false;
        debug=false;
        headless=false;
        gameRunning=false;
    }
    
    //public static void transferControlToGame(NeuralNetwork net){
    //    // implement
    //}
    
    public static void transferControlToEvolution(){
        // implement
    }
}
