/**
 *
 * @author Zackary Misso
 * 
 */
package enemies;
import entities.Block;
import entities.Mario;
import gameState.PlayerState;
import projectiles.Projectile;
import tilesAndGraphics.ImageCache;
import java.util.ArrayList;
public class Goomba extends Enemy{
    private boolean facingRight;

    public Goomba(){ // default constructor
        this(0.0,0.0);
    }
    
    public Goomba(double x,double y){ // constructor with initial location (for sprite)
        super();
        facingRight=false;
        setImage(ImageCache.goomba);
        setXpos(x);
        setYpos(y);
        setWidth(ImageCache.goomba.getWidth());
        setHeight(ImageCache.goomba.getHeight());
        setCWidth(getWidth()-4);
        setCHeight(getHeight()-1);
        updateC();
        //setDrawC(true);
    }
    
    // this method updates the collision box relative to its current location
    private void updateC(){
        setCXpos(getXpos()+2);
        setCYpos(getYpos());
    }

    public void update(ArrayList<Block> blocks){
        if(getFalling()) // if its falling apply grav force
            setDy(getDy()+.11);
        if(facingRight) // if facing right set speed in right direction
            setDx(.3);
        else
            setDx(-.3); // else set speed in left direction
        // Temps are used because collisions may affect the final location
        setXTemp(getXpos()+getDx());
        setYTemp(getYpos()+getDy());
        checkBlockCollision(blocks);
        setXpos(getXTemp());
        setYpos(getYTemp());
        updateC();
    }
    
    // turns 180 degrees
    public void turn(){
        setDx(-getDx());
        facingRight=!facingRight;
    }
    
    public void checkBlockCollision(ArrayList<Block> list){
        setFalling(true);
        double tempDx=getDx();
        double tempX=getXTemp();
        for(int i=0;i<list.size();i++){                         //list is indexed by i
            boolean rgt=false;                                  //initialize directions
            boolean lft=false;
            double w=.5*(list.get(i).getCWidth()+getWidth());   //w = average width?
            double h=.5*(list.get(i).getCHeight()+getHeight()); //h = average height?
            double dx=list.get(i).getCCenterX()-getCenterX();   //horiz. distance between centers
            double dy=list.get(i).getCCenterY()-getCenterY();   //vert. distance between centers
            // You dont need to worry in particular about the calculations here
            // they just help in determining what side the collision is on.
            // to implement Tile Physics all of this will be changed anyway
            if(Math.abs(dx)<=w&&Math.abs(dy)<=h){               //check to see if bounding boxes overlap
                double wy=w*dy;                                 //average width * vert. distance?
                double hx=h*dx;                                 //average height * horiz. distance?
                if(wy>hx){                                      
                    if(wy>-hx){
                        // Collision is on the bottom side of this object
                        if(getDy()>0)
                            setDy(0);
                        setYTemp(list.get(i).getYpos()-getHeight());
                        setFalling(false);
                        //System.out.println("TOP");
                    }else{
                        // Collision is from the left side of this object 
                        if(distanceBetweenCenters(list.get(i))>getMaxDistance(list.get(i))-2){
                            setDx(tempDx);
                            setXTemp(tempX);
                        }else{
                            setXTemp(list.get(i).getXpos()+getWidth());
                            turn();
                        }
                        lft=true;
                        //System.out.println("LEFT");
                    }
                }else{
                    if(wy>-hx){
                        // Collision is on the right side of this object
                        setXTemp(list.get(i).getXpos()-getWidth()-1);
                        if(distanceBetweenCenters(list.get(i))>getMaxDistance(list.get(i))){
                            setDx(tempDx);
                            setXTemp(tempX);
                        }else
                            turn();
                        rgt=true;
                        //System.out.println("RIGHT");
                    }else{
                        // Collision is on the top of this object
                        // THIS SHOULD NEVER HAPPEN
                        if(getDy()<0)
                            setDy(0);
                        setYTemp(list.get(i).getYpos()+list.get(i).getHeight()+1);
                    }
                }
                // This is for the case the Goomba is standing on top of a block
                // whose width is the same as its. The above code will say there
                // is a collision on the right and the left and it will stagnate
                if(rgt&&lft){
                    setDx(tempDx);
                    setXTemp(tempX);
                }
            }
        }
    }

    // has yet to be implemented
    public void checkEnemyCollision(ArrayList<Enemy> list){
        for(int i=0;i<list.size();i++){
            double w=.5*(list.get(i).getCWidth()+getWidth());
            double h=.5*(list.get(i).getCHeight()+getHeight());
            double dx=list.get(i).getCCenterX()-getCenterX();
            double dy=list.get(i).getCCenterY()-getCenterY();
            if(Math.abs(dx)<=w&&Math.abs(dy)<=h){
                double wy=w*dy;
                double hx=h*dx;
                if(wy>hx){
                    if(wy>-hx){
                        // Collision is on the bottom side
                    }else{
                        // Collision is from the left side
                        turn();
                        list.get(i).turn();
                    }
                }else{
                    if(wy>-hx){
                        // Collision is on the right side
                        turn();
                        list.get(i).turn();
                    }else{
                        // Collision is on the top side
                    }
                }
            }
        }
    }

    // if the goomba is hit from the top
    public boolean hit(PlayerState state,Mario mario){
        mario.rebound(); // mario should jump a little
        //state.addPoints(100);
        killed(); // this gets killed
        return true;
    }

    // I dont know how or if we incorporate projectile classes
    public boolean hitByProjectile(Projectile projectile){
        killed();
        return true;
    }
    
    // removes this enemy from the current list of enemies
    public void killed(){
        removeEnemyFromList();
    }
}
