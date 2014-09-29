package neuroevolution.networks;
import neuroevolution.Node;
import neuroevolution.neurons.InputNeuron_Add;
import neuroevolution.neurons.OutputNeuron_Add;
import neuroevolution.neurons.InputNeuron;
import neuroevolution.neurons.OutputNeuron;
import neuroevolution.neurons.Neuron;
import neuroevolution.neurons.Neuron_Add;
import neuroevolution.connections.Connection;
//import experiments.Test;
import core.GlobalController;
import neuroevolution.RandomNumberGenerator;
import java.util.ArrayList;
import testtools.CMDTester;
public class NeuralNetwork {
    protected ArrayList<Node> nodes; // reference to all the nodes
    // TODO :: replace the bottom two with the one above
    protected ArrayList<Neuron> neurons;
    protected ArrayList<Connection> connections;
    protected ArrayList<Double> inputs;
    protected ArrayList<Double> outputs;
    protected RandomNumberGenerator rng;
    protected double fitness;
    protected int nodeCnt; // this will be global soon
    
    public NeuralNetwork(){
        this(2,1);
    }
    
    public NeuralNetwork(int ins,int outs){
        neurons=new ArrayList<>();
        nodes=new ArrayList<>();
        connections=new ArrayList<>();
        inputs=new ArrayList<>();
        outputs=new ArrayList<>();
        rng=new RandomNumberGenerator();
        fitness=0.0;
        nodeCnt=0;
        initializeNetwork(ins,outs);
    }
    
    public NeuralNetwork(ArrayList<Neuron> param,ArrayList<Connection> param2){
        neurons=param;
        connections=param2;
        nodes=new ArrayList<>();
        for(int i=0;i<neurons.size();i++)
            nodes.add(neurons.get(i));
        for(int i=0;i<connections.size();i++)
            nodes.add(connections.get(i));
        inputs=new ArrayList<>();
        outputs=new ArrayList<>();
        rng=new RandomNumberGenerator();
        fitness=0.0;
        nodeCnt=0;
    }
    
    private void initializeNetwork(int insnum,int outsnum){
        for(int i=0;i<insnum;i++){
            InputNeuron_Add neuron=new InputNeuron_Add();
            neuron.setInputID(i);
            neuron.setInnovationNum(nodeCnt++);
            neurons.add(neuron);
            nodes.add(neuron);
            // CHANGE
        }
        for(int i=0;i<outsnum;i++){
            OutputNeuron_Add neuron=new OutputNeuron_Add();
            neuron.setOutputID(i);
            neuron.setInnovationNum(nodeCnt++);
            neurons.add(neuron);
            nodes.add(neuron);
            // CHANGE
        }
        ArrayList<InputNeuron> ins=findInputs();
        ArrayList<OutputNeuron> outs=findOutputs();
        for(int i=0;i<ins.size();i++)
            for(int f=0;f<outs.size();f++)
                makeConnection(ins.get(i),outs.get(f));
    }
    
    public int size(){
        int totalConnections=0;
        for(int i=0;i<neurons.size();i++)
            totalConnections+=neurons.get(i).getNumberOfConnections();
        totalConnections/=2;
        return totalConnections+neurons.size()*2;
    }
    
    private int totalConnections(){
        int totalConnections=0;
        for(int i=0;i<neurons.size();i++)
            totalConnections+=neurons.get(i).getNumberOfConnections();
        return totalConnections/2;
    }
    
    // controls the mutation of the neural network
    public void mutate(){
        double chance=rng.simpleDouble();
        if(chance>.8)
            mutateTopology();
        else
            mutateWeights();
    }
    
    // mutates the weights of the neural network
    public void mutateWeights(){
        //int nodeNum=random.nextInt(neurons.size()+connections.size());
        int nodeNum=rng.getInt(neurons.size(),null,false);
        if(nodeNum>=connections.size()){
            // change a random bias
            nodeNum-=connections.size();
            neurons.get(nodeNum).mutateBias(rng);
        }else{
            // change a random connection
            connections.get(nodeNum).mutateWeight(rng);
        }
    }
    
