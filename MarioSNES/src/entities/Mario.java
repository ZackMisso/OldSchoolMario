/**
 *
 * @author Zackary Misso
 * 
 */
package entities;
import gameState.Level1State;
import tilesAndGraphics.ImageCache;
import tilesAndGraphics.TileMap;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
public class Mario extends GameEntity{
    private int health;
    private boolean dead;
    private boolean flinching;
    private long flinchTime;
    private double rectx;
    private double recty;
    private int rectw;
    private int recth;
    private double rectx2;
    private double recty2;
    private int rectw2;
    private int recth2;
    int num;
    
    public Mario(TileMap tm){
        //super(tm);
        setWidth(16);
        setHeight(32);
        setCWidth(16);
        setCHeight(32);
        //setMoveSpeed(.3);
        //setMaxSpeed(1.6);
        //setStopSpeed(.4);
        setFallSpeed(.15);
        setMaxFallSpeed(4.0);
        setJumpStart(-4.8);
        setStopJumpSpeed(.3);
        //setFacingRight(true);
        health=1;
        setImage(ImageCache.mario);
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
        //System.out.println(getDx());
        
        setXTemp(100+state.getXOffset()+getDx());
        //System.out.print(getXTemp()+" ");
        setYTemp(getYpos()+getDy());
        checkCollisionsWithBlocks(list,state);
        //System.out.println(getXTemp());
        //System.out.println(getDx());
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
        //System.out.print(getDx()+" ");
        double tempDx=getDx();
        double tempX=getXTemp();
        for(int i=0;i<list.size();i++){
            boolean rgt=false;
            boolean lft=false;
            double w=.5*(list.get(i).getCWidth()+getWidth());
            double h=.5*(list.get(i).getCHeight()+getHeight());
            double dx=list.get(i).getCCenterX()-getCenterX(state);
            double dy=list.get(i).getCCenterY()-getCenterY(state);
            if(Math.abs(dx)<=w&&Math.abs(dy)<=h){
                double wy=w*dy;
                double hx=h*dx;
                if(wy>hx){
                    if(wy>-hx){
                        // Collision from the top
                        if(getDy()>0)
                            setDy(0);
                        setYTemp(list.get(i).getYpos()-getHeight());
                        setFalling(false);
                        //System.out.println("TOP");
                    }else{
                        // Collision from the left
                        if(getDx()<0){
                            setDx(0);
                            //setXTemp(list.get(i).getXpos()+getWidth()+1);
                            //System.out.println("BROKEN2");
                        }
                        rectx2=list.get(i).getXpos();
                        recty2=list.get(i).getYpos();
                        rectw2=list.get(i).getWidth();
                        recth2=list.get(i).getHeight();
                        //setXTemp(list.get(i).getXpos()+getWidth()+1);
                        if(distanceBetweenCenters(list.get(i))>getMaxDistance(list.get(i))-2){
                            //setXTemp(list.get(i).getXpos()+getWidth()+1);
                            //System.out.print(distanceBetweenCenters(list.get(i))+" ");
                            //System.out.println(getMaxDistance(list.get(i)));
                            setDx(tempDx);
                            setXTemp(tempX);
                        }else{
                            setXTemp(list.get(i).getXpos()+getWidth());
                            System.out.print(distanceBetweenCenters(list.get(i))+" ");
                            System.out.println(getMaxDistance(list.get(i)));
                        }
                        lft=true;
                        //setXTemp(list.get(i).getXpos()+getWidth()+1);
                        //System.out.println("LEFT");
                    }
                }else{
                    if(wy>-hx){
                        // Collision on the right
                        if(getDx()>0){
                            //System.out.println("BROKEN");
                            //setXTemp(list.get(i).getXpos()-getWidth()-1);
                            setDx(0);
                        }
                        //System.out.println("MARIO :: "+getXpos()+" "+getYpos());
                        //System.out.println("BLOCK :: "+list.get(i).getXpos()+" "+list.get(i).getYpos());
                        rectx=list.get(i).getXpos();
                        recty=list.get(i).getYpos();
                        rectw=list.get(i).getWidth();
                        recth=list.get(i).getHeight();
                        setXTemp(list.get(i).getXpos()-getWidth()-1);
                        if(distanceBetweenCenters(list.get(i))>getMaxDistance(list.get(i))){
                            //setXTemp(list.get(i).getXpos()+getWidth()+1);
                            //System.out.print(distanceBetweenCenters(list.get(i))+" ");
                            //System.out.println(getMaxDistance(list.get(i)));
                            setDx(tempDx);
                            setXTemp(tempX);
                        }
                        rgt=true;
                        //System.out.println("RIGHT");
                    }else{
                        // Collision on the bottom
                        if(getDy()<0){
                            setDy(0);
                        }
                        setYTemp(list.get(i).getYpos()+list.get(i).getHeight()+1);
                        //System.out.println("BOT");
                    }
                }
                if(rgt&&lft){
                    setDx(tempDx);
                    setXTemp(tempX);
                    System.out.println("ADFASDFADF");
                }
            }
        }
        //System.out.print(getDx());
    }
    
    public void keyPressed(int k){
        if(k==KeyEvent.VK_D)setRight(true);
        if(k==KeyEvent.VK_A)setLeft(true);
        if(k==KeyEvent.VK_SPACE)setJumping(true);
    }
    
    public void keyReleased(int k){
        if(k==KeyEvent.VK_D)setRight(false);
        if(k==KeyEvent.VK_A)setLeft(false);
        if(k==KeyEvent.VK_SPACE)setJumping(false);
    }
    
    public void draw(Graphics2D g){
        g.drawImage(getImage(),(int)getXpos(),(int)getYpos(),null);
        g.setColor(Color.BLACK);
        g.drawRect((int)rectx,(int)recty,rectw,recth);
        g.setColor(Color.YELLOW);
        g.drawRect((int)rectx2,(int)recty2,rectw2,recth2);
        //g.drawRect((int)getXpos(),(int)getYpos(),getWidth(),getHeight());
    }
    
    // getter methods
    public int getHealth(){return health;}
    public boolean getDead(){return dead;}
    public boolean getFlinching(){return flinching;}
    public long getFlinchTime(){return flinchTime;}
    
    // setter methods
    public void setHealth(int param){health=param;}
    public void setDead(boolean param){dead=param;}
    public void setFlinching(boolean param){flinching=param;}
    public void setFlinchTime(long param){flinchTime=param;}
}