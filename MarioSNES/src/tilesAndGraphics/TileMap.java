/**
 *
 * @author Zackary Misso
 * 
 */
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
        
        ////int rc=map[row][col];
        ////int r=rc/numTilesAcross;
        //int c=rc%numTilesAcross;
        return tiles[row][col].getType();
    }
    
    public void setPosition(double x,double y){
        this.x+=(x-this.x)*tween;
        this.y+=(y-this.y)*tween;
        fixBounds();
        colOffset=(int)-this.x/tileSize;
        rowOffset=(int)-this.y/tileSize;
    }
    
    private void fixBounds(){
        if(x<xmin) x=xmin;
        if(x>xmax) x=xmax;
        if(y<ymin) y=ymin;
        if(y>ymax) y=ymax;
    }
    
    public void draw(Graphics2D g){
        for(int i=rowOffset;i<numRowsToDraw+rowOffset&&i<numRows;i++){
            for(int f=colOffset;f<numColsToDraw+colOffset&&f<numCols;f++){
                //int rc=map[i][f];
                //int r=rc/numTilesAcross;
                //int c=rc%numTilesAcross;
                g.drawImage(tiles[i][f].getImage(),(int)x+f*tileSize,(int)y+i*tileSize,null);
            }
        }
    }
    
    // getter methods
    public double getX(){return x;}
    public double getY(){return y;}
    public int getXmin(){return xmin;}
    public int getYmin(){return ymin;}
    public int getXMax(){return xmax;}
    public int getYmax(){return ymax;}
    public double getTween(){return tween;}
    public int[][] getMap(){return map;}
    public int getTileSize(){return tileSize;}
    public int getNumRows(){return numRows;}
    public int getNumCols(){return numCols;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getNumTilesAcross(){return numTilesAcross;}
    public Tile[][] getTiles(){return tiles;}
    public int getRowOffset(){return rowOffset;}
    public int getColOffset(){return colOffset;}
    public int getNumRowsToDraw(){return numRowsToDraw;}
    public int getNumColsToDraw(){return numColsToDraw;}
    
    // setter methods
    public void setX(double param){x=param;}
    public void setY(double param){y=param;}
    public void setXmin(int param){xmin=param;}
    public void setYmin(int param){ymin=param;}
    public void setXmax(int param){xmax=param;}
    public void setYmax(int param){ymax=param;}
    public void setTween(double param){tween=param;}
    public void setMap(int[][] param){map=param;}
    public void setTileSize(int param){tileSize=param;}
    public void setNumRows(int param){numRows=param;}
    public void setNumCols(int param){numCols=param;}
    public void setWidth(int param){width=param;}
    public void setHeight(int param){height=param;}
    public void setNumTilesAcross(int param){numTilesAcross=param;}
    public void setTiles(Tile[][] param){tiles=param;}
    public void setRowOffset(int param){rowOffset=param;}
    public void setColOffset(int param){colOffset=param;}
    public void setNumRowsToDraw(int param){numRowsToDraw=param;}
    public void setNumColsToDraw(int param){numColsToDraw=param;}
}