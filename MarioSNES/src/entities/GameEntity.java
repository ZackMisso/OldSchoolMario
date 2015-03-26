/**
 *
 * @author Zackary Misso
 * 
 */
package entities;
import core.GamePanel;
import core.GlobalController;
import datastructures.DoubleCoordinate;
import gameState.Level1State;
import tilesAndGraphics.TileMap;
import tilesAndGraphics.TileRect;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Graphics2D;
public abstract class GameEntity {
    private BufferedImage image;
    private TileMap map; // the tile map
    private double xpos;
    private double ypos;
    private double startX;
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
    //private boolean drawC; // used for debugging
    
    private enum Corners{NONE,TOPLEFT,TOPRIGHT,BOTTOMLEFT,BOTTOMRIGHT};
    
    public boolean collidesWith(GameEntity other){
        Rectangle one=getRigidBody();
        Rectangle two=other.getRigidBody();
        return one.intersects(two);
    }
    
    public double getCenterX(){
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

    public double getRelativeX(GameEntity other){
        if(other == null){
            System.out.println("THIS SHOULD NOT HAPPEN: GameEntity :: 84");
            return Double.MAX_VALUE;
        }
        return getCenterX()-other.getCenterX();
    }

    public double getRelativeY(GameEntity other){
        return getCenterY()-other.getCenterY();
    }
    
    public double distanceBetweenCenters(GameEntity other){
        double x=getCenterX()-other.getCenterX();
        double y=getCenterY()-other.getCenterY();
        x*=x;
        y*=y;
        return Math.sqrt(x+y);
    }

    public double efficientDistanceBetween(GameEntity other){
        double x=getCenterX()-other.getCenterX();
        double y=getCenterY()-other.getCenterY();
        x*=x;
        y*=y;
        return x+y;
    }
    
    public double getMaxDistance(GameEntity other){
        double x=width/2+other.getWidth()/2;
        double y=height/2+other.getHeight()/2;
        x*=x;
        y*=y;
        return Math.sqrt(x+y);
    }
    
    public double getMaxDistanceByY(GameEntity other){
        double y=height/2+other.getHeight()/2;
        return y;
    }
    
    public double getMaxDistanceByX(GameEntity other){
        return width/2+other.getWidth()/2;
    }
    
    public double lengthDistance(GameEntity other){
        double y=getCenterY()-other.getCCenterY();
        return Math.abs(y);
    }
    
    public double xLengthDistance(GameEntity other){
        double x=getCenterX()-other.getCCenterX();
        return Math.abs(x);
    }

    // returns if the entity is in the view (needs to be drawn)
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

    ///////////////// TILE PHYSICS METHODS /////////////////////

    /*// gets the tiles around the entity
    //public TileRect getSurroundings(){
    //    // instantiate the needed variables
    //    boolean exactX = false;
        boolean exactY = false;
        int heightInTiles; // to be removed
        int widthInTiles; // to be removed
        int rectWidth;
        int rectHeight;
        int rectXpos;
        int rectYpos;
        // debug
        if(map==null){
            System.out.println("There is no TileMap :: GameEntity");
            if(this instanceof Mario){
                System.out.println("This is Mario :: GameEntity");
            }
            System.exit(0);
        }
        int tileSize=map.getTileSize();
        // check if the entity is exactly aligned with the tiles
        if( ((int)xpos) % tileSize==0)
            exactX=true;
        if( ((int)ypos) % tileSize==0)
            exactY=true;
        // calculate the height and width of the entity in tiles
        // TODO :: make this an instance variable to avoid calculations
        heightInTiles=height/tileSize;
        widthInTiles=width/tileSize;
        // add bumpers to the edges
        rectHeight=heightInTiles+2;
        rectWidth=widthInTiles+2;
        // add additional bumpers if not exact
        if(!exactX)
            rectWidth+=1;
        if(!exactY)
            rectHeight+=1;
        // calculate top corner; Takes advantage of truncation
        rectXpos=((int)xpos) / tileSize;
        rectYpos=((int)ypos) / tileSize;
        // incorporate bumpers
        rectXpos--;
        rectYpos--;
        // create rect object
        return new TileRect(map,rectXpos,rectYpos,rectWidth,rectHeight);
    }*/
    
    // demo for class
    /*public void lolsCheckMovementOnSurroundings(){
        // debugging
        //System.out.print("Before :: "+ytemp+" After :: ");
        // get the Surrounding blocks
        TileRect surroundings=getSurroundings();
        // new coordinate location on map for first vertex
        int newX=(int)(xpos+dx)/map.getTileSize();
        int newY=(int)(ypos+dy)/map.getTileSize();
        // previous coordinate location on map
        int curX=(int)xpos/map.getTileSize();
        int curY=(int)ypos/map.getTileSize();
        // if the x coordinate changes
        if(curX!=newX){
            if(dx>0){
                // checks for a block on the right
                System.out.println("There is a block to the right");
                if(surroundings.isBlock(newX,curY))
                    xtemp=newX*map.getTileSize();
                dx=0;
            }else{
                // checks for a block on the left
                System.out.println("There is a block to the left");
                if(surroundings.isBlock(newX,curY))
                    xtemp=(newX+1)*map.getTileSize();
                dx=0;
            }
        }
        // if the y coordinate changes
        if(curY!=newY){
            if(dy>0){
                // checks for a block below
                System.out.println("There is a block below");
                if(surroundings.isBlock(curX,newY)){
                    System.out.println("THIS IS RAN :: GameEntity");
                    ytemp=(curY-2)*map.getTileSize();
                    falling=false;            
                }
                dy=0;
            }else{
                // checks for a block above
                System.out.println("There is a block above");
                if(surroundings.isBlock(curX,newY))
                    xtemp=(newY+1)*map.getTileSize();
                dy=0;
            }
        }
        // debugging
        //System.out.println(ytemp);
    }*/
    
    /*private void updateCorners(DoubleCoordinate change,DoubleCoordinate one,DoubleCoordinate two){
        one.addTo(change);
        two.addTo(change);
    }*/
    
    /*private DoubleCoordinate handleCollision(TileRect surroundings,DoubleCoordinate curC,DoubleCoordinate newC,Corners corner){
        int newX=(int)(curC.getXpos())/map.getTileSize();
        int newY=(int)(curC.getYpos())/map.getTileSize();
        int curX=(int)curC.getXpos()/map.getTileSize();
        int curY=(int)curC.getYpos()/map.getTileSize();
        double diffX=0.0;
        double diffY=0.0;
        if(surroundings.isBlock(newX,curY)){
            if(corner==Corners.BOTTOMLEFT){
                // moves the block right
                diffX=xtemp;
                xtemp=curX*map.getTileSize();
                dx=0;
            }
            else if(corner==Corners.BOTTOMRIGHT){
                // moves the block left
                diffX=xtemp;
                xtemp=curX*map.getTileSize();
                dx=0;
            }
            else if(corner==Corners.TOPLEFT){
                // moves the block right
                diffX=xtemp;
                xtemp=curX*map.getTileSize();
                dx=0;
                
            }
            else if(corner==Corners.TOPRIGHT){
                // moves the block left
                diffX=xtemp;
                xtemp=curX*map.getTileSize();
                dx=0;
            }
        }
        if(surroundings.isBlock(curX,newY)){
            if(corner==Corners.BOTTOMLEFT){
                // moves the block up
                diffY=ytemp;
                ytemp=curY*map.getTileSize();
                dy=0;
            }
            else if(corner==Corners.BOTTOMRIGHT){
                // moves the block up
                diffY=ytemp;
                ytemp=curY*map.getTileSize();
                dx=0;
            }
            else if(corner==Corners.TOPLEFT){
                // moves the block down
                diffY=ytemp;
                ytemp=curY*map.getTileSize();
                dy=0;
                
            }
            else if(corner==Corners.TOPRIGHT){
                // moves the block down
                diffY=ytemp;
                ytemp=curY*map.getTileSize();
                dy=0;
            }
            diffX-=xtemp;
            diffY-=ytemp;
            // update new coordinate
            return new DoubleCoordinate(diffX,diffY);
        }
        return null;
    }*/
    
    /*private DoubleCoordinate findCorner(Corners corner){
        DoubleCoordinate coordinate=new DoubleCoordinate();
        if(corner==Corners.BOTTOMLEFT){
            coordinate.setXpos(xpos);
            coordinate.setYpos(ypos+height);
            return coordinate;
        }else if(corner==Corners.BOTTOMRIGHT){
            coordinate.setXpos(xpos+width);
            coordinate.setYpos(ypos+height);
            return coordinate;
        }else if(corner==Corners.TOPLEFT){
            coordinate.setXpos(xpos);
            coordinate.setYpos(ypos);
            return coordinate;
        }else if(corner==Corners.TOPRIGHT){
            coordinate.setXpos(xpos+width);
            coordinate.setYpos(ypos);
            return coordinate;
        }else{
            return new DoubleCoordinate(-100000,-100000);
        }
    }*/


    ////////////////////////////////////////////////////////////

    public void draw(Graphics2D g){
        g.drawImage(image,(int)xpos,(int)ypos,null);
        if(GlobalController.debug){
            g.setColor(Color.MAGENTA);
            g.drawRect((int)cxpos,(int)cypos,cwidth,cheight);
        }
    }

    // currently being debugged (used for tilePhysics)
    public void draw(Graphics2D g,Level1State state){
        double x=xpos-state.getXOffset();
        double y=ypos-state.getYOffset();
        g.drawImage(image,(int)x,(int)y,null);
        if(GlobalController.debug){
            g.setColor(Color.MAGENTA);
            g.drawRect((int)xpos,(int)ypos,width,height);
        }
    }
    
    // getter methods
    public BufferedImage getImage(){return image;}
    public TileMap getTileMap(){return map;}
    public double getXpos(){return xpos;}
    public double getYpos(){return ypos;}
    public double getStartX(){return startX;} // temporary variable
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
    public void setTileMap(TileMap param){map=param;}
    public void setXpos(double param){xpos=param;}
    public void setYpos(double param){ypos=param;}
    public void setStartX(double param){startX=param;} // temporary variable
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
    //public void setDrawC(boolean param){drawC=param;}
}