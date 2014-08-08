/**
 *
 * @author Zackary Misso
 * 
 */
package entities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import tilesAndGraphics.ImageCache;
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
    
    public void draw(Graphics2D g){
        g.drawImage(image,(int)xpos,(int)ypos,null);
        g.setColor(Color.black);
        g.drawRect((int)xpos, (int)ypos, width, height);
    }
    
    public double getCenterX(){
        return xpos+width/2;
    }
    
    public double getCenterY(){
        return ypos+height/2;
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
