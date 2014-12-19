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
    private Level1State reference;
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

    /*public void checkBlockCollision(ArrayList<Block> list){
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
    }*/
    
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
                        // Collision is on the bottom side of this object
                        if(getDy()>0)
                            setDy(0);
                        setYTemp(list.get(i).getYpos()-getHeight());
                        setFalling(false);
                    }else{
                        // Collision is from the left side of this object 
                        if(lengthDistance(list.get(i))>=16){
                            setDx(tempDx);
                            setXTemp(tempX);
                        }else{
                            setXTemp(list.get(i).getXpos()+getWidth());
                            turn();
                        }
                        lft=true;
                    }
                }else{
                    if(wy>-hx){
                        // Collision is on the right side of this object
                        setXTemp(list.get(i).getXpos()-getWidth()-1);
                        if(lengthDistance(list.get(i))>=16){
                            setDx(tempDx);
                            setXTemp(tempX);
                        }else{
                            turn();
                        }
                        rgt=true;
                    }else{
                        // Collision is on the top of this object
                        // THIS SHOULD NEVER HAPPEN
                        if(getDy()<0)
                            setDy(0);
                        setYTemp(list.get(i).getYpos()+list.get(i).getHeight()+1);
                    }
                }
                if(rgt&&lft){
                    setDx(tempDx);
                    setXTemp(tempX);
                }
            }
        }
    }

    // are there even enemy collisions in the old mario game?
    public void checkEnemyCollision(ArrayList<Enemy> list){
        //setFalling(true);
        //double tempDx=getDx();
        //double tempX=getXTemp();
        for(int i=0;i<list.size();i++){
            //boolean rgt=false;
            //boolean lft=false;
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
                        // This will probably never happen
                        System.out.println("Not supposed to happen :: Koopa");
                    }else{
                        // Collision is from the left
                        if(!shell){
                            turn();
                            list.get(i).turn();
                        }else{
                            list.get(i--).hitByProjectile(this);
                        }
                    }
                }else{
                    if(wy>-hx){
                        // Collision is on the right
                        if(!shell){
                            turn();
                            list.get(i).turn();
                        }else{
                            list.get(i--).hitByProjectile(this);
                        }
                    }else{
                        // Collision is on the bottom
                        System.out.println("Not supposed to happen :: Koopa");
                    }
                }
                //if(rgt&&lft){
                //    setDx(tempDx);
                //    setXTemp(tempX);
                //}
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
    
    public boolean hitByProjectile(Projectile projectile){
        // implement
        return false;
    }

    // Projectile methods

    public void update(){
        // will do nothing
    }

    public void addProjectileToList(Level1State state){
        if(reference==null)
            reference=state;
        //state.getProjectiles().add(this);
        state.getEnemies().remove(this); // update this later
    }

    public void removeProjectileFromList(Level1State state){
        //state.getProjectiles().remove(this);
    }

    public void enemyHit(Enemy enemy){
        // I dont think this is needed
    }

    public void checkProjectileCollision(ArrayList<Projectile> list){
    //    for(int i=0;i<list.size();i++){
    //        double w=.5*(list.get(i).getCWidth()+getWidth());
    ///        double h=.5*(list.get(i).getCHeight()+getHeight());
    //       double dx=list.get(i).getCCenterX()-getCenterX();
    ///        double dy=list.get(i).getCCenterY()-getCenterY();
    //       if(Math.abs(dx)<=w&&Math.abs(dy)<=h){
    //            projectileHit(projectile);
    //            i--;
    //        }
    //    }
    }

    public void projectileHit(Projectile projectile){
        projectile.removeProjectileFromList(reference);
        removeProjectileFromList(reference);
    }

    // this projectile can always hurt the player
    public boolean getPlayer(){return false;}
    // this projectile can always hurt the enemy
    public boolean getEnemy(){return false;}
    
    // getter methods
    
    // setter methods
    public void setFlying(boolean param){flying=param;}
}
