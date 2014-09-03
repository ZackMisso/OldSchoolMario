/**
 *
 * @author Zackary Misso
 * 
 */
package neuroevolution;
public class MarioAI {
    private NeuralNetwork net;
    private ArrayList<Double> outputs;
    
    public MarioAI(){
        net=null;
    }

    public void createAI(NeuralNetwork param){
    	net=param;
    }

    public boolean movesLeft(){
    	return outputs.get(0)>=1.0;
    }

    public boolean movesRight(){
    	return outputs.get(1)>=1.0;
    }

    public boolean jumps(){
    	return outputs.get(2)>=1.0;
    }

    public void propagate(Level1State state){
    	ArrayList<Double> inputs=new ArrayList<Double>();
    	// sensor infor
    	ArrayList<Block> nearest=getNearestBlocks(state.getBlocks(),state.getPlayer());
    	for(int i=0;i<nearest.size();i++){
    		inputs.add(nearest.get(i).getRelativeX(state.getPlayer()));
    		inputs.add(nearest.get(i).getRelativeY(state.getPlayer()));
    	}
    	// now propogate
    	net.setInputs(inputs);
    	outputs=net.run();
    }

    // The effitientcy of this could be greatly improved
    private ArrayList<Block> getNearestBlocks(ArrayList<Block> blocks,Mario player){
    	ArrayList<Block> nearest=new ArrayList<>();
    	nearest.add(new Block(0,0,-100000,-1000000));
    	nearest.add(new Block(0,0,-100000,-1000000));
    	nearest.add(new Block(0,0,-100000,-1000000));
    	double zero=10000000000;
    	double one=10000000000;
    	double two=10000000000;
    	for(int i=0;i<blocks.size();i++){
    		Block blk=blocks.get(i);
    		double dist=player.effitientDistanceBetween(blk);
    		if(dist<zero){
    			double temp=zero;
    			zero=dist;
    			dist=zero;
    			Block btemp=nearest.get(0);
    			nearest.set(0,blk);
    			blk=btemp;
    		}
    		if(dist<one){
    			double temp=one;
    			one=dist;
    			dist=one;
    			Block btemp=nearest.get(1);
    			nearest.set(1,blk);
    			blk=btemp;
    		}
    		if(dist<two){
    			two=dist;
    			nearest.set(2,blk);
    		}
    	}
    }
    
    // getter methods
    public NeuralNetwork getNet(){return net;}
    
    // setter methods
    public void setNet(NeuralNetwork param){net=param;}
}
