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
import entities.Floor;
import core.GamePanel;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
public class Level1State extends GameState{
    private TileMap tileMap;
    private Mario player;
    private Background background;
    private ArrayList<Block> blocks;
    private double xOffset;
    private double yOffset;
    private double totalPast;
    
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
        totalPast=100;
        xOffset=0;
        yOffset=0;
        initBlocks();
    }
    
    private void initBlocks(){
        blocks.add(new Block(180,120,16,16));
        blocks.add(new Block(50,120,16,16));
        blocks.add(new Block(88,160,16,16));
        blocks.add(new Block(88+16,160,16,16));
        blocks.add(new Block(88+32,160,16,16));
        int y=208;
        for(int i=0;i<31;i++){
            blocks.add(new Floor(i*16,y,16,16));
            blocks.add(new Floor(i*16,y+16,16,16));
        }
        int x=34*16;
        for(int i=0;i<16;i++){
            blocks.add(new Floor(i*16+x,y,16,16));
            blocks.add(new Floor(i*16+x,y+16,16,16));
        }
        x+=(16+7)*16;
        for(int i=0;i<38;i++){
            blocks.add(new Floor(i*16+x,y,16,16));
            blocks.add(new Floor(i*16+x,y+16,16,16));
        }
        x+=(38+4)*16;
        for(int i=0;i<32;i++){
            blocks.add(new Floor(i*16+x,y,16,16));
            blocks.add(new Floor(i*16+x,y+16,16,16));
        }
        x+=(32+4)*16;
        for(int i=0;i<20;i++){
            blocks.add(new Floor(i*16+x,y,16,16));
            blocks.add(new Floor(i*16+x,y+16,16,16));
        }
        System.out.println(x+=10*16);
        x=39*16;
        for(int i=0;i<4;i++){
            blocks.add(new Block(i*16+x,208-4*16,16,16));
        }
        x+=4*16;
        for(int i=0;i<8;i++){
            blocks.add(new Block(i*16+x,208-7*16,16,16));
        }
    }
    
    public void update(){
        player.update(blocks,this);
        player.finalizeMovement(this);
        for(int i=0;i<blocks.size();i++){
            blocks.get(i).setXpos(blocks.get(i).getXpos()-xOffset);
            blocks.get(i).updateC();
        }
        totalPast+=xOffset;
        if(totalPast>2320)
            System.exit(0);
        xOffset=0;
    }
    
    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,GamePanel.WIDTH*GamePanel.SCALE,GamePanel.HEIGHT*GamePanel.SCALE);
        //background.draw(g);
        for(int i=0;i<blocks.size();i++)
            blocks.get(i).draw(g,this);
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
    public double getXOffset(){return xOffset;}
    public double getYOffset(){return yOffset;}
    public double getTotalPast(){return totalPast;}
    
    // setter methods
    public void setTileMap(TileMap param){tileMap=param;}
    public void setPlayer(Mario param){player=param;}
    public void setBackground(Background param){background=param;}
    public void setXOffset(double param){xOffset=param;}
    public void setYOffset(double param){yOffset=param;}
    public void setTotalPast(double param){totalPast=param;}
}