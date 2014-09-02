/**
 *
 * @author Zackary Misso
 * 
 */
package core;
import javax.swing.JFrame;
import neuroevolution.MarioAI;
import tilesAndGraphics.ImageCache;
public class Game {
    public static void initMario(){
        JFrame window=new JFrame("Mario");
        ImageCache.initImages();
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
    
    public static void initMarioAI(MarioAI ai){
        // implement
    }
}
