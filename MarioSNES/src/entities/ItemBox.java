/**
 *
 * @author Zackary Misso
 *
 */
package entities;
public class ItemBox extends Block{
	private Collectable contents;
	private boolean empty;

    public ItemBox(double x,double y,int w,int h){
    	super(x,t,w,h);
    	empty=true;
    }

    public void hit(){
    	if(contents!=null){
    		// implement
    		contents=null;
    		empty=true;
		}
    }

    // getter method
    public Collectable getContents(){return contents;}
    public boolean getEmpty(){return empty;}

    // setter method
    public void setContents(Collectable param){contents=param;}
    public void setEmpty(boolean param){empty=param;}
}
