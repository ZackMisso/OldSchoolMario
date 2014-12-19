package neuroevolution.networks;
//import neuroevolution.Node;
import neuroevolution.neurons.InputNeuron_Add;
import neuroevolution.neurons.OutputNeuron_Add;
import neuroevolution.neurons.InputNeuron;
import neuroevolution.neurons.OutputNeuron;
import neuroevolution.neurons.Neuron;
import neuroevolution.neurons.Neuron_Add;
import neuroevolution.connections.Connection;
import core.GlobalController;
//import neuroevolution.speciation.SpeciationFunctions;
import neuroevolution.speciation.HistoricalTracker;
import testtools.CMDTester;
import neuroevolution.RandomNumberGenerator;
//import datastructures.NodeToNode;
import java.util.ArrayList;
import java.util.Random;
public class SpeciationNeuralNetwork extends NeuralNetwork{
    //private ArrayList<Node> nodes; // reference to all the nodes
    // TODO :: replace the bottom two with the one above
    //private ArrayList<Neuron> neurons;
    //private ArrayList<Connection> connections;
    //private ArrayList<Integer> inputs;
    //private ArrayList<Integer> outputs;
    private HistoricalTracker tracker;
    //private RandomNumberGenerator rng;
    //private double fitness;
    //private int nodeCnt; // this will be global soon
    //private int currentPropogationStep;
    //private boolean depthNotFound;
    private boolean usingSpeciation;
    private boolean networkSorted; // used to save run time
    
    public SpeciationNeuralNetwork(HistoricalTracker param){
        this(param,2,1);
    }
    
    public SpeciationNeuralNetwork(HistoricalTracker param,int ins,int outs){
        super();
        neurons=new ArrayList<>();
        nodes=new ArrayList<>();
        connections=new ArrayList<>();
        inputs=new ArrayList<>();
        inputs.add(0.0);
        outputs=new ArrayList<>();
        tracker=param;
        //System.out.println("RNG was set");
        rng=new RandomNumberGenerator();
        fitness=0.0;
        nodeCnt=0;
        //currentPropogationStep=0;
        //depthNotFound=true;
        usingSpeciation=true; // set to true to test speciation
        initializeNetwork(ins,outs);
    }
    
    public SpeciationNeuralNetwork(ArrayList<Neuron> param,ArrayList<Connection> param2){
        neurons=param;
        connections=param2;
        nodes=new ArrayList<>();
        for(int i=0;i<neurons.size();i++)
            nodes.add(neurons.get(i));
        for(int i=0;i<connections.size();i++)
            nodes.add(connections.get(i));
        inputs=new ArrayList<>();
        outputs=new ArrayList<>();
        //System.out.println("RNG was set");
        rng=new RandomNumberGenerator();
        fitness=0.0;
        nodeCnt=0;
    }
    
    private void initializeNetwork(int insnum,int outsnum){
        int i;
        int f;
        for(i=0;i<insnum;i++){
            InputNeuron_Add neuron=new InputNeuron_Add();
            neuron.setInputID(i);
            if(tracker==null)
                System.out.println("Historical Tracker is null :: NeuralNetwork");
            if(usingSpeciation)
                neuron.setInnovationNum(i);
            else
                neuron.setInnovationNum(nodeCnt++);
            neurons.add(neuron);
            nodes.add(neuron);
        }
        for(f=0;f<outsnum;f++){
            OutputNeuron_Add neuron=new OutputNeuron_Add();
            neuron.setOutputID(f);
            if(usingSpeciation){
                neuron.setInnovationNum(f+i);
                //tracker.setNextInnovation(insnum+outsnum);
            }
            else
                neuron.setInnovationNum(nodeCnt++);
            neurons.add(neuron);
            nodes.add(neuron);
        }
        ArrayList<InputNeuron> ins=findInputs();
        ArrayList<OutputNeuron> outs=findOutputs();
        for(i=0;i<ins.size();i++)
            for(f=0;f<outs.size();f++)
                makeConnection(ins.get(i),outs.get(f),insnum+outsnum+f+i*outs.size());
        //if(tracker!=null){
        //    if(tracker.notSet())
        //        tracker.setNextInnovation(100); // will need to change if we have large networks
        //}
    }
    
    // controls the mutation of the neural network
    public void mutate(){
        double chance=rng.simpleDouble();
        if(chance>.96){
            //System.out.println("Mutating topology :: SNN");
            mutateTopology();
        }else{
            //System.out.println("Mutating weights :: SNN");
            mutateWeights();
        }
    }
    
