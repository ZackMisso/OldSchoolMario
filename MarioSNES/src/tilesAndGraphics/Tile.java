/**
 *
 * @author Zackary Misso
 * 
 */
package tilesAndGraphics;
import java.awt.image.BufferedImage;
public class Tile{
    public static final int NORMAL=0;
    public static final int BLOCKED=1;
    private BufferedImage image;
    private int xpos;
    private int ypos;
    private int size;
    private int type;
    
    public Tile(BufferedImage param,int param2){
        image=param;
        type=param2;
        xpos=0;
        ypos=0;
        size=16;
    }
    
    public Tile(BufferedImage img,int tp,int siz,int x,int y){
        image=img;
        type=tp;
        size=siz;
        xpos=x*size;
        ypos=y*size;
    }
    
    // getter methods
    public BufferedImage getImage(){return image;}
    public int getXpos(){return xpos;}
    public int getYpos(){return ypos;}
    public int getType(){return type;}
    public int getSize(){return size;}
    
    // setter methods
    public void setImage(BufferedImage param){image=param;}
    public void setXpos(int param){xpos=param;}
    public void setYpos(int param){ypos=param;}
    public void setType(int param){type=param;}
    public void setSize(int param){size=param;}
}
