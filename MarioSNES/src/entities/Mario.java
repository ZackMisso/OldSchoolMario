/**
 *
 * @author Zack Misso
 * 
 */
package entities;
import gameState.Level1State;
import tilesAndGraphics.ImageCache;
import tilesAndGraphics.TileMap;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
public class Mario extends GameEntity{
    // TODO :: REWATCH PLAYER TUTORIAL TO IMPLEMENT THE ANIMATIONS
    private int health;
    private boolean dead;
    private boolean flinching;
    private long flinchTime;
    private BufferedImage image;
    private boolean collidingRight;
    private boolean collidingLeft;
    private boolean collidingUp;
    private boolean collidingDown;
    
    public Mario(TileMap tm){
        super(tm);
        setWidth(16);
        setHeight(32);
        setCWidth(16);
        setCHeight(32);
        setMoveSpeed(.3);
        setMaxSpeed(1.6);
        setStopSpeed(.4);
        setFallSpeed(.15);
        setMaxFallSpeed(4.0);
        setJumpStart(-4.8);
        setStopJumpSpeed(.3);
        setFacingRight(true);
        health=1;
        image=ImageCache.mario;
        setFalling(true);
        //setDy(1);
    }
    
    public void update(ArrayList<Block> list,Level1State state){
        // update position
        //getNextPosition();
        //checkTileMapCollision();
        //setPosition(getXTemp(),getYTemp());
        if(getFalling())
            setDy(getDy()+.11);
        if(getRight()){
            setDx(1);
        }
        else if(getLeft()){
            setDx(-1);
        }
        //else if(getUp()){
        //    setDy(-1);
        //}
        //else if(getDown()){
        //    setDy(1);
        //}
        else{
            setDx(0);
            //setDy(0);
        }
        //setXTemp(getXpos()+getDx());
        setXTemp(100+state.getXOffset()+getDx());
        setYTemp(getYpos()+getDy());
        checkCollisionsWithBlocks(list,state);
        if(getJumping()&&!getFalling()){
            setDy(getJumpStart());
            setJumping(false);
            setFalling(true);
            setYTemp(getYpos()+getDy());
        }
        //setXTemp(getXpos()+getDx());
        //setYTemp(getYpos()+getDy());
    }
    
    public void finalizeMovement(Level1State state){
        //double changeX=getXTemp()-getXpos();
        //setXpos(getXTemp());
        state.setXOffset(getXTemp()-100);
        setYpos(getYTemp());
        //System.out.println(state.getXOffset());
        if(getYpos()>330)
            System.exit(0);
    }
    
    public void checkCollisionsWithBlocks(ArrayList<Block> list,Level1State state){
        setFalling(true);
        for(int i=0;i<list.size();i++){
            double w=.5*(list.get(i).getWidth()+getWidth());
            double h=.5*(list.get(i).getHeight()+getHeight());
            double dx=list.get(i).getCenterX(state)-getCenterX(state);
            //System.out.println(getCenterX(state));
            double dy=list.get(i).getCenterY(state)-getCenterY(state);
            if(Math.abs(dx)<=w&&Math.abs(dy)<=h){
                //System.out.println("THere was a Collision");
                double wy=w*dy;
                double hx=h*dx;
                if(wy>hx){
                    if(wy>-hx){
                        // Collision from the top
                        if(getDy()>0)
                            setDy(0);
                        setYTemp(list.get(i).getYpos()-getHeight()-1);
                        setFalling(false);
                        //System.out.println("TOP");
                    }else{
                        // Collision from the left
                        setDx(0);
                        setXTemp(list.get(i).getXpos()+getWidth()+1);
                        //System.out.println("LEFT");
                    }
                }else{
                    if(wy>-hx){
                        // Collision on the right
                        if(getDx()>0)
                            setDx(0);
                        setXTemp(list.get(i).getXpos()-getWidth()-1);
                        //System.out.println("RIGHT");
                    }else{
                        // Collision on the bottom
                        if(getDy()<0)
                            setDy(0);
                        setYTemp(list.get(i).getYpos()+list.get(i).getHeight()+1);
                        //System.out.println("BOTTOM");
                    }
                }
            }
        }
    }
    
    public void getNextPosition(){
        // movement
        if(getLeft()){
            setDx(getDx()-getMoveSpeed());
            if(getDx()<-getMaxSpeed())
                setDx(-getMaxSpeed());
        }
        else if(getRight()){
            setDx(getDx()+getMoveSpeed());
            if(getDx()>getMaxSpeed())
                setDx(getMoveSpeed());
        }
        else{
            if(getDx()>0){
                setDx(getDx()-getStopSpeed());
                if(getDx()<0)
                    setDx(0);
            }
            else if(getDx()<0){
                setDx(getDx()+getStopSpeed());
                if(getDx()>0)
                    setDx(0);
            }
        }
        // jumping
        if(getJumping()&&!getFalling()){
            setDy(getJumpStart());
            setFalling(true);
        }
        // falling
        if(getFalling()){
            setDy(getDy()+getFallSpeed());
            if(getDy()>0)setJumping(false);
            if(getDy()<0&&!getJumping())setDy(getDy()+getStopJumpSpeed());
            if(getDy()>getMaxFallSpeed())setDy(getMaxFallSpeed());
        }
    }
    
    public void keyPressed(int k){
        if(k==KeyEvent.VK_D)setRight(true);
        if(k==KeyEvent.VK_A)setLeft(true);
        //if(k==KeyEvent.VK_W)setUp(true);
        //if(k==KeyEvent.VK_S)setDown(true);
        if(k==KeyEvent.VK_SPACE)setJumping(true);
    }
    
    public void keyReleased(int k){
        if(k==KeyEvent.VK_D)setRight(false);
        if(k==KeyEvent.VK_A)setLeft(false);
        //if(k==KeyEvent.VK_W)setUp(false);
        //if(k==KeyEvent.VK_S)setDown(false);
        if(k==KeyEvent.VK_SPACE)setJumping(false);
    }
    
    public void draw(Graphics2D g){
        //setMapPosition();
        //g.drawImage(image,(int)(getXpos()+getXMap()-getWidth()/2),(int)(getYpos()+getYMap()-getHeight()/2),null);
        g.drawImage(image,(int)getXpos(),(int)getYpos(),null);
        g.setColor(Color.BLACK);
        //g.drawRect((int)getXpos(),(int)getYpos(),getWidth(),getHeight());
    }
    
    // getter methods
    public int getHealth(){return health;}
    public boolean getDead(){return dead;}
    public boolean getFlinching(){return flinching;}
    public long getFlinchTime(){return flinchTime;}
    public BufferedImage getMainImage(){return image;}
    
    // setter methods
    public void setHealth(int param){health=param;}
    public void setDead(boolean param){dead=param;}
    public void setFlinching(boolean param){flinching=param;}
    public void setFlinchTime(long param){flinchTime=param;}
    public void setMainImage(BufferedImage param){image=param;}
}