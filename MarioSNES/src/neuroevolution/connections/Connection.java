package neuroevolution.connections;
import neuroevolution.RandomNumberGenerator;
import neuroevolution.networks.NeuralNetwork;
import neuroevolution.Node;
import neuroevolution.neurons.Neuron;
import java.util.ArrayList;
import java.util.Random;
public class Connection extends Node{
    private Neuron giveNeuron;
    private Neuron recieveNeuron;
    private double weight;
    private boolean active;
    
    public Connection(){
        giveNeuron=null;
        recieveNeuron=null;
        Random random=new Random();
        double neg=random.nextDouble();
        weight=random.nextDouble();
        if(neg>.5)
            weight*=-1;
        setInnovationNum(-1);
        active=false;
    }
    /*
    Alternative Connection constructor that specifies input and output nodes at object creation.
    @param source: a reference to the source node of the connection. target: the target node of the connection.
    */
    public Connection(Neuron source, Neuron target){
        this();
        setGiveNeuron(source);
        setRecieveNeuron(target);
    }
    
    public double calculateValue(){
        if(getEvaluated())
            return getCache();
        setCache(giveNeuron.evaluate()*weight);
        setEvaluated(true);
        return getCache();
    }

    public void mutateWeight(RandomNumberGenerator rng){
        weight=rng.changeDouble(weight,true);
    }
    
    public Connection makeCopy(ArrayList<Neuron> neurons,NeuralNetwork net){
        Connection copy=new Connection();
        copy.setInnovationNum(getInnovationNum());
        copy.setWeight(weight);
        copy.setActive(active);
        int giveNeuronNum=giveNeuron.getInnovationNum();
        int recieveNeuronNum=recieveNeuron.getInnovationNum();
        for(int i=0;i<neurons.size();i++){
            if(neurons.get(i).getInnovationNum()==giveNeuronNum)
                copy.setGiveNeuron(neurons.get(i));
            if(neurons.get(i).getInnovationNum()==recieveNeuronNum)
                copy.setRecieveNeuron(neurons.get(i));
        }
        if(copy.getGiveNeuron()==null)
            {
                System.out.println("Error 1 :: Connection starting with "+giveNeuronNum + " in list of :");
                for(Neuron n: neurons)
                    System.out.println(n.getInnovationNum());
            }
        copy.getGiveNeuron().getOutputs().add(copy);
        copy.getRecieveNeuron().getInputs().add(copy);
        return copy;
    }
    
    public boolean isSameConnection(Connection other){
        if(giveNeuron.getInnovationNum()==other.getGiveNeuron().getInnovationNum())
            if(recieveNeuron.getInnovationNum()==other.getRecieveNeuron().getInnovationNum())
                return true;
        return false;
    }

    // This method is used by CMDTester
    public static ArrayList<Integer> getInnovations(ArrayList<Connection> list){
        ArrayList<Integer> nums=new ArrayList<>();
        for(int i=0;i<list.size();i++)
            nums.add(list.get(i).getInnovationNum());
        return nums;
    }
    
    public String toString(){
        String data="Connection\n";
        data+="GiveNeuron :: "+giveNeuron.getInnovationNum()+"\n";
        data+="RecieveNeuron :: "+recieveNeuron.getInnovationNum()+"\n";
        return data;
    }
    
    // getter methods
    public Neuron getGiveNeuron(){return giveNeuron;}
    public Neuron getRecieveNeuron(){return recieveNeuron;}
    public double getWeight(){return weight;}
    public boolean getActive(){return active;}
    
    // setter methods
    public void setGiveNeuron(Neuron param){giveNeuron=param;}
    public void setRecieveNeuron(Neuron param){recieveNeuron=param;}
    public void setWeight(double param){weight=param;}
    public void setActive(boolean param){active=param;}
}