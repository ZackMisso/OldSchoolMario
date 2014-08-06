/**
 *
 * @author Zackary Misso
 * 
 */
package tilesAndGraphics;
import core.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.File;

public class Background {
    private BufferedImage image;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double moveScale;
    
    public Background(String s,double ms){
        try{
            image=ImageIO.read(new File(""));
        }catch(Exception e){
            
        }
    }
    
    public void setPosition(double x,double y){
        this.x=(x*moveScale)%GamePanel.WIDTH;
        this.y=(y*moveScale)%GamePanel.HEIGHT;
    }
    
    public void setVector(double dx,double dy){
        this.dx=dx;
        this.dy=dy;
    }
    
    public void update(){
        x+=dx;
        y+=dy;
    }
    
    public void draw(Graphics2D g){
        g.drawImage(image,(int)x,(int)y,null);
        if(x<0){
            g.drawImage(image,(int)x+GamePanel.WIDTH,(int)y,null);
        }
        if(x>0){
            g.drawImage(image,(int)x-GamePanel.WIDTH,(int)y,null);
        }
    }
}