package neuroevolution;
import core.GlobalController;
import core.GamePanel;
import neuroevolution.networks.NeuralNetwork;
//import neuroevolution.io.NetReader; // will need later
import neuroevolution.io.NetWriter;
import gameState.Level1State;
import java.util.ArrayList;
import java.util.Random;
public class EvolutionaryAlgorithm {
    private ArrayList<NeuralNetwork> networks;
    private GamePanel game;
    
    public EvolutionaryAlgorithm(){
        networks=new ArrayList<>();
        initializeFirstGeneration();
    }
    
    // possibly change this to allow for different set-ups
    private void initializeFirstGeneration(){
        for(int i=0;i<GlobalController.individuals;i++)
            networks.add(new NeuralNetwork(6,3));
    }

    public void runExperiment(){
        System.out.println("Starting Experiment");
        NeuralNetwork totalBest=new NeuralNetwork();
        int f;
        for(int i=0;i<GlobalController.generations;i++){
            ArrayList<NeuralNetwork> newList=new ArrayList<>();
            for(f=0;f<networks.size();f++){
                runMarioGame(networks.get(f),i);
            }
            networks=sortNetworksByFitness(networks);
            if(networks.get(0).getFitness()>totalBest.getFitness())
                totalBest=networks.get(0);
            for(f=0;f<networks.size()/2;f++){
                networks.get(f).mutate();
                newList.add(networks.get(f).copy());
                networks.get(f).mutate();
                newList.add(networks.get(f).copy());
            }
            // uncomment the bottom line for single individual generations
            //networks.get(f).mutate();
            //newList.add(networks.get(f).copy());
            // IMPLEMENT CROSSOVER !!!
            networks=newList;
            //System.out.println("Generation "+i+" Over :: Best "+bests.get(bests.size()-1).getFitness());
            NetWriter.write(totalBest,"test/Generation"+i+"Best_"+totalBest.getFitness());
        }
        NetWriter.write(totalBest,"test/TOTALbestRun_"+totalBest.getFitness());
        System.out.println("The Experiment is done");
        System.exit(0);
    }

    private void runMarioGame(NeuralNetwork net,int generation){
        //System.out.println("Starting Instance of Game");
        MarioAI agent=new MarioAI();
        agent.createAI(net);
        Level1State state=(Level1State)(game.getGSM().getGameStates().get(1));
        state.setANN(agent);
        state.getPlayer().setANN(agent);
        state.makeTimer(generation);
        game.controlledRun();
    }
    
    // this is going to be Merge Sort, possibly implement others later
    private ArrayList<NeuralNetwork> sortNetworksByFitness(ArrayList<NeuralNetwork> nets){
        //System.out.println("WHYYYYY");
        //System.out.println(nets.size());
        if(nets.size()==1)
            return nets;
        ArrayList<NeuralNetwork> one=new ArrayList<>();
        ArrayList<NeuralNetwork> two=new ArrayList<>();
        int i=0;
        for(;i<nets.size()/2;i++)
            one.add(nets.get(i));
        for(;i<nets.size();i++)
            two.add(nets.get(i));
        one=sortNetworksByFitness(one);
        two=sortNetworksByFitness(two);
        return merge(one,two);
    }
    
    // merge for te merge sort
    private ArrayList<NeuralNetwork> merge(ArrayList<NeuralNetwork> one,ArrayList<NeuralNetwork> two){
        ArrayList<NeuralNetwork> merged=new ArrayList<>();
        while(!one.isEmpty()&&!two.isEmpty()){
            if(one.get(0).getFitness()>two.get(0).getFitness())
                merged.add(one.remove(0));
            else if(one.get(0).getFitness()<two.get(0).getFitness())
                merged.add(two.remove(0));
            else{
                double random=(new Random()).nextDouble();
                if(random>.5)
                    merged.add(one.remove(0));
                else
                    merged.add(two.remove(0));
            }
        }
        while(!one.isEmpty())
            merged.add(one.remove(0));
        while(!two.isEmpty())
            merged.add(two.remove(0));
        return merged;
    }
    
    // getter methods
    public ArrayList<NeuralNetwork> getNetworks(){return networks;}
    
    // setter methods
    public void setNetworks(ArrayList<NeuralNetwork> param){networks=param;}
    public void setGame(GamePanel param){game=param;}
}