    // mutates the weights of the neural network
    public void mutateWeights(){
        int nodeNum=rng.getInt(neurons.size(),null,false);
        if(nodeNum>=connections.size()){
            // change a random bias
            nodeNum-=connections.size();
            neurons.get(nodeNum).mutateBias(rng);
        }else
            // change a random connection
            connections.get(nodeNum).mutateWeight(rng);
    }
    
    // mutates the topology of the neural network
    public void mutateTopology(){
        int nodeNum=rng.getInt(neurons.size(),null,false);
        Neuron neuron=neurons.get(nodeNum);
        double makeRandomConnection=rng.simpleDouble();
        double newNeuron=rng.simpleDouble();
        if(makeRandomConnection>.55)
            newRandomConnection(neuron);
        else if(neuron instanceof InputNeuron){
            //System.out.println("Debug :: 1 :: SNN");
            ArrayList<Connection> outputs=neuron.getOutputs();
            if(newNeuron>.8&&neurons.size()<GlobalController.MAX_NEURONS)
                addNeuron(outputs,neuron);
            else if(connections.size()>GlobalController.MIN_CONNECTIONS)
                turnOffConnection(outputs,neuron);
            else
                mutateTopology();
        }
        else if(neuron instanceof OutputNeuron){
            //System.out.println("Debug :: 2 :: SNN");
            ArrayList<Connection> inputs=neuron.getInputs();
            if(newNeuron>.5&&neurons.size()<GlobalController.MAX_NEURONS){
                //System.out.println("ADDING NEURON :: SNN");
                addNeuron(inputs,neuron); // error called from here
            }
            else if(connections.size()>GlobalController.MIN_CONNECTIONS){
                //System.out.println("Turning off connection :: SNN");
                turnOffConnection(inputs,neuron);
            }else
                mutateTopology();
        }else{ // hidden neurons
            //System.out.println("Debug :: 3 :: SNN");
            ArrayList<Connection> inputs=neuron.getInputs();
            ArrayList<Connection> outputs=neuron.getOutputs();
            double inorout=rng.simpleDouble();
            if(inorout>.5&&neurons.size()<GlobalController.MAX_NEURONS){
                if(newNeuron>.5)
                    addNeuron(inputs,neuron);
                else if(connections.size()>GlobalController.MIN_CONNECTIONS)
                    turnOffConnection(outputs,neuron);
                else
                    mutateTopology();
            }else{
                if(newNeuron>.5&&neurons.size()<GlobalController.MAX_NEURONS)
                    addNeuron(outputs,neuron);
                else if(connections.size()>GlobalController.MIN_CONNECTIONS)
                    turnOffConnection(outputs,neuron);
                else
                    mutateTopology();
            }
        }
    }
    
    // new (More efficient new Random Connection method)
    public void newRandomConnection(Neuron neuron){
        //ArrayList<OutputNeuron> outputs=findOutputs();
        //    for(int i=0;i<outputs.size();i++)
        //        outputs.get(i).findDepth();
        try{
            for(int i=0;i<neurons.size();i++)
                neurons.get(i).findDepth();
        }catch(StackOverflowError e){
            System.out.println("There was a stack overflow because of findDepth :: SpeciationNeuralNetwork");
            new CMDTester(this);
            System.exit(0);
        }
        if(neuron instanceof OutputNeuron){
            mutate();
            return;
        }
        neurons=Neuron.sortByDepth(neurons);
        int index=0;
        for(;index<neurons.size()&&neurons.get(index)!=neuron;index++){}
        int maxRan=(new Random()).nextInt(neurons.size()-index);
        if(maxRan==0){
            // recurrent connection to self
            mutate();
            return;
        }
        if(neuron.existsConnection(neurons.get(index+maxRan))){
            // the connection already exists
            mutate();
            return;
        }
        if(neurons.indexOf(neuron) >= index+maxRan){
            System.out.println("ERROR :: SpeciationNeuralNetwork 211");
        }
        makeConnection(neuron,neurons.get(index+maxRan));
    }

    // creates a new connection between two neurons
    public Connection makeConnection(Neuron give,Neuron recieve){
        Connection connection=new Connection();
        connection.setEvaluated(false);
        connection.setActive(true);
        connection.setGiveNeuron(give);
        connection.setRecieveNeuron(recieve);
        give.getOutputs().add(connection);
        recieve.getInputs().add(connection);
        if(usingSpeciation){
            connection.setInitIn(give.getInnovationNum());
            connection.setInitOut(recieve.getInnovationNum());
            tracker.defineConnection(connection);
        }
        else
            connection.setInnovationNum(nodeCnt++);
        if(rng==null)
            System.out.println("Random Number Generator was null!");
        connection.setWeight(rng.simpleDouble());
        connections.add(connection);
        nodes.add(connection);
        return connection;
    }
    
