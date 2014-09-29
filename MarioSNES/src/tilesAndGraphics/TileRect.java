/**
 *
 * @author Zack and Mark
 *
 */
package tilesAndGraphics;
public class TileRect {
    private TileCoordinate[][] rect;
    private int startX; // not sure if we need references to these yet
    private int startY; // not sure if we need references to these yet
    //private TileMap map;

    public TileRect(TileMap param,int x,int y, int lengthX,int lengthY){
    	startX=x;
    	startY=y;
    	createRect(param,lengthX,lengthY);
    }

    // creates the recangle from the specified dimensions on the map
    private void createRect(TileMap map,int lengthX,int lengthY){
    	// initialize the rect
    	rect=new TileCoordinate[lengthY][lengthX];
    	// get the dimensions
    	//int maxWidth=map.getMap()[0].length;
    	int maxHeight=map.getMap().length;
        //System.out.println(maxHeight+" HEIGHT :: TileRect");
        int maxWidth=map.getMap()[0].length;
    	// loop to create the rect
    	for(int i=startY; i<startY+lengthY; i++){
            for(int f=startX; f<startX+lengthX; f++){
    		// bounds check
    		if(i<0||i>=maxWidth||f<0||f>=maxHeight)
                    rect[i-startY][f-startX]=new TileCoordinate(f,i,1);
                else // normal tile creation
                    rect[i-startY][f-startX]=new TileCoordinate(f,i,map.getMap()[i][f]);
            }
    	}
    }

    // gets the object at the specified index
    private TileCoordinate index(int x,int y){
    	return rect[x-startX][y-startY];
    }

    public boolean isBlock(int x,int y){
        return index(x,y).getValue()!=0;
    }

    //public TileRect(TileCoordinate[][] rect){
    //	// implement
    //}

    // getter methods
    public TileCoordinate[][] getRect(){return rect;}
}