    // mutates the topography of the neural network
    public void mutateTopology(){
        // TODO :: IMPROVE THIS METHOD
        int nodeNum=rng.getInt(neurons.size(),null,false);  //select random index to modify
        Neuron neuron=neurons.get(nodeNum);                 //get associated Neuron object
        double makeRandomConnection=rng.simpleDouble();     //decides if new connection is made
        double newNeuron=rng.simpleDouble();                //decides if new neuron is made
        if(makeRandomConnection>.59)                        
            newRandomConnection(neuron);                    //make connection among unconnecteds
        
        //inputs
        else if(neuron instanceof InputNeuron){
            ArrayList<Connection> outputs=neuron.getOutputs(); //get list of connected outputs
            if(outputs.size()==0){
                mutateTopology();
                return;
            }
            if(newNeuron>.7&&neurons.size()<GlobalController.MAX_NEURONS) //if new neuron condition matched
            {                                                             // and not max # neurons
                addNeuron(outputs,neuron);                                // then add neuron among 
            }                                                             // random connection
            else if(totalConnections()>GlobalController.MIN_CONNECTIONS)
            {
                turnOffConnection(outputs,neuron);                        //otherwise try to turn off a connection
            }
            /*else
            {
                mutateTopology(); // 22 Sep 14 deprecated
                return;
            }*/
        }
        
        //outputs
        else if(neuron instanceof OutputNeuron){
            ArrayList<Connection> inputs=neuron.getInputs();
            if(inputs.size()==0){
                mutateTopology();
                return;
            }
            if(newNeuron>.5&&neurons.size()<GlobalController.MAX_NEURONS)
            {
                addNeuron(inputs,neuron);
            }
            else if(totalConnections()>GlobalController.MIN_CONNECTIONS)
            {
                turnOffConnection(inputs,neuron);
            }
            /*else
            {
                mutateTopology(); 22 Sep 14 deprecated
                return;
            }*/
        }
        // hidden neurons
        else{
            ArrayList<Connection> inputs=neuron.getInputs();
            ArrayList<Connection> outputs=neuron.getOutputs();
            double inorout=rng.simpleDouble();
            if(inorout>.5&&neurons.size()<GlobalController.MAX_NEURONS){
                if(newNeuron>.5){
                    addNeuron(inputs,neuron);
                }else if(totalConnections()>GlobalController.MIN_CONNECTIONS){
                    turnOffConnection(outputs,neuron);
                }else{
                    mutateTopology();
                    return;
                }
            }else{
                if(newNeuron>.5&&neurons.size()<GlobalController.MAX_NEURONS){
                    addNeuron(outputs,neuron);
                }else if(totalConnections()>GlobalController.MIN_CONNECTIONS){
                    turnOffConnection(outputs,neuron);
                }else{
                    mutateTopology();
                    return;
                }
            }
        }
    }
    
