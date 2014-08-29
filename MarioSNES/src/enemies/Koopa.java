/**
 *
 * @author Zackary Misso
 *
 */
package enemies;
import gameState.PlayerState;
import gameState.Level1State;
import entities.Block;
import entities.Mario;
import enemies.Enemy;
import projectiles.Projectile;
import java.util.ArrayList;
public class Koopa extends Enemy implements Projectile{
	private double shellSpeed;
    private boolean facingRight;
    private boolean flying; // implement later
    private boolean shell; // implement later

    public Koopa(){
    	this(false);
    }

    public Koopa(boolean fly){
        shellSpeed=4.0;
        facingRight=false;
        flying=fly;
        shell=false;
    }

    public void update(ArrayList<Block> blocks){
    	// implement
    }

    public void turn(){
    	setDx(-getDx());
        facingRight=!facingRight;
    }

    public void checkBlockCollision(ArrayList<Block> list){
        setFalling(true);
        double tempDx=getDx();
        double tempX=getXTemp();
    	for(int i=0;i<list.size();i++){
            boolean rgt=false;
            boolean lft=false;
            double w=.5*(list.get(i).getCWidth()+getWidth());
            double h=.5*(list.get(i).getCHeight()+getHeight());
            double dx=list.get(i).getCCenterX()-getCenterX();
            double dy=list.get(i).getCCenterY()-getCenterY();
            if(Math.abs(dx)<=w&&Math.abs(dy)<=h){
                double wy=w*dy;
                double hx=h*dx;
                if(wy>hx){
                    if(wy>-hx){
                        // Collision is on the top
                    }else{
                        // Collision is from the left
                    }
                }else{
                    if(wy>-hx){
                        // Collision is on the right
                    }else{
                        // Collision is on the bottom
                    }
                }
                if(rgt&&lft){
                    setDx(tempDx);
                    setXTemp(tempX);
                }
            }
        }
    }

    public boolean hit(PlayerState param,Mario mario){
    	// TODO :: IMPLEMENT PROJECTILE INFO FOR THE KOOPA
    	mario.rebound();
    	if(flying){
    		flying=false;
    		if(getDy()<0)
    			setDy(0);
    		return false;
    	}else if(!shell){
    		shell=true;
    		if(getDy()<0)
    			setDy(0);
    		setDx(0);
    		return false;
    	}else{
    		// hit when shell
    		if(getDx()==0.0){
    			if(facingRight)
    				setDx(shellSpeed);
    			else
    				setDx(-shellSpeed);
    		}else
    			setDx(0.0);
    		return false;
    	}
    }

    public void killed(){
    	// IDK if I am going to use this method yet
    }

    // Projectile methods

    public void update(){
        // will do nothing
    }

    public void addProjectileToList(Level1State state){
        // implement
    }

    public void removeProjectileFromList(Level1State state){
        // implement
    }

    public void enemyHit(Enemy enemy){
        // implement
    }

    public void checkProjectileCollision(ArrayList<Projectile> list){
        // implment
    }

    public void projectileHit(Projectile projectile){
        // implement
    }

    // this projectile can always hurt the player
    public boolean getPlayer(){return false;}
    // this projectile can always hurt the enemy
    public boolean getEnemy(){return false;}
}
