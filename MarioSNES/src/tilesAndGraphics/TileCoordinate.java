/**
 *
 * @author Zack and Mark
 *
 */
package tilesAndGraphics;
public class TileCoordinate {
    private int xpos; // the xposition of the coordinate
    private int ypos; // the yposition of the coordinate
    private int value; // the value at that coordinate; blocked or not

    public TileCoordinate(int x,int y,int val){
    	xpos=x;
    	ypos=y;
    	value=val;
    }

    // getter methods
    public int getXpos(){return xpos;}
    public int getYpos(){return ypos;}
    public int getValue(){return value;}

    // setter methods
    public void setXpos(int param){xpos=param;}
    public void setYpos(int param){ypos=param;}
    public void setValue(int param){value=param;}
}