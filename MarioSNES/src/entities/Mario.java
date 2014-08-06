/**
 *
 * @author Zack Misso
 * 
 */
package entities;
import tilesAndGraphics.TileMap;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import tilesAndGraphics.ImageCache;
public class Mario extends GameEntity{
    // TODO :: REWATCH PLAYER TUTORIAL TO IMPLEMENT THE ANIMATIONS
    private int health;
    private boolean dead;
    private boolean flinching;
    private long flinchTime;
    private BufferedImage image;
    
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
        // load the image
        image=ImageCache.mario;
    }
    
    public void update(){
        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(getXTemp(),getYTemp());
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
    
    public void draw(Graphics2D g){
        setMapPosition();
        g.drawImage(image,(int)(getXpos()+getXMap()-getWidth()/2),(int)(getYpos()+getYMap()-getHeight()/2),null);
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