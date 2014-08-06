/**
 *
 * @author Zackary Misso
 * 
 */
package entities;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
public class Animation {
    private ArrayList<BufferedImage> frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;
    
    public Animation(){
        playedOnce=false;
    }
    
    public void setFrames(ArrayList<BufferedImage> param){
        frames=param;
        currentFrame=0;
        startTime=System.nanoTime();
        playedOnce=false;
    }
    
    public void update(){
        if(delay==-1) return;
        long elapsed=(System.nanoTime()-startTime)/1000000;
        if(elapsed>delay){
            currentFrame++;
            startTime=System.nanoTime();
        }
        if(currentFrame==frames.size()){
            currentFrame=0;
            playedOnce=true;
        }
    }
    
    public BufferedImage getCurrentImage(){
        return frames.get(currentFrame);
    }
    
    // getter methods
    public ArrayList<BufferedImage> getFrames(){return frames;}
    public int getCurrentFrame(){return currentFrame;}
    public long getStartTime(){return startTime;}
    public long getDelay(){return delay;}
    public boolean getPlayedOnce(){return playedOnce;}
    
    // setter methods
    public void setCurrentFrame(int param){currentFrame=param;}
    public void setStartTime(long param){startTime=param;}
    public void setDelay(long param){delay=param;}
    public void setPlayedOnce(boolean param){playedOnce=param;}
}