/**
 *
 * @author Zackary Misso
 * 
 */
package entities;
import gameState.Level1State;
import tilesAndGraphics.ImageCache;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
public class Block {
    private BufferedImage image;
    private double xpos;
    private double ypos;
    private int width;
    private int height;
    
    public Block(double x,double y,int w,int h){
        xpos=x;
        ypos=y;
        width=w;
        height=h;
        image=ImageCache.block;
    }
    
    public Rectangle getRect(){
        return new Rectangle((int)xpos,(int)ypos,width,height);
    }
    
    public double getCenterX(Level1State state){
        //System.out.println(xpos-state.getXOffset()+width/2);
        return xpos-state.getXOffset()+width/2;
    }
    
    public double getCenterY(Level1State state){
        return ypos-state.getYOffset()+height/2;
    }
    
    public void draw(Graphics2D g,Level1State state){
        g.drawImage(image,(int)(xpos-state.getXOffset()),(int)ypos,null);
        g.setColor(Color.black);
        g.drawRect((int)(xpos-state.getXOffset()), (int)ypos, width, height);
    }
    
    // getter methods
    public BufferedImage getImage(){return image;}
    public double getXpos(){return xpos;}
    public double getYpos(){return ypos;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    
    // setter methods
    public void setImage(BufferedImage param){image=param;}
    public void setXpos(double param){xpos=param;}
    public void setYpos(double param){ypos=param;}
    public void setWidth(int param){width=param;}
    public void setHeight(int param){height=param;}
}