    // THIS METHOD IS VERY INEFFICIENT FIX IT LATER TODO
    public void newRandomConnection(Neuron neuron){
        //System.out.println("Making a new Random Connection");
        ArrayList<Neuron> connected=new ArrayList<>();
        for(int i=0;i<neuron.getInputs().size();i++)
            connected.add(neuron.getInputs().get(i).getGiveNeuron());
        for(int i=0;i<neuron.getOutputs().size();i++)
            connected.add(neuron.getOutputs().get(i).getRecieveNeuron());
        connected.add(neuron);
        ArrayList<Integer> indexes=new ArrayList<>();
        for(int i=0;i<neurons.size();i++)
            if(!connected.contains(neurons.get(i)))
                indexes.add(i);
        //if(connected.size()==neurons.size()-1){
        //    System.out.println("This is So Freaking Stupid");
        //    return;
        //}
        //Random random=new Random();
        if(indexes.size()==0){
            //System.out.println("This is causing the error");
            return;
        }
        //int chosenNeuron=random.nextInt(indexes.size());
        int chosenNeuron=rng.getInt(indexes.size(),false);
        //double recogiv=random.nextDouble();
        double recogiv=rng.simpleDouble();
        int otherIndex=indexes.get(chosenNeuron);
        Neuron otherNeuron=neurons.get(otherIndex);
        if(otherNeuron==null){
            //System.out.println("Major Error :: Other Null");
            return;
        }
        if(otherNeuron instanceof InputNeuron&&neuron instanceof InputNeuron){
            mutate();
            //System.out.println("Input-Input");
        }
        else if(otherNeuron instanceof OutputNeuron&&neuron instanceof OutputNeuron){
            mutate();
            //System.out.println("Output-Output");
        }
        else if(otherNeuron instanceof InputNeuron&&neuron instanceof OutputNeuron){
            makeConnection(otherNeuron,neuron);
            //System.out.println("Input-Output");
        }
        else if(otherNeuron instanceof OutputNeuron&&neuron instanceof InputNeuron){
            makeConnection(neuron,otherNeuron);
            //System.out.println("Output-Input");
        }
        else if(otherNeuron instanceof InputNeuron){
            makeConnection(otherNeuron,neuron);
            //System.out.println("Other Input");
        }
        else if(otherNeuron instanceof OutputNeuron){
            makeConnection(neuron,otherNeuron);
            //System.out.println("Other Output");
        }
        else if(neuron instanceof InputNeuron){
            makeConnection(neuron,otherNeuron);
            //System.out.println("This Input");
        }
        else if(neuron instanceof OutputNeuron){
            makeConnection(otherNeuron,neuron);
            //System.out.println("This Output");
        }
        else if(neuron==otherNeuron){
            //System.out.println("ARE YOU FING KIDDING ME");
        }
        else{ // connection between two hidden neurons
            if(recogiv>.5){
                //System.out.println("This Should Not Happen");
                makeConnection(neuron,otherNeuron);
            }else{
                //System.out.println("this should not happen");
                makeConnection(otherNeuron,neuron);
            }
        }
    }

    // creates a new connection between two neurons
    // TODO :: EDIT THIS
    public Connection makeConnection(Neuron give,Neuron recieve){
        //Random random=new Random();
        Connection connection=new Connection();
        connection.setEvaluated(false);
        connection.setActive(true);
        connection.setGiveNeuron(give);
        connection.setRecieveNeuron(recieve);
        give.getOutputs().add(connection);
        recieve.getInputs().add(connection);
        //recieve.getWeights().add(random.nextDouble());
        connection.setInnovationNum(nodeCnt++);
        connection.setWeight(rng.simpleDouble());
        connections.add(connection);
        nodes.add(connection);
        // CHANGE
        return connection;
    }
    
    // adds a neuron in the middle of a connection
    public void addNeuron(ArrayList<Connection> connects,Neuron neuron){
        // THIS SHOULD NEVER RUN
        if(connects.isEmpty()){
            //CMDTester debug = new CMDTester(this);
            throw new IllegalArgumentException("List of connections must be nonempty.");
            //mutate();
            //setFitness(-10000.0);
            //return;
        }
        //int connectionNum=random.nextInt(connects.size());
        int connectionNum=rng.getInt(connects.size(),null,false);
        Connection connection=connects.get(connectionNum);
        Neuron otherNeuron;
        if(connection.getGiveNeuron()==neuron)
            otherNeuron=connection.getRecieveNeuron();
        else
            otherNeuron=connection.getGiveNeuron();
        Neuron newNeuron=new Neuron_Add();
        newNeuron.setInnovationNum(nodeCnt++);
        if(connection.getGiveNeuron()==neuron){
            makeConnection(neuron,newNeuron);
            makeConnection(newNeuron,otherNeuron);
        }else{
            makeConnection(otherNeuron,newNeuron);
            makeConnection(newNeuron,neuron);
        }
        //newNeuron.setInnovationNum(nodeCnt++);
        neurons.add(newNeuron);
        nodes.add(newNeuron);
        // CHANGE
    }
    
    // turns off a connection
    public void turnOffConnection(ArrayList<Connection> connects,Neuron neuron){
        if(connects.isEmpty()){
            mutate();
            return;
        }
        //int connectionNum=random.nextInt(connects.size());
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
        // CHANGE
    }
    
    public void crossOver(NeuralNetwork other){
        // implement
    }
    
