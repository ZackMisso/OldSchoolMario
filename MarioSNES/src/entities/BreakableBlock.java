/**
 *
 * @author Zackary Misso
 * 
 */
package entities;
import tilesAndGraphics.ImageCache;
public class BreakableBlock extends Block{
    public BreakableBlock(){
        this(0.0,0.0,ImageCache.breakableBlock.getWidth(),ImageCache.breakableBlock.getHeight());
    }
    
    public BreakableBlock(double x,double y,int w,int h){
        super(x,y,w,h);
        setImage(ImageCache.breakableBlock);
    }
}