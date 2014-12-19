/**
 *
 * @author Zackary Misso
 *
 */
package neuroevolution.speciation;
import core.GlobalController;
import neuroevolution.networks.NeuralNetwork;
import neuroevolution.Node;
import neuroevolution.neurons.Neuron;
import neuroevolution.connections.Connection;
import datastructures.NodeToNode;
import java.util.ArrayList;
public class SpeciationFunctions {
	public static final double THRESHOLD=25.0; // change as needed
	public static final double weightConstant=.4; // change as needed

    // returns the list of nodes that have the same innovation between two networks
    public static ArrayList<NodeToNode> getSimilarNodes(NeuralNetwork one,NeuralNetwork two){
        ArrayList<Node> oneNodes=one.getAllNodes();
        ArrayList<Node> twoNodes=two.getAllNodes();
        ArrayList<NodeToNode> nodes=new ArrayList<>();
        // sort the two lists of genes
        Node.sort(oneNodes);
        Node.sort(twoNodes);
        int i=0;
        int f=0;
        while(i!=oneNodes.size()||f!=twoNodes.size()){
            if(oneNodes.get(i).getInnovationNum()==twoNodes.get(f).getInnovationNum())
                nodes.add(new NodeToNode(oneNodes.get(i++),twoNodes.get(f++)));
            else if(oneNodes.get(i).getInnovationNum()<twoNodes.get(f).getInnovationNum())
                i++;
            else
                f++;
        }
        return nodes;
    }

    // returns if the two individuals are apart of the same species
	public static boolean sameSpecies(NeuralNetwork one,NeuralNetwork two){
		return findNetworkDistance(one,two)<THRESHOLD;
	}

    // calculates the theoretical "distance" between two individuals' phenotypes
    public static double findNetworkDistance(NeuralNetwork one,NeuralNetwork two){
    	ArrayList<Node> oneNodes=Node.sort(one.getAllNodes());
    	ArrayList<Node> twoNodes=Node.sort(two.getAllNodes());
    	double distance=0.0;
    	int i=0;
    	int f=0;
    	int oneIn=oneNodes.get(i).getInnovationNum();
    	int twoIn=twoNodes.get(i).getInnovationNum();
    	while(i!=oneNodes.size()-1&&f!=twoNodes.size()-1){
    		if(oneIn==twoIn){
    			distance+=findNodeDistance(oneNodes.get(i),twoNodes.get(f));
    			oneIn=oneNodes.get(++i).getInnovationNum();
    			twoIn=twoNodes.get(++f).getInnovationNum();
    		}else if(oneIn>twoIn){
    			distance+=1.0;
    			twoIn=twoNodes.get(++f).getInnovationNum();
    		}else{
    			distance+=1.0;
    			oneIn=oneNodes.get(++i).getInnovationNum();
    		}
    	}
    	while(i!=oneNodes.size()){
    		distance+=1.0;
    		i++;
    	}
    	while(f!=twoNodes.size()){
    		distance+=1.0;
    		f++;
    	}
    	return distance;
    }

    // finds the distance between two of the same node
    public static double findNodeDistance(Node one,Node two){
        // THERE ARE OCCASIONAL EXCEPTIONS CAUSED BY THIS METHOD ON 1/100 OF RUNS
        if(one instanceof Neuron)
            return findNeuronDistance((Neuron)one,(Neuron)two);
        else
            return findConnectionDistance((Connection)one,(Connection)two);
    }

    // finds the distance between two of the same neuron
    public static double findNeuronDistance(Neuron one,Neuron two){
    	double biasDistance=one.getBias()-two.getBias();
    	double maxDistance=GlobalController.MAX_WEIGHT_VALUE-GlobalController.MIN_WEIGHT_VALUE;
    	biasDistance=Math.abs(biasDistance);
    	maxDistance=Math.abs(maxDistance);
    	double proportion=biasDistance/maxDistance;
    	return proportion/weightConstant;
    }

    // finds the distance between two of the same connections
    public static double findConnectionDistance(Connection one,Connection two){
    	double weightDistance=one.getWeight()-two.getWeight();
    	double maxDistance=GlobalController.MAX_WEIGHT_VALUE-GlobalController.MIN_WEIGHT_VALUE;
    	weightDistance=Math.abs(weightDistance);
    	maxDistance=Math.abs(maxDistance);
    	double proportion=weightDistance/maxDistance;
    	return proportion/weightConstant;
    }
}