    public Connection makeConnection(Neuron give,Neuron recieve,int num){
        Connection connection=new Connection();
        connection.setEvaluated(false);
        connection.setActive(true);
        connection.setGiveNeuron(give);
        connection.setRecieveNeuron(recieve);
        give.getOutputs().add(connection);
        recieve.getInputs().add(connection);
        if(usingSpeciation){
            connection.setInitIn(give.getInnovationNum());
            connection.setInitOut(recieve.getInnovationNum());
            connection.setInnovationNum(num);
        }
        else
            connection.setInnovationNum(nodeCnt++);
        connection.setWeight(rng.simpleDouble());
        connections.add(connection);
        nodes.add(connection);
        return connection;
    }
    
    // adds a neuron in the middle of a connection
    public void addNeuron(ArrayList<Connection> connects,Neuron neuron){
        // THIS SHOULD NEVER RUN
        //System.out.println("Starting Add Neuron");
        if(connects.isEmpty()){
            mutate();
            //System.out.println("List of connections is empty :: SNN");
            //System.out.println("Nodes: " + nodes.size() + ", Connections: "+connects.size());
            //new CMDTester(this);
            //setFitness(-10.0);
            return;
        }
        int connectionNum=rng.getInt(connects.size(),null,false);
        Connection connection=connects.get(connectionNum);
        //hack to make sure not inserting node into connection to self
        //System.out.println("So Far So Good :: SNN");
        int cnt=0;
        while(connection.getGiveNeuron()==connection.getRecieveNeuron()){
            cnt++;
            connectionNum=rng.getInt(connects.size(),null,false);
            connection=connects.get(connectionNum);
            if(cnt==200)
                new CMDTester(this);
        }
        //System.out.println("Hey Listen :: SNN");
        Neuron otherNeuron=null;
        if(connection.getGiveNeuron()==neuron)
            otherNeuron=connection.getRecieveNeuron();
        else
            otherNeuron=connection.getGiveNeuron();
        Neuron newNeuron=new Neuron_Add();
        //System.out.println("Im too young for this shzz :: SNN");
        if(usingSpeciation){
            newNeuron.setInitIn(neuron.getInnovationNum());
            newNeuron.setInitOut(otherNeuron.getInnovationNum());
            tracker.defineNeuron(newNeuron);
        }
        else
            newNeuron.setInnovationNum(nodeCnt++);
        //System.out.println("You can obviously tell I am tired :: SNN");
        if(connection.getGiveNeuron()==neuron){
            makeConnection(neuron,newNeuron);
            makeConnection(newNeuron,otherNeuron);
        }else{
            makeConnection(otherNeuron,newNeuron);
            makeConnection(newNeuron,neuron);
        }
        neurons.add(newNeuron);
        nodes.add(newNeuron);
        //System.out.println("Fourth wall has been broken :: SNN");
    }
    
    public ArrayList<Double> run(){
        for(int i = 0; i<connections.size(); i++){
            if(connections.get(i).getGiveNeuron()==null) System.out.println("Error 1:: NeuralNetwork 368");
            if(connections.get(i).getRecieveNeuron()==null) System.out.println("Error 2:: NeuralNetwork 369");
            if(connections.get(i).getGiveNeuron().equals(connections.get(i).getRecieveNeuron())){
                //System.out.println("Recurrent connection found. Removing. :: NeuralNetwork 361");
                connections.remove(connections.get(i));
            }
        }
        ArrayList<OutputNeuron> outputNs=findOutputs();
        //System.out.println(outputNs.size()+" :: Number of found output neurons");
        ArrayList<InputNeuron> inpuNs=findInputs();
        if(inpuNs.size()==0){
            System.out.println("there are no input neurons");
            System.exit(0);
        }
        //if(inputs.size()==0){
        //    System.out.println("there are no input neurons :: 2");
        //    System.exit(0);
        //}
        ArrayList<Double> results=new ArrayList<>();
        for(int i=0;i<inpuNs.size();i++){
            //inputs.get(i).setInput(param.getInputs().get(i));
            //inputs.add(i,1.0); // debuging
        }
        //System.out.println("DEBUG");
        for(int i=0;i<outputNs.size();i++){
            outputNs.get(i).evaluate();
            results.add(outputNs.get(i).getOutput());
        }
        //System.out.println(results.size()+" HADHFASDFA");
        //System.out.println(results.toString());
        nextGeneration();
        return results;
        //return null;
    }
    
