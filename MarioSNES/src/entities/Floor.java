/**
 *
 * @author Zackary Misso
 * 
 */
package entities;
import tilesAndGraphics.ImageCache;
public class Floor extends Block{
    public Floor(double x,double y,int w,int h){
        super(x,y,w,h);
        setImage(ImageCache.floor);
    }
}
