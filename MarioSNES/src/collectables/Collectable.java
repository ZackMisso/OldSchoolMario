/**
 *
 * @author Zackary Misso
 *
 */
package collectables;
import gameState.PlayerState;
import gameState.Level1State;
import entities.Mario;
public interface Collectable {
    public void update();
    public void hit(PlayerState state,Mario mario);
    public void removeSelfFromList(Level1State state);
}
