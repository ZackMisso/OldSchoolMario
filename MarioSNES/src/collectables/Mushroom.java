/**
 *
 * @author Zack Misso
 *
 */
package collectables;
import gameState.PlayerState;
import gameState.Level1State;
import entities.GameEntity;
import entities.Mario;
public class Mushroom extends GameEntity implements Collectable{
    public Mushroom(){
    	// implement
    }

    public Mushroom(int x,int y){
    	setXpos(x);
    	setYpos(y);
    }

    public void update(){
    	// does nothing
    }

    public void hit(PlayerState state,Mario mario){
    	// implement
    }

    public void removeSelfFromList(Level1State state){
    	// implement
    }
}