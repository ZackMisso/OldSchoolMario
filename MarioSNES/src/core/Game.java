/**
 *
 * @author Zackary Misso
 * 
 */
package core;
import javax.swing.JFrame;
import tilesAndGraphics.ImageCache;
public class Game {
    public static void main(String[] args){
        JFrame window=new JFrame("Mario");
        ImageCache.initImages();
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
}
