/**
 *
 * @author Zackary Misso
 * 
 */
package enemies;
import entities.Block;
import gameState.PlayerState;
import java.util.ArrayList;
public interface Enemy{
    public void update();
    public void turn();
    public void checkBlockCollision(ArrayList<Block> list);
    public void killed(PlayerState param);
}
