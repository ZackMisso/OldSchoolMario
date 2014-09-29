package neuroevolution.neurons;
import neuroevolution.Node;
import neuroevolution.connections.Connection;
import neuroevolution.RandomNumberGenerator;
import java.util.ArrayList;
import java.util.Random;
public abstract class Neuron extends Node{
    private ArrayList<Connection> inputs;
    private ArrayList<Connection> outputs;
    private Neuron initInput;
    private Neuron initOutput;
    private double threshold;
    private double bias;
    
    public Neuron(){ // default constructor
        inputs=new ArrayList<>();
        outputs=new ArrayList<>();
        initInput=null;
        initOutput=null;
        setInnovationNum(-100);
        threshold=1.0;
        Random random=new Random();
        double neg=random.nextDouble();
        bias=random.nextDouble();
        if(neg>.5)
            bias*=-1;
    }
    
    // the method that all neurons need to decide if they fire
    public abstract double evaluate();
    
    // checks if the neuron should fire
    public double checkThreshold(double value){
        if(value>=threshold)
            return 1.0;
        return 0.0;
    }
    
    // I honestly dont think this is needed anymore
    public void checkInputs(){
        for(int i=0;i<inputs.size();i++)
            if(!inputs.get(i).getEvaluated()){
                inputs.get(i).calculateValue();
                inputs.get(i).setEvaluated(true);
            }
    }
    
    // sets up the neuron for the next iteration
    public void deEvaluate(){
        for(int i=0;i<inputs.size();i++)
            inputs.get(i).setEvaluated(false);
        for(int i=0;i<outputs.size();i++)
            outputs.get(i).setEvaluated(false);
    }
    
    // return the total number of connections
    public int getNumberOfConnections(){
        return inputs.size()+outputs.size();
    }
    
    // checks if there exists a connection with the other neuron
    public boolean existsConnection(Neuron other){
        for(int i=0;i<outputs.size();i++)
            if(outputs.get(i).getGiveNeuron()==other||outputs.get(i).getRecieveNeuron()==other)
                return true;
        for(int i=0;i<inputs.size();i++)
            if(inputs.get(i).getGiveNeuron()==other||inputs.get(i).getRecieveNeuron()==other)
                return true;
        return false;
    }

    // mutates the bias for this neuron
    public void mutateBias(RandomNumberGenerator rng){
        bias=rng.changeDouble(bias,true);
    }
    
    // returns the connection with the other neuron
    public Connection getConnectionWith(Neuron other){
        for(int i=0;i<outputs.size();i++)
            if(outputs.get(i).getGiveNeuron()==other||outputs.get(i).getRecieveNeuron()==other)
                return outputs.get(i);
        for(int i=0;i<inputs.size();i++)
            if(inputs.get(i).getGiveNeuron()==other||inputs.get(i).getRecieveNeuron()==other)
                return inputs.get(i);
        return null;
    }
    
    // removes the connection with the other neuron
    public void removeConnectionWith(Neuron other){
        Connection connection=getConnectionWith(other);
        if(outputs.contains(connection))
            outputs.remove(connection);
        else
            inputs.remove(connection);
    }
    
    // makes a copy of this neuron
    public Neuron makeCopy(){
        Neuron neuron;
        if(this instanceof InputNeuron){
            InputNeuron_Add newNeuron=new InputNeuron_Add();
            InputNeuron temp=(InputNeuron)this;
            newNeuron.setInputID(temp.getInputID());
            neuron=newNeuron;
        }else if(this instanceof OutputNeuron){
            OutputNeuron_Add newNeuron=new OutputNeuron_Add();
            OutputNeuron temp=(OutputNeuron)this;
            newNeuron.setOutputID(temp.getOutputID());
            neuron=newNeuron;
        }else{
            neuron=new Neuron_Add();
        }
        neuron.setBias(bias);
        neuron.setInnovationNum(getInnovationNum());
        return neuron;
    }
    
    // checks if this is the same neuron (THIS IS WRONG)
    public boolean isSameNeuron(Neuron other){
        boolean outputs=false;
        boolean inputs=false;
        if(initInput==null&&other.getInitInput()!=null)
            return false;
        if(initInput!=null&&other.getInitInput()==null)
            return false;
        if(initOutput==null&&other.getInitOutput()!=null)
            return false;
        if(initOutput!=null&&other.getInitOutput()==null)
            return false;
        if(initInput==null&&other.getInitInput()==null)
            inputs=true;
        if(initOutput==null&&other.getInitOutput()==null)
            outputs=true;
        if(!inputs&&initInput.getInnovationNum()==other.getInitInput().getInnovationNum())
            inputs=true;
        if(!outputs&&initOutput.getInnovationNum()==other.getInitOutput().getInnovationNum())
            outputs=true;
        return outputs&&inputs;
    }
    
    // getter methods
    public ArrayList<Connection> getInputs(){return inputs;}
    public ArrayList<Connection> getOutputs(){return outputs;}
    public Neuron getInitInput(){return initInput;}
    public Neuron getInitOutput(){return initOutput;}
    public double getThreshold(){return threshold;}
    public double getBias(){return bias;}
    
    // setter methods
    public void setInputs(ArrayList<Connection> param){inputs=param;}
    public void setOutputs(ArrayList<Connection> param){outputs=param;}
    public void setInitInput(Neuron param){initInput=param;}
    public void getSetInitOutput(Neuron param){initOutput=param;}
    public void setThreshold(double param){threshold=param;}
    public void setBias(double param){bias=param;}
}