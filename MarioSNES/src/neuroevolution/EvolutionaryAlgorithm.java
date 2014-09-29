package neuroevolution;
import core.GlobalController;
import core.GamePanel;
import entities.Mario;
import neuroevolution.networks.NeuralNetwork;
//import neuroevolution.io.NetReader; // will need later
import neuroevolution.io.NetWriter;
import gameState.Level1State;
import java.util.ArrayList;
import java.util.Random;
public class EvolutionaryAlgorithm {
    private ArrayList<? extends NeuralNetwork> networks; //21 Sep 14 mark changed generic type
    private GamePanel game;
    
    public <T extends NeuralNetwork> EvolutionaryAlgorithm(ArrayList<T> list){
        networks=list;
        //initializeFirstGeneration();
    }
    
    /* DEPRECATED by Mark 21 Sep 14
    private void initializeFirstGeneration(){
        for(int i=0;i<GlobalController.individuals;i++)
            networks.add(new HorizontalAndVerticalNetworkHardcoded((Level1State)game.getGSM().getGameStates().get(0)));
    }
    */
    
    public void runExperiment(){
        System.out.println("Starting Experiment :: EvolutionaryAlgorithm 29");
        NeuralNetwork totalBest=new NeuralNetwork();
        int f;
        for(int i=0;i<GlobalController.generations;i++){
            ArrayList<NeuralNetwork> newList=new ArrayList<>();
            for(f=0;f<networks.size();f++){
                runMarioGame(networks.get(f),i);
                //System.out.println("Individual "+ f +" finished.");
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
            //uncomment the bottom line for single individual generations
            //networks.get(f).mutate();
            //newList.add(networks.get(f).copy());
            // IMPLEMENT CROSSOVER !!!
            networks=newList;
            //System.out.println("Generation "+i+" Over :: Best "+bests.get(bests.size()-1).getFitness());
            System.out.println("Generation " + i + " finished.");
            NetWriter.write(totalBest,"test/Generation"+(i+1)+"Best_"+totalBest.getFitness());
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
        //if(agent==null) System.out.println("ERROR A :: EvolutionaryAlgorithm 67");
        //if(net==null) System.out.println("ERROR B :: EvolutionaryAlgorithm 68");
        //System.out.println("THIS HAS HAPPENED :: EvolutionaryAlgorithm 69");
        state.makeTimer(generation);
        game.controlledRun();
    }
    
    // this is going to be Merge Sort, possibly implement others later
    private <T extends NeuralNetwork> ArrayList<T> sortNetworksByFitness(ArrayList<T> nets){
        //System.out.println("WHYYYYY");
        //System.out.println(nets.size());
        if(nets.size()==1)
            return nets;
        ArrayList<T> one=new ArrayList<>();
        ArrayList<T> two=new ArrayList<>();
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
    private <T extends NeuralNetwork> ArrayList<T> merge(ArrayList<T> one,ArrayList<T> two){
        ArrayList<T> merged=new ArrayList<>();
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
    public <T extends NeuralNetwork> ArrayList<T> getNetworks(){return (ArrayList<T>) networks;}
    
    // setter methods
    public void setNetworks(ArrayList<NeuralNetwork> param){networks=param;}
    public void setGame(GamePanel param){game=param;}
}