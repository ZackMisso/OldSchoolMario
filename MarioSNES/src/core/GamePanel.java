/**
 *
 * @author Zackary Misso
 * 
 */
package core;
import gameState.GameStateManager;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
import java.awt.Graphics;
public class GamePanel extends JPanel implements Runnable,KeyListener{
    public static final int WIDTH=320;
    public static final int HEIGHT=240;
    public static final int SCALE=2;
    private Thread thread;
    private int FPS=60;
    private long targetTime=1000/FPS;
    private BufferedImage image;
    private Graphics2D g;
    private GameStateManager gsm;
    
    public GamePanel(){
        super();
        gsm=new GameStateManager();
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        setFocusable(true);
        requestFocus();
    }
    
    public void addNotify(){
        super.addNotify();
        if(thread==null&&(GlobalController.gameRunning||GlobalController.aiRun)){
            thread=new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    public void controlledRun(){
        thread=new Thread(this);
        addKeyListener(this);
        init();
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){System.out.println("Thread Interupted... IDK");}
    }
    
    public void init(){
        image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        g=(Graphics2D)image.getGraphics();
        GlobalController.running=true;
    }
    
    public void run(){
        init();
        long start,elapsed,wait;
        while(GlobalController.running){
            if(GlobalController.headless){
                update();
            }
            else{
                start=System.nanoTime();
                update();
                draw();
                drawToScreen();
                elapsed=System.nanoTime()-start;
                wait=targetTime-elapsed/1000000;
                if(wait<0)
                    wait=5;
                try{
                    Thread.sleep(wait);
                }catch(InterruptedException e){}
            }
        }
    }
    
    private void update(){
        gsm.update();
    }
    
    private void draw(){
        gsm.draw(g);
    }
    
    private void drawToScreen(){
        Graphics g2=getGraphics();
        g2.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
        g2.dispose();
    }
    
    public void keyTyped(KeyEvent event){}
    
    public void keyPressed(KeyEvent event){
        gsm.keyPressed(event.getKeyCode());
    }
    
    public void keyReleased(KeyEvent event){
        gsm.keyReleased(event.getKeyCode());
    }

    // getter methods
    public GameStateManager getGSM(){return gsm;}
}