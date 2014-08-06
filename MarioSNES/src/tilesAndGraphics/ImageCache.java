/**
 *
 * @author Zackary Misso
 * 
 */
package tilesAndGraphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ImageCache {
    public static BufferedImage mario;
    public static BufferedImage block;
    public static BufferedImage floor;
    public static BufferedImage breakableBlock;
    public static BufferedImage sky;
    //public static final BufferedImage goomba;
    //public static final BufferedImage koopa;
    //public static final BufferedImage pipe;
    //public static final BufferedImage flag;
    
    public static void initImages(){
        try{
            mario=ImageIO.read(new File("resources/images/marioBig.png"));
            block=ImageIO.read(new File("resources/images/block.png"));
            floor=ImageIO.read(new File("resources/images/floor.png"));
            breakableBlock=ImageIO.read(new File("resources/images/breakableBlock.png"));
            sky=ImageIO.read(new File("resources/images/sky.png"));
        }catch(IOException e){
            System.out.println("Major Error :: Image files could not be read");
        }
    }
}