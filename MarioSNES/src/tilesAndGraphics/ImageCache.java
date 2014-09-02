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
    public static BufferedImage goomba;
    public static BufferedImage koopa;
    //public static final BufferedImage flyingKoopa; // to be implemented
    //public static final BufferedImage koopaShell; // to be implemented
    //public static final BufferedImage pipe1v1;
    public static BufferedImage pipe2v2;
    public static BufferedImage pipe2v3;
    //public static final BufferedImage pipe1v4;
    //public static final BufferedImage pipe1v5;
    //public static final BufferedImage flag;
    
    public static void initImages(){
        try{
            mario=ImageIO.read(new File("resources/images/marioBig.png"));
            block=ImageIO.read(new File("resources/images/block.png"));
            floor=ImageIO.read(new File("resources/images/floor.png"));
            breakableBlock=ImageIO.read(new File("resources/images/breakableBlock.png"));
            sky=ImageIO.read(new File("resources/images/sky.png"));
            goomba=ImageIO.read(new File("resources/images/goomba.png"));
            koopa=ImageIO.read(new File("resources/images/koopa.png"));
            pipe2v2=ImageIO.read(new File("resources/images/pipe2x2.png"));
            pipe2v3=ImageIO.read(new File("resources/images/pipe2x3.png"));
        }catch(IOException e){
            System.out.println("Major Error :: Image files could not be read");
        }
    }
}