    // turns off a connection
    public void turnOffConnection(ArrayList<Connection> connects,Neuron neuron){
        if(connects.isEmpty()){
            mutate();
            return;
        }
        int connectionNum=rng.getInt(connects.size(),null,false);
        Connection connection=connects.get(connectionNum);
        Neuron other=null;
        if(connection.getGiveNeuron()==neuron)
            other=connection.getRecieveNeuron();
        else
            other=connection.getGiveNeuron();
        other.removeConnectionWith(neuron);
        connects.remove(connection);
        nodes.remove(connection);
    }
    
    // a sanity check for the find depth method
    private boolean findDepthSanityCheck(){
        ArrayList<InputNeuron> inputs=findInputs();
        ArrayList<OutputNeuron> outputs=findOutputs();
        for(int i=0;i<inputs.size();i++)
            if(inputs.get(i).getDepth()!=0){
                System.out.println("Input depth not right");
                return false;
            }
        for(int i=0;i<outputs.size();i++)
            for(int f=0;f<neurons.size();f++){
                if(neurons.get(f).getDepth()>outputs.get(i).getDepth()&&!outputs.contains(neurons.get(f))){
                    System.out.println("Output Neurons are not the max");
                    return false;
                }
            }
        for(int i=0;i<neurons.size();i++)
            if(neurons.get(i).getDepth()==0&&!(neurons.get(i)instanceof InputNeuron)){
                System.out.println("A Neuron cannot have depth 0");
                return false;
            }
        return true;
    }

    public SpeciationNeuralNetwork copy(){
        SpeciationNeuralNetwork network=new SpeciationNeuralNetwork(tracker);
        network.getNeurons().clear();
        network.getConnections().clear();
        for(int i=0;i<neurons.size();i++)
            network.getNeurons().add(neurons.get(i).makeCopy());
        for(int i=0;i<connections.size();i++)
            network.getConnections().add(connections.get(i).makeCopy(network.getNeurons(),network));
            //    catch(NullPointerException e){new CMDTester(this);}
        network.setNodeCnt(nodeCnt);
        network.setRNG(rng);
        network.setState(level);
        return network;
    }
    
    public SpeciationNeuralNetwork copyAndMutate(){
        //System.out.println("Starting from SpeciationNeuralNetwork :: SNN");
        SpeciationNeuralNetwork network=copy();
        //System.out.println("Copied :: SNN");
        network.mutate();
        //System.out.println("Mutated :: SNN");
        return network;
    }
    
    private void nextGeneration(){
        for(int i=0;i<neurons.size();i++)
            neurons.get(i).deEvaluate();
    }
    
    public Neuron getSpecific(int index){
        for(int i=0;i<neurons.size();i++)
            if(neurons.get(i).getInnovationNum()==index)
                return neurons.get(i);
        return null;
    }
    
    public void reset(){
        for(int i=0;i<neurons.size();i++)
            neurons.get(i).reset();
        for(int i=0;i<connections.size();i++)
            connections.get(i).reset();
    }
    
    // getter methods
    //public ArrayList<Node> getNodes(){return nodes;}
    //public ArrayList<Neuron> getNeurons(){return neurons;}
    //public ArrayList<Connection> getConnections(){return connections;}
    //public ArrayList<Integer> getInputs(){return inputs;}
    //public ArrayList<Integer> outputs(){return outputs;}
    public RandomNumberGenerator getRNG(){return rng;} // needed for CMDTest
    //public double getFitness(){return fitness;}
    //public int getNodeCnt(){return nodeCnt;}
    //public int getCurrentPropogationStep(){return currentPropogationStep;}
    public boolean getNetworkSorted(){return networkSorted;}
    
    // setter methods
    //public void setNeurons(ArrayList<Neuron> param){neurons=param;}
    //public void setConnections(ArrayList<Connection> param){connections=param;}
    //public void setInputs(ArrayList<Integer> param){inputs=param;}
    //public void setOutputs(ArrayList<Integer> param){outputs=param;}
    public void setRNG(RandomNumberGenerator param){rng=param;}
    //public void setFitness(double param){fitness=param;}
    //public void setNodeCnt(int param){nodeCnt=param;}
    public void setNetworkSorted(boolean param){networkSorted=param;}
}