package entities;

import tilesAndGraphics.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public abstract class GameEntity {
    private TileMap tileMap;
    private int tileSize;
    private double xmap;
    private double ymap;
    private BufferedImage image;
    private double xpos;
    private double ypos;
    private double dx;
    private double dy;
    private int width;
    private int height;
    private int cwidth;
    private int cheight;
    private int currentRow;
    private int currentCol;
    private double xdest;
    private double ydest;
    private double xtemp;
    private double ytemp;
    private boolean topLeft;
    private boolean topRight;
    private boolean bottomLeft;
    private boolean bottomRight;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean jumping;
    private boolean falling;
    private double moveSpeed;
    private double maxSpeed;
    private double stopSpeed;
    private double fallSpeed;
    private double maxFallSpeed;
    private double jumpStart;
    private double stopJumpSpeed;
    
    public GameEntity(){
        // implement if needed
    }
    
    public boolean collidesWith(GameEntity other){
        Rectangle one=getRigidBody();
        Rectangle two=other.getRigidBody();
        return one.intersects(two);
    }
    
    public void calculateCorners(double x,double y){
        //int leftTile=()
    }
    
    public Rectangle getRigidBody(){
        return new Rectangle((int)xpos,(int)ypos,width,height);
    }
    
    // CONTINUE AT 8 MINUTES IN
    
    // getter methods
    public TileMap getTileMap(){return tileMap;}
    public int getTileSize(){return tileSize;}
    public double getXMap(){return xmap;}
    public double getYMap(){return ymap;}
    public BufferedImage getImage(){return image;}
    public double getXpos(){return xpos;}
    public double getYpos(){return ypos;}
    public double getDx(){return dx;}
    public double getDy(){return dy;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getCWidth(){return cwidth;}
    public int getCHeight(){return cheight;}
    public int getCurrentRow(){return currentRow;}
    public int getCurrentCol(){return currentCol;}
    public double getXDest(){return xdest;}
    public double getYDest(){return ydest;}
    public double getXTemp(){return xtemp;}
    public double getYTemp(){return ytemp;}
    public boolean getTopLeft(){return topLeft;}
    public boolean getTopRight(){return topRight;}
    public boolean getBottomLeft(){return bottomLeft;}
    public boolean getBottomRight(){return bottomRight;}
    public boolean getLeft(){return left;}
    public boolean getRight(){return right;}
    public boolean getUp(){return up;}
    public boolean getDown(){return down;}
    public boolean getJumping(){return jumping;}
    public boolean getFalling(){return falling;}
    public double getMoveSpeed(){return moveSpeed;}
    public double getMaxSpeed(){return maxSpeed;}
    public double getStopSpeed(){return stopSpeed;}
    public double getFallSpeed(){return fallSpeed;}
    public double getMaxFallSpeed(){return maxFallSpeed;}
    public double getJumpStart(){return jumpStart;}
    public double getStopJumpSpeed(){return stopJumpSpeed;}
    
    // setter methods
    public void setTileMap(TileMap param){tileMap=param;}
    public void setTileSize(int param){tileSize=param;}
    public void setXMap(double param){xmap=param;}
    public void setYMap(double param){ymap=param;}
    public void setImage(BufferedImage param){image=param;}
    public void setXpos(double param){xpos=param;}
    public void setYpos(double param){ypos=param;}
    public void setDx(double param){dx=param;}
    public void setDy(double param){dy=param;}
    public void setWidth(int param){width=param;}
    public void setHeight(int param){height=param;}
    public void setCWidth(int param){cwidth=param;}
    public void setCHeight(int param){cheight=param;}
    public void setCurrentRow(int param){currentRow=param;}
    public void setCurrentCol(int param){currentCol=param;}
    public void setXDest(double param){xdest=param;}
    public void setYDest(double param){ydest=param;}
    public void setXTemp(double param){xtemp=param;}
    public void setYTemp(double param){ytemp=param;}
    public void setTopLeft(boolean param){topLeft=param;}
    public void setTopRight(boolean param){topRight=param;}
    public void setBottomRight(boolean param){bottomRight=param;}
    public void setBottomLeft(boolean param){bottomLeft=param;}
    public void setLeft(boolean param){left=param;}
    public void setRight(boolean param){right=param;}
    public void setUp(boolean param){up=param;}
    public void setDown(boolean param){down=param;}
    public void setJumping(boolean param){jumping=param;}
    public void setFalling(boolean param){falling=param;}
    public void setMoveSpeed(double param){moveSpeed=param;}
    public void setMaxSpeed(double param){maxSpeed=param;}
    public void setStopSpeed(double param){stopSpeed=param;}
    public void setFallSpeed(double param){fallSpeed=param;}
    public void setMaxFallSpeed(double param){maxFallSpeed=param;}
    public void setJumpStart(double param){jumpStart=param;}
    public void setStopJumpSpeed(double param){stopJumpSpeed=param;}
}