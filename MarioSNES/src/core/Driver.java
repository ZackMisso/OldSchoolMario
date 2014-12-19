/**
 *
 * @author Zackary Misso
 * 
 */
package core;
public class Driver {
    public static void main(String[] args){
        GlobalController.init();
        if(GlobalController.evolving){
            if(GlobalController.speciation)
                System.out.println("This program is evolving an AI to play Mario by using speciation :: Driver");
            else
                System.out.println("This program is evolving an AI to play Mario :: Driver 11");
        }
        else if(GlobalController.aiRun)
            System.out.println("The game is playing in AI mode :: Driver 13");
        else
            System.out.println("The game is playing in player mode :: Driver 15");
        new Game();
    }
}
