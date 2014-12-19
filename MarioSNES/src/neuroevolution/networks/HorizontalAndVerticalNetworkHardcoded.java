/**
 *
 * @author Zack and Mark
 * 
 */

//first hardcoded network without recurrent connections
package neuroevolution.networks;
import gameState.*;
import java.util.*;
import neuroevolution.connections.Connection;
import neuroevolution.neurons.*;
import neuroevolution.neurons.hardcoded.*;
import neuroevolution.speciation.HistoricalTracker;

public class HorizontalAndVerticalNetworkHardcoded extends SpeciationNeuralNetwork {
    /* Inheirited fields:
    protected ArrayList<Neuron> neurons;
    protected ArrayList<Connection> connections;
    protected ArrayList<Double> inputs;
    protected ArrayList<Double> outputs;
    protected RandomNumberGenerator rng;
    protected double fitness;
    protected int nodeCnt;
    */
    private DistanceThresholdChecker DTC;
    
    public HorizontalAndVerticalNetworkHardcoded(HistoricalTracker param, GameState ref){
        super(param,0,3);                                 //Call NN constructor
        DTC = new DistanceThresholdChecker(ref);    //Initialize input node
        DTC.setInnovationNum(3);
        neurons.add(DTC);
        makeConnection(DTC, neurons.get(2));
        neurons.get(1).setBias(0.0);                //set left bias zero
    }
    
    public double evalHorizontal(){
        return DTC.evalHorizontal();
    }
    
    public double evalVertical(){
        return DTC.evalVertical();
    }
    
}
