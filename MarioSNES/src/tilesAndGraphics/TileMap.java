package tilesAndGraphics;

import core.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;

public class TileMap {
    private double x;
    private double y;
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;
    private double tween; // probably dont use this
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;
    
    public TileMap(int tileSize){
        this.tileSize=tileSize;
        numRowsToDraw=GamePanel.HEIGHT/tileSize+2;
        numColsToDraw=GamePanel.WIDTH/tileSize+2;
        tween=.07;
    }
    
    // this will be implemented in GameReader
    public void loadTiles(String s){
        try{
            tileset=ImageIO.read(new File(""));
            numTilesAcross=tileset.getWidth()/tileSize;
            tiles=new Tile[2][numTilesAcross];
        }catch(Exception e){
            
        }
    }
    
    public void loadMap(String s){
        // do not use this
    }
    
    public int getType(int row,int col){
        int rc=map[row][col];
        int r=rc/numTilesAcross;
        int c=rc%numTilesAcross;
        return tiles[r][c].getType();
    }
    
    public void setPosition(double x,double y){
        this.x+=(x-this.x)*tween;
        this.y+=(y-this.y)*tween;
        fixBounds();
    }
    
    private void fixBounds(){
        if(x<xmin) x=xmin;
        if(x>xmax) x=xmax;
        if(y<ymin) y=ymin;
        if(y>ymax) y=ymax;
    }
    
    public void draw(Graphics2D g){
        for(int i=0;i<numRowsToDraw;i++){
            for(int f=0;f<numColsToDraw;i++){
                // implement
            }
        }
    }
    
    // getter methods
    public double getX(){return x;}
    public double getY(){return y;}
    public int getTileSize(){return tileSize;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
}