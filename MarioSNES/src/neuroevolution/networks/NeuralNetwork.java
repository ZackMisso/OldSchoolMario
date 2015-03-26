package neuroevolution.networks;
import core.GlobalController;
import datastructures.NodeToNode;
import gameState.GameState;
import java.util.ArrayList;
import java.util.Random;
import neuroevolution.Node;
import neuroevolution.RandomNumberGenerator;
import neuroevolution.connections.Connection;
import neuroevolution.neurons.*;
import neuroevolution.speciation.SpeciationFunctions;
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
    protected GameState level;
    
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
        try{
            initializeNetwork(ins,outs);
        }
        catch(ExceptionInInitializerError e){
            e.getException().printStackTrace();
        }
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
            InputNeuron neuron=new InputNeuron_Add();
            neuron.setInputID(i);
            neuron.setInnovationNum(nodeCnt++);
            neurons.add(neuron);
            nodes.add(neuron);
            // CHANGE
        }
        for(int i=0;i<outsnum;i++){
            OutputNeuron neuron=new OutputNeuron_Add();
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
        // TODO :: IMPROVE THIS METHOD
        int nodeNum=rng.getInt(neurons.size(),null,false);  //select random index to modify
        Neuron neuron=neurons.get(nodeNum);                 //get associated Neuron object
        double makeRandomConnection=rng.simpleDouble();     //decides if new connection is made
        double newNeuron=rng.simpleDouble();                //decides if new neuron is made
        if(makeRandomConnection>.59)                        
            newRandomConnection(neuron);                    //make connection among unconnecteds
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
        }
        // hidden neurons
        else{
            ArrayList<Connection> inputs=neuron.getInputs();
            ArrayList<Connection> outputs=neuron.getOutputs();
            double inorout=rng.simpleDouble();
            if(inorout>.5&&neurons.size()<GlobalController.MAX_NEURONS){
                if(newNeuron>.5){
                    if(inputs.size()==0){
                        mutateTopology();
                        return;}
                    addNeuron(inputs,neuron);
                }else if(totalConnections()>GlobalController.MIN_CONNECTIONS){
                    turnOffConnection(outputs,neuron);
                }else{
                    mutateTopology();
                    return;
                }
            }else{
                if(newNeuron>.5&&neurons.size()<GlobalController.MAX_NEURONS){
                    if(outputs.size()==0){
                        mutateTopology();
                        return;}
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
        System.out.println("Making a new Random Connection");
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
        if(indexes.size()==0)
            return;
        int chosenNeuron=neurons.indexOf(neuron);
        while(chosenNeuron==neurons.indexOf(neuron)){
            chosenNeuron = rng.getInt(indexes.size(),false);
        }
        double recogiv=rng.simpleDouble();
        int otherIndex=indexes.get(chosenNeuron);
        Neuron otherNeuron=neurons.get(otherIndex);
        if(otherNeuron==null)
            return;
        if(otherNeuron instanceof InputNeuron&&neuron instanceof InputNeuron)
            mutate();
        else if(otherNeuron instanceof OutputNeuron&&neuron instanceof OutputNeuron)
            mutate();
        else if(otherNeuron instanceof InputNeuron&&neuron instanceof OutputNeuron)
            makeConnection(otherNeuron,neuron);
        else if(otherNeuron instanceof OutputNeuron&&neuron instanceof InputNeuron)
            makeConnection(neuron,otherNeuron);
        else if(otherNeuron instanceof InputNeuron)
            makeConnection(otherNeuron,neuron);
        else if(otherNeuron instanceof OutputNeuron)
            makeConnection(neuron,otherNeuron);
        else if(neuron instanceof InputNeuron)
            makeConnection(neuron,otherNeuron);
        else if(neuron instanceof OutputNeuron)
            makeConnection(otherNeuron,neuron);
        else if(neuron==otherNeuron){
            //System.out.println("ARE YOU FING KIDDING ME");
        }
        else{ // connection between two hidden neurons
            if(recogiv>.5)
                makeConnection(neuron,otherNeuron);
            else
                makeConnection(otherNeuron,neuron);
        }
    }

    // creates a new connection between two neurons
    // TODO :: EDIT THIS
    public Connection makeConnection(Neuron give,Neuron recieve){
        if(give==null || recieve == null){
            System.out.println("Critical Error:: NeuralNetwork 296");
        }
        Connection connection=new Connection();
        connection.setEvaluated(false);
        connection.setActive(true);
        connection.setGiveNeuron(give);
        connection.setRecieveNeuron(recieve);
        give.getOutputs().add(connection);
        recieve.getInputs().add(connection);
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
        System.out.println("Adding a new neuron");
        if(connects.isEmpty())
            throw new IllegalArgumentException("List of connections must be nonempty.");
        int connectionNum=rng.getInt(connects.size(),null,false);
        Connection connection=connects.get(connectionNum);
        Neuron otherNeuron;
        if(connection.getGiveNeuron()==neuron)
            otherNeuron=connection.getRecieveNeuron();
        else
            otherNeuron=connection.getGiveNeuron();
        if(otherNeuron == null){
            System.out.println("CRITICAL ERROR :: NeuralNetwork 324");
        }
        Neuron newNeuron=new Neuron_Add();
        newNeuron.setInnovationNum(nodeCnt++);
        if(connection.getGiveNeuron()==neuron){
            makeConnection(neuron,newNeuron);
            makeConnection(newNeuron,otherNeuron);
        }else{
            makeConnection(otherNeuron,newNeuron);
            makeConnection(newNeuron,neuron);
        }
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
    
    public NeuralNetwork crossOver(NeuralNetwork other){
        // NOTE :: THIS CROSSOVER REQUIRES SPECIATION
        NeuralNetwork newNetwork;
        ArrayList<NodeToNode> similar;
        if(fitness>other.getFitness())
            similar=SpeciationFunctions.getSimilarNodes(this,other);
        else
            similar=SpeciationFunctions.getSimilarNodes(other,this);
        ArrayList<Node> newNodes=new ArrayList<>();
        boolean hasBetterFitness=fitness>other.getFitness();
        if(hasBetterFitness)
            newNetwork=copy();
        else
            newNetwork=other.copy();
        Random random=new Random();
        for(int i=0;i<similar.size();i++){
            if(similar.get(i).getTwo()!=null){
                if(random.nextDouble()>.78)
                    newNodes.add(similar.get(i).mix(level));
                else{
                    if(hasBetterFitness)
                        newNodes.add(similar.get(i).getOne());
                    else
                        newNodes.add(similar.get(i).getTwo());
                }
            }else
                newNodes.add(similar.get(i).getOne());
        }
        sortingSanity(newNodes);
        for(int i=0;i<newNodes.size();i++){
            boolean chk=false;
            for(int f=0;f<newNetwork.getNeurons().size();f++)
                if(newNetwork.getNeurons().get(f).getInnovationNum()==newNodes.get(i).getInnovationNum()){
                    if(newNodes.get(i)instanceof Connection)
                        System.out.println("WASSSST :: NeuralNetwork");
                    newNetwork.getNeurons().get(f).setBias(((Neuron)(newNodes.get(i))).getBias());
                    chk=true;
                }
            if(!chk)
                for(int f=0;f<newNetwork.getConnections().size();f++)
                    if(newNetwork.getConnections().get(f).getInnovationNum()==newNodes.get(i).getInnovationNum()){
                        if(newNodes.get(i)instanceof Neuron)
                            System.out.println("WAST :: NeuralNetwork");
                        newNetwork.getConnections().get(f).setWeight(((Connection)(newNodes.get(i))).getWeight());
                    }
        }
        newNetwork.setState(level);
        return newNetwork;
    }
    
    private void sortingSanity(ArrayList<Node> nodes){
        boolean chk=false;
        for(int i=0;i<nodes.size();i++){
            if(nodes.get(i)instanceof Neuron&&chk){
                System.out.println("The list is not sorted");
                System.exit(0);
            }
            if(nodes.get(i)instanceof Connection)
                chk=true;
        }
    }
    
    // TODO :: Write a new run method
    public ArrayList<Double> run(){
        for(int i = 0; i<connections.size(); i++){
            if(connections.get(i).getGiveNeuron()==null) System.out.println("Error 1:: NeuralNetwork 368");
            if(connections.get(i).getRecieveNeuron()==null) System.out.println("Error 2:: NeuralNetwork 369");
            if(connections.get(i).getGiveNeuron().equals(connections.get(i).getRecieveNeuron())){
                System.out.println("Recurrent connection found. Removing. :: NeuralNetwork 361");
                connections.remove(connections.get(i));
            }
        }
        ArrayList<OutputNeuron> outputNs=findOutputs();
        ArrayList<InputNeuron> inpuNs=findInputs();
        ArrayList<Double> results=new ArrayList<>();
        for(int i=0;i<inpuNs.size();i++)
            inputs.set(i,1.0); // debuging
        for(int i=0;i<outputNs.size();i++){
            outputNs.get(i).evaluate();
            results.add(outputNs.get(i).getOutput());
        }
        nextGeneration();
        return results;
    }
    
    private void nextGeneration(){
        outputs.clear();
        inputs.clear();
        fitness=0.0;
    }
    
    public NeuralNetwork copyAndMutate(){
        NeuralNetwork network=copy();
        network.mutate();
        return network;
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
        network.setState(level);
        return network;
    }
    
    public ArrayList<InputNeuron> findInputs(){
        ArrayList<InputNeuron> ins=new ArrayList<>();
        for(int i=0;i<neurons.size();i++)
            if(neurons.get(i)instanceof InputNeuron)
                ins.add((InputNeuron)neurons.get(i));
        return ins;
    }
    
    public ArrayList<OutputNeuron> findOutputs(){
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
    
    // a merge sort to sort a list of neural networks by their fitness
    public static <T extends NeuralNetwork> ArrayList sort(ArrayList<T> net){
        if(net.isEmpty())
            return new ArrayList<>();
        if(net.size()==1)
            return net;
        ArrayList<T> one=new ArrayList<>();
        ArrayList<T> two=new ArrayList<>();
        int i;
        for(i=0;i<net.size()/2;i++)
            one.add(net.get(i));
        for(;i<net.size();i++)
            two.add(net.get(i));
        one=sort(one);
        two=sort(two);
        return merge(one,two);
    }
    
    public static <T extends NeuralNetwork> ArrayList merge(ArrayList<T> one,ArrayList<T> two){
        ArrayList<T> merged=new ArrayList<>();
        while(!one.isEmpty()&&!two.isEmpty()){
            if(one.get(0).getFitness()>two.get(0).getFitness())
                merged.add(one.remove(0));
            else
                merged.add(two.remove(0));
        }
        while(!one.isEmpty())
            merged.add(one.remove(0));
        while(!two.isEmpty())
            merged.add(two.remove(0));
        return merged;
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
    public void setState(GameState state){level = state;}
}