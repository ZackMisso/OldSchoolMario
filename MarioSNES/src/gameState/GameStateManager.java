/**
 *
 * @author Zackary Misso
 * 
 */
package gameState;
import gameState.Level1State;
import java.util.ArrayList;
import java.awt.Graphics2D;
public class GameStateManager {
    // THIS CLASS IS DEPRECIATED.... REMOVE IT SOON
    private ArrayList<GameState> gameStates;
    private int currentState;
    public static final int MENUSTATE=0;
    public static final int LEVEL1STATE=0;
    
    public GameStateManager(){
        gameStates=new ArrayList<>();
        currentState=LEVEL1STATE;
        gameStates.add(new Level1State(this));
    }
    
    public void setState(int state){
        currentState=state;
        gameStates.get(currentState).init();
    }
    
    public void update(){
        gameStates.get(currentState).update();
    }
    
    public void draw(Graphics2D g){
        gameStates.get(currentState).draw(g);
    }
    
    public void keyPressed(int k){
        gameStates.get(currentState).keyPressed(k);
    }
    
    public void keyReleased(int k){
        gameStates.get(currentState).keyReleased(k);
    }

    // getter methods
    public ArrayList<GameState> getGameStates(){return gameStates;}
}
