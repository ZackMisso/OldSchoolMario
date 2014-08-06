/**
 *
 * @author Zackary Misso
 * 
 */
package gameState;
import tilesAndGraphics.TileMap;
import tilesAndGraphics.Background;
import tilesAndGraphics.ManualLevel;
import entities.Mario;
import java.awt.Graphics2D;
import java.awt.Color;
import core.GamePanel;
public class Level1State extends GameState{
    private TileMap tileMap;
    private Mario player;
    private Background background;
    
    public Level1State(GameStateManager param){
        setGSM(param);
        init();
    }
    
    public void init(){
        tileMap=ManualLevel.createMap();
        tileMap.setPosition(0,0);
        // load the tile map
        player=new Mario(tileMap);
        player.setPosition(100,100);
    }
    
    public void update(){
        player.update();
    }
    
    public void draw(Graphics2D g){
        g.setColor(Color.white);
        g.fillRect(0,0,GamePanel.WIDTH*GamePanel.SCALE,GamePanel.HEIGHT*GamePanel.SCALE);
        // implement
        //background.draw(g);
        tileMap.draw(g);
        player.draw(g);
    }
    
    public void keyPressed(int k){
        // implement
    }
    
    public void keyReleased(int k){
        // implement
    }
    
    // getter methods
    public TileMap getTileMap(){return tileMap;}
    public Mario getPlayer(){return player;}
    public Background getBackground(){return background;}
    
    // setter methods
    public void setTileMap(TileMap param){tileMap=param;}
    public void setPlayer(Mario param){player=param;}
    public void setBackground(Background param){background=param;}
}