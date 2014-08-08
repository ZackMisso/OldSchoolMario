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
import entities.Block;
import core.GamePanel;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
public class Level1State extends GameState{
    private TileMap tileMap;
    private Mario player;
    private Background background;
    private ArrayList<Block> blocks;
    
    public Level1State(GameStateManager param){
        setGSM(param);
        blocks=new ArrayList<>();
        init();
    }
    
    public void init(){
        tileMap=ManualLevel.createMap();
        tileMap.setPosition(0,0);
        // load the tile map
        player=new Mario(tileMap);
        player.setPosition(100,100);
        initBlocks();
    }
    
    private void initBlocks(){
        blocks.add(new Block(180,120,16,16));
        blocks.add(new Block(50,120,16,16));
        blocks.add(new Block(95,160,16,16));
        blocks.add(new Block(95+16,160,16,16));
        blocks.add(new Block(95+32,160,16,16));
        //blocks.add(new Block(80,100,16,16));
        //blocks.add(new Block(100,160,16,16));
    }
    
    public void update(){
        player.update(blocks);
        // implement more updates
        // now check for collisions
        //player.checkCollisionsWithBlocks(blocks);
        player.finalizeMovement();
    }
    
    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,GamePanel.WIDTH*GamePanel.SCALE,GamePanel.HEIGHT*GamePanel.SCALE);
        // implement
        //background.draw(g);
        for(int i=0;i<blocks.size();i++)
            blocks.get(i).draw(g);
        //tileMap.draw(g);
        player.draw(g);
    }
    
    public void keyPressed(int k){
        player.keyPressed(k);
    }
    
    public void keyReleased(int k){
        player.keyReleased(k);
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