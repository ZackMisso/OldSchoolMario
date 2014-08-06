package gameState;

import tilesAndGraphics.TileMap;
import java.awt.Color;

import gameState.GameStateManager;
import tilesAndGraphics.Background;

import java.awt.Graphics2D;
import core.GamePanel;

public class Level1State extends GameState{
    private TileMap tileMap;
    private Background background;
    
    public Level1State(GameStateManager param){
        setGSM(param);
    }
    
    public void init(){
        tileMap=new TileMap(16);
        // load the tile map
    }
    
    public void update(){
        
    }
    
    public void draw(Graphics2D g){
        g.setColor(Color.white);
        g.fillRect(0,0,GamePanel.WIDTH*GamePanel.SCALE,GamePanel.HEIGHT*GamePanel.SCALE);
        tileMap.draw(g);
    }
    
    public void keyPressed(int k){
        
    }
    
    public void keyReleased(int k){
        
    }
    
    // getter methods
    public TileMap getTileMap(){return tileMap;}
    public Background getBackground(){return background;}
    
    // setter methods
    public void setTileMap(TileMap param){tileMap=param;}
    public void setBackground(Background param){background=param;}
}