/**
 *
 * @author Zackary Misso
 * 
 */
package enemies;
import entities.Block;
import entities.Mario;
import gameState.PlayerState;
import java.util.ArrayList;
public class Goomba extends Enemy{
    private boolean facingRight;

    public Goomba(){
        // implement
    }

    public void update(ArrayList<Block> blocks){
        if(getFalling())
            setDy(getDy()+.11);
        if(facingRight)
            setDx(1);
        else
            setDx(-1);
        setXTemp(getXpos()+getDx());
        setYTemp(getYpos()+getDy());
        checkBlockCollision(blocks);
        setXpos(getXTemp());
        setYpos(getYTemp());
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
                        if(getDy()>0)
                            setDy(0);
                        setYTemp(list.get(i).getYpos()-getHeight());
                        setFalling(false);
                        //System.out.println("TOP");
                    }else{
                        // Collision is from the left
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
                        // Collision is on the right
                        setXTemp(list.get(i).getXpos()-getWidth()-1);
                        if(distanceBetweenCenters(list.get(i))>getMaxDistance(list.get(i))){
                            setDx(tempDx);
                            setXTemp(tempX);
                        }else
                            turn();
                        rgt=true;
                        //System.out.println("RIGHT");
                    }else{
                        // Collision is on the bottom
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

    public boolean hit(PlayerState state,Mario mario){
        mario.rebound();
        state.addPoints(100);
        killed();
        return true;
    }
    
    public void killed(){
        // IDK if I am going to use this method yet
    }
}
