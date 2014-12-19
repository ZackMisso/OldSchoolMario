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
import testtools.CMDTester;

public class HVplusPit extends SpeciationNeuralNetwork {
    /* Inheirited fields:
    protected ArrayList<Neuron> neurons;
    protected ArrayList<Connection> connections;
    protected ArrayList<Double> inputs;
    protected ArrayList<Double> outputs;
    protected RandomNumberGenerator rng;
    protected double fitness;
    protected int nodeCnt;
    */
   // private DistanceThresholdChecker DTC;
    private PitChecker PC;
    private VerticalChecker VC;
    private HorizontalChecker HC;
    
    public HVplusPit(HistoricalTracker HT, GameState ref){
        super(HT, 0,3);                                 //Call NN constructor
        //DTC = new DistanceThresholdChecker(ref);    //Initialize input node
        VC = new VerticalChecker(ref);
        HC = new HorizontalChecker(ref);
        PC = new PitChecker(ref);
        Neuron hidden = new Neuron_Add();
        HC.setInnovationNum(3);
        VC.setInnovationNum(4);
        PC.setInnovationNum(5);
        hidden.setInnovationNum(6);
        neurons.add(HC);                           //manually add neuron
        neurons.add(VC);
        neurons.add(PC);
        neurons.add(hidden);
        makeConnection(HC, hidden);        //plus connection
        makeConnection(VC, hidden);
        makeConnection(hidden,neurons.get(2));
        makeConnection(PC,neurons.get(2));
        neurons.get(0).findDepth();
        neurons.get(1).findDepth();
        neurons.get(2).findDepth();
        //new CMDTester(this);
        neurons.get(0).setBias(0.0);                //set left bias zero
        setState(ref);
        PC.setLevel(ref);
    }
    
    public double evalHorizontal(){
        return HC.evaluate();
    }
    
    public double evalVertical(){
        return VC.evaluate();
    }
    
    public double evalPit(){
        return PC.evaluate();
    }
}
