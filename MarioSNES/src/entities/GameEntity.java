/**
 *
 * @author Zackary Misso
 * 
 */
package entities;
import core.GamePanel;
import gameState.Level1State;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Graphics2D;
public abstract class GameEntity {
    private BufferedImage image;
    private double xpos;
    private double ypos;
    private double dx;
    private double dy;
    private int width;
    private int height;
    private double cxpos;
    private double cypos;
    private int cwidth;
    private int cheight;
    private double xtemp;
    private double ytemp;
    private boolean left;
    private boolean right;
    private boolean jumping;
    private boolean falling;
    //private boolean facingRight;
    //private double moveSpeed;
    //private double maxSpeed;
    //private double stopSpeed;
    private double fallSpeed;
    private double maxFallSpeed;
    private double jumpStart;
    private double stopJumpSpeed;
    private boolean onScreen; // used to save some computation
    
    public boolean collidesWith(GameEntity other){
        Rectangle one=getRigidBody();
        Rectangle two=other.getRigidBody();
        return one.intersects(two);
    }
    
    public double getCenterX(){
        //System.out.println("asdfasdfasd "+xpos);
        return xpos+width/2;
    }
    
    public double getCenterY(){
        return ypos+height/2;
    }
    
    public double getCCenterX(){
        return cxpos+cwidth/2;
    }
    
    public double getCCenterY(){
        return cypos+cheight/2;
    }
    
    public void setPosition(double x,double y){
        xpos=x;
        ypos=y;
    }
    
    public void setVector(double x,double y){
        dx=x;
        dy=y;
    }
    
    public double distanceBetweenCenters(GameEntity other){
        double x=getCenterX()-other.getCenterX();
        double y=getCenterY()-other.getCenterY();
        x*=x;
        y*=y;
        return Math.sqrt(x+y);
    }
    
    public double getMaxDistance(GameEntity other){
        double x=width/2+other.getWidth()/2;
        double y=height/2+other.getHeight()/2;
        x*=x;
        y*=y;
        return Math.sqrt(x+y);
    }

    public boolean isOnScreen(){
        if(xpos+width<0||xpos>GamePanel.WIDTH){
            onScreen=false;
            return false;
        }
        if(ypos+height<0||ypos>GamePanel.HEIGHT){
            onScreen=false;
            return false;
        }
        onScreen=true;
        return true;
    }
    
    public Rectangle getRigidBody(){
        return new Rectangle((int)xpos-cwidth,(int)ypos-cheight,cwidth,cheight);
    }

    public void draw(Graphics2D g){
        g.drawImage(image,(int)xpos,(int)ypos,null);
    }
    
    // getter methods
    public BufferedImage getImage(){return image;}
    public double getXpos(){return xpos;}
    public double getYpos(){return ypos;}
    public double getDx(){return dx;}
    public double getDy(){return dy;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public double getCXpos(){return cxpos;}
    public double getCYpos(){return cypos;}
    public int getCWidth(){return cwidth;}
    public int getCHeight(){return cheight;}
    public double getXTemp(){return xtemp;}
    public double getYTemp(){return ytemp;}
    public boolean getLeft(){return left;}
    public boolean getRight(){return right;}
    public boolean getJumping(){return jumping;}
    public boolean getFalling(){return falling;}
    //public boolean getFacingRight(){return facingRight;}
    //public double getMoveSpeed(){return moveSpeed;}
    //public double getMaxSpeed(){return maxSpeed;}
    //public double getStopSpeed(){return stopSpeed;}
    public double getFallSpeed(){return fallSpeed;}
    public double getMaxFallSpeed(){return maxFallSpeed;}
    public double getJumpStart(){return jumpStart;}
    public double getStopJumpSpeed(){return stopJumpSpeed;}
    public boolean getOnScreen(){return onScreen;}
    
    // setter methods
    public void setImage(BufferedImage param){image=param;}
    public void setXpos(double param){xpos=param;}
    public void setYpos(double param){ypos=param;}
    public void setDx(double param){dx=param;}
    public void setDy(double param){dy=param;}
    public void setWidth(int param){width=param;}
    public void setHeight(int param){height=param;}
    public void setCXpos(double param){cxpos=param;}
    public void setCYpos(double param){cypos=param;}
    public void setCWidth(int param){cwidth=param;}
    public void setCHeight(int param){cheight=param;}
    public void setXTemp(double param){xtemp=param;}
    public void setYTemp(double param){ytemp=param;}
    public void setLeft(boolean param){left=param;}
    public void setRight(boolean param){right=param;}
    public void setJumping(boolean param){jumping=param;}
    public void setFalling(boolean param){falling=param;}
    //public void setFacingRight(boolean param){facingRight=param;}
    //public void setMoveSpeed(double param){moveSpeed=param;}
    //public void setMaxSpeed(double param){maxSpeed=param;}
    //public void setStopSpeed(double param){stopSpeed=param;}
    public void setFallSpeed(double param){fallSpeed=param;}
    public void setMaxFallSpeed(double param){maxFallSpeed=param;}
    public void setJumpStart(double param){jumpStart=param;}
    public void setStopJumpSpeed(double param){stopJumpSpeed=param;}
    public void setOnScreen(boolean param){onScreen=param;}
}