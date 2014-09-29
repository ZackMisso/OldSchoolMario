/**
 *
 * @author Zack and Mark
 * 
 */
package datastructures;
public class DoubleCoordinate {
    private double xpos;
    private double ypos;
    
    public DoubleCoordinate(){
        xpos=0.0;
        ypos=0.0;
    }
    
    public DoubleCoordinate(double x,double y){
        xpos=x;
        ypos=y;
    }
    
    // returns the sum between two coordinates
    public DoubleCoordinate add(DoubleCoordinate other){
        return new DoubleCoordinate(xpos+other.getXpos(),ypos+other.getYpos());
    }
    
    // returns the difference between two coordinates
    public DoubleCoordinate subtract(DoubleCoordinate other){
        return new DoubleCoordinate(xpos-other.getXpos(),ypos-other.getYpos());
    }
    
    // returns the sum between this coordinate and two constants
    public DoubleCoordinate add(double x,double y){
        return new DoubleCoordinate(xpos+x,ypos+y);
    }
    
    // returns the difference between this coordinate and two constants
    public DoubleCoordinate subtract(double x,double y){
        return new DoubleCoordinate(xpos-x,ypos-y);
    }
    
    // returns the tile Coordinate equivalent of this coordinate
    public IntCoordinate tilify(int tileSize){
        return new IntCoordinate((int)xpos/tileSize,(int)ypos/tileSize);
    }
    
    // returns if this coordinate is the same as the other
    public boolean equals(DoubleCoordinate other){
        return xpos==other.getXpos()&&ypos==other.getYpos();
    }
    
    // adds the constants to the coordinates
    public void addTo(double x,double y){
        xpos+=x;
        ypos+=y;
    }
    
    public void addTo(DoubleCoordinate other){
        xpos+=other.getXpos();
        ypos+=other.getYpos();
    }
    
    // subtracts the constants from the coordinates
    public void subtractFrom(double x,double y){
        xpos-=x;
        ypos-=y;
    }
    
    public void subtractFrom(DoubleCoordinate other){
        xpos-=other.getXpos();
        ypos-=other.getYpos();
    }
    
    // getter methods
    public double getXpos(){return xpos;}
    public double getYpos(){return ypos;}
    
    // setter methods
    public void setXpos(double param){xpos=param;}
    public void setYpos(double param){ypos=param;}
}