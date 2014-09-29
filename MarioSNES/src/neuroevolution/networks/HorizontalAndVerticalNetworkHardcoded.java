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

public class HorizontalAndVerticalNetworkHardcoded extends NeuralNetwork {
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
    
    public HorizontalAndVerticalNetworkHardcoded(GameState ref){
        super(0,3);                                 //Call NN constructor
        DTC = new DistanceThresholdChecker(ref);    //Initialize input node
        assert neurons.size()==3;                   //safety check: two output neurons, one hidden
        neurons.add(DTC);                           //manually add neuron
        makeConnection(DTC, neurons.get(3));        //plus connection
        neurons.get(2).setBias(0.0);                //set left bias zero
    }
    
    
}
