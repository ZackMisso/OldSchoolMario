/**
 *
 * @author Zackary Misso
 * 
 */
package enemies;
import entities.GameEntity;
import entities.Block;
import entities.Mario;
import projectiles.Projectile;
import gameState.PlayerState;
import gameState.Level1State;
import java.util.ArrayList;
public abstract class Enemy extends GameEntity{
    private Level1State reference;
	private boolean killedByTop;

	public Enemy(){
        reference=null;
		killedByTop=true;
	}

    public void addEnemyToList(Level1State state){
        if(reference==null)
            reference=state;
        state.getEnemies().add(this);
    }

    public void removeEnemyFromList(){
        reference.getEnemies().remove(this);
    }

	// TODO :: FIX THIS CLASS
    public abstract void update(ArrayList<Block> blocks);
    public abstract void turn();
    public abstract void checkBlockCollision(ArrayList<Block> list);
    public abstract void checkEnemyCollision(ArrayList<Enemy> list);
    public abstract boolean hit(PlayerState state,Mario mario); // returns true if killed
    public abstract boolean hitByProjectile(Projectile projectile);
    public abstract void killed();

    // getter methods
    public boolean getKilledByTop(){return killedByTop;}

    // setter methods
    public void setKilledByTop(boolean param){killedByTop=param;}
}