    // TODO :: Write a new run method
    public ArrayList<Double> run(){
        ArrayList<OutputNeuron> outputNs=findOutputs();
        //System.out.println(outputNs.size()+" :: Number of found output neurons");
        ArrayList<InputNeuron> inpuNs=findInputs();
        ArrayList<Double> results=new ArrayList<>();
        //for(int i=0;i<param.getInputs().size();i++){
        //    inputs.get(i).setInput(param.getInputs().get(i));
        //}
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
    
    private void nextGeneration(){
        outputs.clear();
        inputs.clear();
        fitness=0.0;
    }

    public NeuralNetwork copy(){
        NeuralNetwork network=new NeuralNetwork();
        network.getNeurons().clear();
        network.getConnections().clear();
        for(int i=0;i<neurons.size();i++)
            network.getNeurons().add(neurons.get(i).makeCopy());
        for(int i=0;i<connections.size();i++)
            network.getConnections().add(connections.get(i).makeCopy(network.getNeurons(),network));
        network.setNodeCnt(nodeCnt);
        network.setRNG(rng);
        return network;
    }
    
    private ArrayList<InputNeuron> findInputs(){
        ArrayList<InputNeuron> ins=new ArrayList<>();
        for(int i=0;i<neurons.size();i++)
            if(neurons.get(i)instanceof InputNeuron)
                ins.add((InputNeuron)neurons.get(i));
        return ins;
    }
    
    private ArrayList<OutputNeuron> findOutputs(){
        ArrayList<OutputNeuron> outs=new ArrayList<>();
        for(int i=0;i<neurons.size();i++)
            if(neurons.get(i)instanceof OutputNeuron)
                outs.add((OutputNeuron)neurons.get(i));
        return outs;
    }
    
    public Neuron getSpecific(int index){
        for(int i=0;i<neurons.size();i++)
            if(neurons.get(i).getInnovationNum()==index)
                return neurons.get(i);
        return null;
    }

    // THESE METHODS ARE USED FOR CMD TESTING
    public Node getNode(int nodeNum){
        for(int i=0;i<neurons.size();i++)
            if(neurons.get(i).getInnovationNum()==nodeNum)
                return neurons.get(i);
        for(int i=0;i<connections.size();i++)
            if(connections.get(i).getInnovationNum()==nodeNum)
                return connections.get(i);
        return null;
    }

    // TODO :: Create a reference to the list of all nodes

    public ArrayList<Node> getAllNodes(){
        ArrayList<Node> nodes=new ArrayList<>();
        for(int i=0;i<neurons.size();i++)
           nodes.add(neurons.get(i));
        for(int i=0;i<connections.size();i++)
            nodes.add(connections.get(i));
        return nodes;
    }
    
    public void reset(){
        for(int i=0;i<neurons.size();i++)
            neurons.get(i).reset();
        for(int i=0;i<connections.size();i++)
            connections.get(i).reset();
    }
    
    public String toString(){
        String data="";
        data+="Total Neurons "+neurons.size()+"\n";
        data+="Total Connections "+totalConnections()+"\n";
        for(int i=0;i<neurons.size();i++){
            data+=neurons.toString();
        }
        return data;
    }
    
    // getter methods
    public ArrayList<Neuron> getNeurons(){return neurons;}
    public ArrayList<Connection> getConnections(){return connections;}
    public ArrayList<Double> getInputs(){return inputs;}
    public ArrayList<Double> getOutputs(){return outputs;}
    public RandomNumberGenerator getRNG(){return rng;}
    public double getFitness(){return fitness;}
    public int getNodeCnt(){return nodeCnt;}
    
    // setter methods
    public void setNeurons(ArrayList<Neuron> param){neurons=param;}
    public void setConnections(ArrayList<Connection> param){connections=param;}
    public void setInputs(ArrayList<Double> param){inputs=param;}
    public void setOutputs(ArrayList<Double> param){outputs=param;}
    public void setRNG(RandomNumberGenerator param){rng=param;}
    public void setFitness(double param){fitness=param;}
    public void setNodeCnt(int param){nodeCnt=param;}
}