package gameState;

import java.awt.Graphics2D;

public abstract class GameState {
    private GameStateManager gsm;
    
    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
    
    // getter methods
    public GameStateManager getGSM(){return gsm;}
    
    // setter methods
    public void setGSM(GameStateManager param){gsm=param;}
}
