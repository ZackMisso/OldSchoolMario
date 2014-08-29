/**
 *
 * @author Zackary Misso
 *
 */
package entities;
public class Pipe extends Block{ // initially the pipe will do nothing
    public Pipe(double x,double y,int w,int h){
    	super(x,y,w,h);
    	deriveImage();
    }

    // sets the image based on the size
    private void deriveImage(){
    	// implement
    }
}
