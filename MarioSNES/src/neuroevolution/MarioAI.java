/**
 *
 * @author Zackary Misso
 * 
 */
package neuroevolution;
import core.GlobalController;
import entities.Block;
import entities.Mario;
import gameState.Level1State;
import java.util.ArrayList;
import neuroevolution.networks.NeuralNetwork;
import neuroevolution.networks.SpeciationNeuralNetwork;
import testtools.CMDTester;
public class MarioAI {
    
    private NeuralNetwork net;
    private ArrayList<Double> outputs;
    private boolean readyToPropogate = false;
    
    public MarioAI(){
        net=null;
    }

    public void createAI(NeuralNetwork param){
    	net=param;
        readyToPropogate = true;
    }

    public boolean movesLeft(){
        //if(GlobalController.speciation) // temporary
        //    return false;
        return outputs.get(0)>=1.0;
        //return false;
    }

    public boolean movesRight(){
        //System.out.println("Moving right by SPECIATION");
        //if(GlobalController.speciation)
        //    return outputs.get(0)>=1.0;
    	return outputs.get(1)>=1.0;
    }

    public boolean jumps(){
        //if(GlobalController.speciation) // temporary
        //    return false;
    	return outputs.get(2)>=1.0;
    }

    public void propogate(Level1State state){
    	ArrayList<Double> inputs=new ArrayList<>();
    	// sensor infor
    	//ArrayList<Block> nearest=getNearestBlocks(state.getBlocks(),state.getPlayer());
    	//for(int i=0;i<nearest.size();i++){
    	//	inputs.add(nearest.get(i).getRelativeX(state.getPlayer()));
    	//	inputs.add(nearest.get(i).getRelativeY(state.getPlayer()));
    	//}
    	// now propogate
        //System.out.println(inputs.toString());
    	net.setInputs(inputs);
        try{outputs=net.run();}
        catch(StackOverflowError e){
            System.out.println("Recurrent connection found. Outputs set to default. :: MarioAI 52");
            outputs = new ArrayList();
            outputs.add(0.0);
            outputs.add(0.0);
            outputs.add(0.0);
            //new CMDTester(net);
            net.setFitness(Double.MIN_VALUE);
            return;
        }
        //catch(NullPointerException e){
        //    new CMDTester(net);
        //}
        
        //System.out.println(outputs.toString());
        //System.out.println(net.getNeurons().size());
        //System.out.println("THIS HAS RAN");
    }

    // The efficiency of this could be greatly improved
    private ArrayList<Block> getNearestBlocks(ArrayList<Block> blocks,Mario player){
    	ArrayList<Block> nearest=new ArrayList<>();
    	nearest.add(new Block(0,0,-100000,-1000000));
    	nearest.add(new Block(0,0,-100000,-1000000));
    	nearest.add(new Block(0,0,-100000,-1000000));
    	double zero=10000000000.0;
    	double one=10000000000.0;
    	double two=10000000000.0;
    	for(int i=0;i<blocks.size();i++){
    		Block blk=blocks.get(i);
                if(blk==null)
                    continue;
    		double dist=player.efficientDistanceBetween(blk);
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
        //System.out.println(zero+" :: closest distance to a block");
        return nearest;
    }
    
    // getter methods
    public NeuralNetwork getNet(){return net;}
    public boolean isReadytoPropogate(){return readyToPropogate;}
    public ArrayList<Double> getOutputs(){return outputs;}
    // setter methods
    public void setNet(SpeciationNeuralNetwork param){net=param;} //21 Sep 14 mark changed from NN to H&VNHC
}