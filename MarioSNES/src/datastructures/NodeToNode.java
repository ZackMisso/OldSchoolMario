/**
 *
 * @author Zackary Misso
 * 
 */
package datastructures;
import gameState.GameState;
import java.util.ArrayList;
import java.util.Random;
import neuroevolution.Node;
import neuroevolution.connections.Connection;
import neuroevolution.neurons.*;
import neuroevolution.neurons.hardcoded.*;
public class NodeToNode {
    private Node one;
    private Node two;
    
    public NodeToNode(Node a,Node b){
        one=a;
        two=b;
    }
    
    // returns a cross between the two nodes
    public Node mix(GameState ref){
        if(one instanceof Connection && two instanceof Connection)
            return connectionMix();
        else if(one instanceof Neuron && two instanceof Neuron)
            return neuronMix(ref);
        else{
            System.out.println("Conflicting Types :: NodeToNode");
            System.exit(0);
            return null;
        }
    }
    
    // returns a cross between the two connections
    public Node connectionMix(){
        Connection newConnection=new Connection();
        Connection oneC=(Connection)one;
        Connection twoC=(Connection)two;
        Random random=new Random();
        newConnection.setGiveNeuron(oneC.getGiveNeuron());
        newConnection.setRecieveNeuron(oneC.getRecieveNeuron());
        newConnection.setInnovationNum(oneC.getInnovationNum());
        // set active
        double test=random.nextDouble();
        if(test>.5)
            newConnection.setActive(oneC.getActive());
        else
            newConnection.setActive(twoC.getActive());
        // set weight
        test=random.nextDouble();
        if(test>.5)
            newConnection.setWeight(oneC.getWeight());
        else
            newConnection.setWeight(twoC.getWeight());
        return newConnection;
    }
    
    // returns a cross between the two neurons
    public Node neuronMix(GameState ref){
        Neuron oneN = new Neuron_Add();
        Neuron twoN = new Neuron_Add();
        Neuron newNeuron;
        if(one instanceof InputNeuron_Add){
            oneN = (InputNeuron_Add)one;
            twoN = (InputNeuron_Add)two;
            newNeuron=new InputNeuron_Add();
        }
        else if(one instanceof OutputNeuron_Add){
            oneN = (OutputNeuron_Add)one;
            twoN = (OutputNeuron_Add)two;
            newNeuron=new OutputNeuron_Add();
        }
        else if(one instanceof DistanceThresholdChecker){
            newNeuron = new DistanceThresholdChecker(ref);
            oneN = (DistanceThresholdChecker) one;
            twoN = (DistanceThresholdChecker) two;
            
            //crossover HC of each DTC
            HorizontalChecker temphc = new HorizontalChecker(ref);
            temphc.setBias((((DistanceThresholdChecker)oneN).gethcbias()+((DistanceThresholdChecker)twoN).gethcbias())/2);
            ((DistanceThresholdChecker)newNeuron).setHC(temphc);
            
            //crossover VC
            VerticalChecker tempvc = new VerticalChecker(ref);
            tempvc.setBias((((DistanceThresholdChecker)oneN).getvcbias()+((DistanceThresholdChecker)twoN).getvcbias())/2);
            ((DistanceThresholdChecker)newNeuron).setVC(tempvc);
            
            //crossover bias
            ((DistanceThresholdChecker)newNeuron).sethcweight((((DistanceThresholdChecker)oneN).gethcweight()+((DistanceThresholdChecker)twoN).gethcweight())/2);
            ((DistanceThresholdChecker)newNeuron).setvcweight((((DistanceThresholdChecker)oneN).getvcweight()+((DistanceThresholdChecker)twoN).getvcweight())/2);
        }
        else if(one instanceof PitChecker){
            oneN = (PitChecker)one;
            twoN = (PitChecker)two;
            newNeuron = new PitChecker(ref);
        }
        else
            newNeuron=new Neuron_Add();
        // set up bias
        newNeuron.setBias((oneN.getBias()+twoN.getBias())/2);
        // inputs
        newNeuron.setInputs(oneN.getInputs());
        newNeuron.setOutputs(oneN.getOutputs());
        return newNeuron;
    }
    
    // For evolving a network without special neurons (probably not needed)
    public Node neuronMixNonSpecial(){
        Neuron oneN=(Neuron)one;
        Neuron twoN=(Neuron)two;
        Neuron newNeuron;
        if(oneN instanceof InputNeuron)
            newNeuron=new InputNeuron_Add();
        else if(oneN instanceof OutputNeuron)
            newNeuron=new OutputNeuron_Add();
        else
            newNeuron=new Neuron_Add();
        // set up bias
        Random random=new Random();
        double test=random.nextDouble();
        if(test>.5)
            newNeuron.setBias(oneN.getBias());
        else
            newNeuron.setBias(twoN.getBias());
        // inputs
        test=random.nextDouble();
        newNeuron.setInputs(oneN.getInputs());
        newNeuron.setOutputs(oneN.getOutputs());
        return newNeuron;
    }

    // this is used for the debugger
    public static ArrayList<Integer> getSharedInnoNums(ArrayList<NodeToNode> nodes){
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<nodes.size();i++)
            if(nodes.get(i).getOne()!=null&&nodes.get(i).getTwo()!=null)
                list.add(nodes.get(i).getOne().getInnovationNum());
        return list;
    }
    
    // getter methods
    public Node getOne(){return one;}
    public Node getTwo(){return two;}
    
    // setter methods
    public void setOne(Node param){one=param;}
    public void setTwo(Node param){two=param;}
}