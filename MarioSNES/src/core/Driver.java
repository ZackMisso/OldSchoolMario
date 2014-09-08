/**
 *
 * @author Zackary Misso
 * 
 */
package core;
public class Driver {
    public static void main(String[] args){
        GlobalController.init();
        if(GlobalController.evolving)
            System.out.println("This program is evolving an AI to play Mario");
        else if(GlobalController.aiRun)
            System.out.println("The game is playing in AI mode");
        else
            System.out.println("The game is playing in player mode");
        new Game();
    }
}
