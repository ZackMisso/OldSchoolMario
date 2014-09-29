/**
 *
 * @author Zack and Mark
 * 
 */
package datastructures;
public class IntCoordinate {
    private int xpos;
    private int ypos;
    
    public IntCoordinate(int x,int y){
        xpos=x;
        ypos=y;
    }
    
    // returns the sum between two coordinates
    public IntCoordinate add(IntCoordinate other){
        return new IntCoordinate(xpos+other.getXpos(),ypos+other.getYpos());
    }
    
    // returns the difference between two coordinates
    public IntCoordinate subtract(IntCoordinate other){
        return new IntCoordinate(xpos-other.getXpos(),ypos-other.getYpos());
    }
    
    // returns the sum between this coordinate and two constants
    public IntCoordinate add(int x,int y){
        return new IntCoordinate(xpos+x,ypos+y);
    }
    
    // returns the difference between this coordinate and two constants
    public IntCoordinate subtract(int x,int y){
        return new IntCoordinate(xpos-x,ypos-y);
    }
    
    // returns if this coordinate is the same as the other
    public boolean equals(IntCoordinate other){
        return xpos==other.getXpos()&&ypos==other.getYpos();
    }
    
    // adds the constants to the coordinates
    public void addTo(int x,int y){
        xpos+=x;
        ypos+=y;
    }
    
    // subtracts the constants from the coordinates
    public void subtractFrom(int x,int y){
        xpos-=x;
        ypos-=y;
    }
    
    // getter methods
    public int getXpos(){return xpos;}
    public int getYpos(){return ypos;}
    
    // setter methods
    public void setXpos(int param){xpos=param;}
    public void setYpos(int param){ypos=param;}
}