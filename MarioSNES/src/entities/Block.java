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
public class Block extends GameEntity{
    public Block(){
        // implement
        setWidth(16);
        setHeight(16);
        setImage(ImageCache.block);
    }

    public Block(double x,double y,int w,int h){
        setXpos(x);
        setYpos(y);
        setStartX(x);
        setWidth(w);
        setHeight(h);
        setImage(ImageCache.block);
        updateC();
    }
    
    public void updateC(){ // TODO :: Test if this is even being called
        setCXpos(getXpos()-1);
        setCYpos(getYpos()-1);
        setCWidth(getWidth()-1);
        setCHeight(getHeight()-1);
    }
    
    // Depreciated :: Zack :: Moved to GameEntity class
    public void draw(Graphics2D g,Level1State state){
        g.drawImage(getImage(),(int)(getStartX()-state.getXOffset()),(int)getYpos(),null);
        //g.setColor(Color.black);
        //g.drawRect((int)(xpos-state.getXOffset()), (int)ypos, width, height);
    }
}