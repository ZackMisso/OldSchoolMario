/**
 *
 * @author Zack and Mark
 * 
 */
package animations;
import java.awt.Image;
import java.util.ArrayList;
public class Animation {
    private ArrayList<Image> images;
    private int index;
    private int quantum;
    private int cnt;
    
    public Animation(){
        this(null,0);
    }
    
    public Animation(ArrayList<Image> param,int time){
        images=param;
        quantum=time;
        cnt=quantum;
        index=0;
    }
}