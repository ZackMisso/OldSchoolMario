/**
 *
 * @author Zackary Misso
 * 
 */
package enemies;
import entities.Mario;
import entities.Block;
import gameState.PlayerState;
import projectiles.Projectile;
import java.util.ArrayList;
public class FirePiranhaPlant extends Enemy{
    public FirePiranhaPlant(){
        // implement
    }
    
    public void update(ArrayList<Block> blocks){
        // implememnt
    }
    
    public void turn(){
        // implement
    }
    
    public void checkBlockCollision(ArrayList<Block> list){
        // implement
    }
    
    public void checkEnemyCollision(ArrayList<Enemy> list){
        // implement
    }
    
    public boolean hit(PlayerState state,Mario mario){
        // implement
        return false;
    }
    
    public boolean hitByProjectile(Projectile projectile){
        // implement
        return false;
    }
    
    public void killed(){
        // implement
    }
}