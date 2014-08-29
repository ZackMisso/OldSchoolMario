/**
 *
 * @author Zackary Misso
 *
 */
package projectiles;
import enemies.Enemy;
import gameState.PlayerState;
import gameState.Level1State;
import entities.Mario;
import enemies.Enemy;
import java.util.ArrayList;
public interface Projectile{ // TODO :: moke this into an abstract class
    public void addProjectileToList(Level1State state);
    public void removeProjectileFromList(Level1State state);
    public void update();
    public boolean hit(PlayerState param,Mario mario);
    public void enemyHit(Enemy enemy);
    public void checkProjectileCollision(ArrayList<Projectile> projectile);
    public void checkEnemyCollision(ArrayList<Enemy> enemy);
    public void projectileHit(Projectile projectile);
    public boolean getPlayer();
    public boolean getEnemy();
}