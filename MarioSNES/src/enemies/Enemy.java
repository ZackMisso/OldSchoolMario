/**
 *
 * @author Zackary Misso
 * 
 */
package enemies;
import entities.GameEntity;
import entities.Block;
import entities.Mario;
import gameState.PlayerState;
import java.util.ArrayList;
public abstract class Enemy extends GameEntity{
	// TODO :: FIX THIS CLASS
    public abstract void update(ArrayList<Block> blocks);
    public abstract void turn();
    public abstract void checkBlockCollision(ArrayList<Block> list);
    public abstract boolean hit(PlayerState state,Mario mario); // returns true if killed
    public abstract void killed();
}
