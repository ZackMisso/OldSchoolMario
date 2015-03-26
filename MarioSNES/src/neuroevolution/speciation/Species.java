/**
 *
 * @author Zackary Misso
 * 
 */
package neuroevolution.speciation;
import gameState.GameState;
import java.util.ArrayList;
import java.util.Random;
import neuroevolution.networks.HVplusPit;
import neuroevolution.networks.NeuralNetwork;
import neuroevolution.networks.SpeciationNeuralNetwork;
import neuroevolution.neurons.OutputNeuron;
import testtools.CMDTester;
public class Species{
    private ArrayList<SpeciationNeuralNetwork> nets;
    private HistoricalTracker tracker;
    private double averageFitness;
    private int maxAllowed;
    private int speciesNum;
    private int age;
    
    public Species(HistoricalTracker param){
        nets=new ArrayList<>();
        tracker=param;
        averageFitness=0.0;
        maxAllowed=0;
        age=0;
    }
    
    public Species(HistoricalTracker param,SpeciationNeuralNetwork base){
        this(param);
        nets.add(base);
    }
    
    public void mutate(){
        nets=SpeciationNeuralNetwork.sort(nets);
        while(nets.size()<maxAllowed) // add individuals if there are too few
            nets.add(nets.get(0).copyAndMutate());
        while(nets.size()>maxAllowed&&nets.size()>0) // remove individuals if there are too many
            nets.remove(nets.size()-1);
        // mutate every one
        Random random=new Random();
        for(int i=1;i<nets.size();i++){
            ArrayList<OutputNeuron> outputs=nets.get(i).findOutputs();
            for(int f=0;f<outputs.size();f++){
                try{
                    outputs.get(f).findDepth();
                }
                catch(Exception e){
                    new CMDTester(nets.get(i));
                    System.out.println("Encountered a stack overflow due to find depth. Setting this network's fitness low.");
                    nets.get(i).setFitness(Double.MIN_VALUE);
                }
            }
            double mORc=random.nextDouble();
            if(mORc>.8) // possibly make this global
                crossover(nets.get(i),random);
            else
                nets.set(i,nets.get(i).copyAndMutate());
        }
    }
    
    public void crossover(NeuralNetwork net,Random random){
        NeuralNetwork other;
        do{
            other=nets.get(random.nextInt(nets.size()));
            if(nets.size()==1){
                net=net.copyAndMutate();
                return;
            }
        }while(other!=net);
        ArrayList<OutputNeuron> outputs=other.findOutputs();
            for(int i=0;i<outputs.size();i++)
                outputs.get(i).findDepth();
        // calls the neural network's crossover function
        if(other!=null){
            try{
                net=net.crossOver(other);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void initFromStart(GameState level){
        for(int i=0;i<maxAllowed;i++)
            nets.add(new HVplusPit(tracker,level));
    }
    
    public void calculateAverageFitness(){
        double avg=0.0;
        for(int i=0;i<nets.size();i++)
            avg+=nets.get(i).getFitness();
        averageFitness=avg/nets.size();
    }
    
    public boolean shouldDie(){
        return age>40&&nets.size()<=5;
    }
    
    public boolean belongs(SpeciationNeuralNetwork net){
        if(nets.size()==0)
            return false;
        if(SpeciationFunctions.sameSpecies(net,nets.get(0))){
            nets.add(net);
            return true;
        }
        return false;
    }
    
    public void reset(){
        for(int i=0;i<nets.size();i++)
            nets.get(i).setFitness(0);
    }
    
    public ArrayList<SpeciationNeuralNetwork> checkDeviation(){
        ArrayList<SpeciationNeuralNetwork> deviated=new ArrayList<>();
        if(nets.isEmpty())
            return deviated;
        SpeciationNeuralNetwork base=nets.get(0);
        for(int i=1;i<nets.size();i++)
            if(SpeciationFunctions.findNetworkDistance(base,nets.get(i))>SpeciationFunctions.THRESHOLD)
                deviated.add(nets.remove(i));
        return deviated;
    }
    
    public static ArrayList<Species> sort(ArrayList<Species> list){
        if(list.size()==1)
            return list;
        ArrayList<Species> one=new ArrayList<>();
        ArrayList<Species> two=new ArrayList<>();
        int i=0;
        for(;i<list.size()/2;i++)
            one.add(list.get(i));
        for(;i<list.size();i++)
            two.add(list.get(i));
        one=sort(one);
        two=sort(two);
        return merge(one,two);
    }
    
    public static ArrayList<Species> merge(ArrayList<Species> one,ArrayList<Species> two){
        ArrayList<Species> merged=new ArrayList<>();
        while(!one.isEmpty()&&!two.isEmpty()){
            if(one.get(0).getAverageFitness()>two.get(0).getAverageFitness())
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
    
    // getter methods
    public ArrayList<SpeciationNeuralNetwork> getIndividuals(){return nets;}
    public double getAverageFitness(){return averageFitness;}
    public int getSpeciesNum(){return speciesNum;}
    public int getMaxAllowed(){return maxAllowed;}
    public int getAge(){return age;}
    
    // setter methods
    public void setIndividuals(ArrayList<SpeciationNeuralNetwork> ind){nets = ind;}
    public void setMaxAllowed(int param){maxAllowed=param;}
    public void setSpeciesNum(int param){speciesNum=param;}
    public void setAge(int param){age=param;}
}