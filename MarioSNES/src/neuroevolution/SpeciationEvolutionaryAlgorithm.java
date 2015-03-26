package neuroevolution;
import neuroevolution.networks.NeuralNetwork;
import neuroevolution.networks.SpeciationNeuralNetwork;
import neuroevolution.RandomNumberGenerator;
import neuroevolution.speciation.Species;
import neuroevolution.speciation.HistoricalTracker;
import neuroevolution.io.NetWriter;
import core.GlobalController;
import core.GamePanel;
import gameState.Level1State;
import java.util.ArrayList;
import java.util.Random;
public class SpeciationEvolutionaryAlgorithm {
    private GamePanel game;
    private HistoricalTracker history; // controls the origins of genes
    private RandomNumberGenerator rng; // creates forms of random numbers
    private ArrayList<Species> species; // the current species
    private ArrayList<SpeciationNeuralNetwork> networks; // the current networks (will be removed soon)
    private ArrayList<SpeciationNeuralNetwork> bests; // the list of best networks
    
    public SpeciationEvolutionaryAlgorithm(GamePanel panel){ // default constructor
        history=new HistoricalTracker();
        rng=new RandomNumberGenerator();
        species=new ArrayList<>();
        networks=new ArrayList<>();
        bests=new ArrayList<>();
        setGame(panel);
        initializeFirstGeneration();
    }
    
    // a unit test for the initializeFirstGeneration method
    private void initializeFirstGeneration(){
        species.add(new Species(history));
        species.get(0).setMaxAllowed(GlobalController.individuals);
        species.get(0).initFromStart(game.getGSM().getGameStates().get(0));
    }

    public void runExperiment(){
        SpeciationNeuralNetwork totalBest=new SpeciationNeuralNetwork(history); // best neural network
        for(int i=0;i<GlobalController.generations;i++){ // for each generation
            System.out.println("Starting generation :: "+i);
            // Create temporary lists to hold any new species and deviated nets
            ArrayList<SpeciationNeuralNetwork> deviated=new ArrayList<>();
            ArrayList<Species> newSpecies=new ArrayList<>();
            // for each species
            for(int f=0;f<species.size();f++){
                // run the tests to get each individuals fitness
                for(int g=0;g<species.get(f).getIndividuals().size();g++)
                    runMarioGame(species.get(f).getIndividuals().get(g),i);
                // gets the list of individuals who no longer belong to this species
                ArrayList<SpeciationNeuralNetwork> temp=species.get(f).checkDeviation();
                // add those individuals to the deviated list
                for(int g=0;g<temp.size();g++)
                    deviated.add(temp.get(g));
                // increments the species age
                species.get(f).setAge(species.get(f).getAge()+1);
            }
            // for all of the deviated individuals
            for(int f=0;f<deviated.size();f++){
                boolean foundFit=false;
                // check if the individual belongs in a current species
                for(int g=0;g<species.size()&&!foundFit;g++)
                    if(species.get(g).belongs(deviated.get(f)))
                        foundFit=true;
                // check if the individual belongs in a newly added species
                for(int g=0;g<newSpecies.size()&&!foundFit;g++)
                    if(newSpecies.get(g).belongs(deviated.get(f)))
                        foundFit=true;
                // if not make a new species
                if(!foundFit){
                    Species add=new Species(history,deviated.get(f));
                    add.setSpeciesNum(history.nextSpecies());
                    newSpecies.add(add);
                }
            }
            // add all the new species to the list of all species
            for(int f=0;f<newSpecies.size();f++)
                species.add(newSpecies.get(f));
            double sum=0.0;
            int chk=0;
            // gets the sum of the average fitness of all species
            ArrayList<SpeciationNeuralNetwork> sortedlist = new ArrayList();
            for(int f=0;f<species.size();f++){
                species.get(f).calculateAverageFitness();
                sum+=species.get(f).getAverageFitness();
            }
            // sort species list by average fitness
            Species.sort(species);
            species.get(0).setIndividuals(sortNetworksByFitness(species.get(0).getIndividuals()));
            totalBest = species.get(0).getIndividuals().get(0);
            // sets the allowed population size to a proportion of its average fitness to the sum
            for(int f=0;f<species.size();f++){
                if(species.size()==1){
                    species.get(f).setMaxAllowed(GlobalController.individuals);
                }else{
                    double proportion=species.get(f).getAverageFitness()/sum;
                    species.get(f).setMaxAllowed((int)(proportion*GlobalController.individuals));
                    if(species.get(f).getMaxAllowed()==0)
                        species.get(f).setMaxAllowed(1);
                }
                chk+=species.get(f).getIndividuals().size();
            }
            while(chk<GlobalController.individuals){
                species.get(0).setMaxAllowed(species.get(0).getMaxAllowed()+1);
                chk++;
            }
            // mutates each species
            if(i!=GlobalController.generations-1)
                for(int f=0;f<species.size();f++)
                    species.get(f).mutate();
            // resets the changes in the history tracker
            history.endGeneration();
            if(GlobalController.runningOnWorkstation)
                NetWriter.write(totalBest,"workstationTests/species/Generation"+(i+1)+"Best_"+totalBest.getFitness());
            else
                NetWriter.write(totalBest,"test/species/Generation"+(i+1)+"Best_"+totalBest.getFitness());
            if(i!=GlobalController.generations-1)
                for(int f=0;f<species.size();f++)
                    species.get(f).reset();
            int totalInd=0;
            for(int g=0;g<species.size();g++)
                totalInd+=species.get(g).getIndividuals().size();
            System.out.println("Total Individuals :: "+totalInd);
        }
        species=Species.sort(species);
        System.out.println("Total Species :: "+species.size());
        System.out.println("Best Species Average Fitness :: "+species.get(0).getAverageFitness());
        System.out.println("Worst Species Average Fitness :: "+species.get(species.size()-1).getAverageFitness());
        int totalInd=0;
        for(int i=0;i<species.size();i++)
            totalInd+=species.get(i).getIndividuals().size();
        // Final test (need to implement functionality)
        System.out.println("Total Individuals :: "+totalInd);
        NetWriter.write(totalBest,"test/TOTALbestRun_"+totalBest.getFitness());
    }
    
    private void sortSpeciesByAverageFitness(ArrayList<Species> specieslist){
        boolean sorted = false;
        while(!sorted){
            sorted = true;
            for(int i=0; i<specieslist.size(); i++){
                if(i+1<specieslist.size()&&specieslist.get(i).getAverageFitness()>specieslist.get(i+1).getAverageFitness()){
                    Species temp = specieslist.get(i+1);
                    specieslist.set(i+1,specieslist.get(i));
                    specieslist.set(i,temp);
                    sorted = false;
                }
            }
        }
    }
    
    private void runMarioGame(NeuralNetwork net,int generation){
        GlobalController.running=true;
        MarioAI agent=new MarioAI();
        agent.createAI(net);
        Level1State state=(Level1State)(game.getGSM().getGameStates().get(0));
        state.setANN(agent);
        state.getPlayer().setANN(agent);
        state.makeTimer(generation);
        game.controlledRun();
    }
    
    // MergeSort
    private ArrayList<SpeciationNeuralNetwork> sortNetworksByFitness(ArrayList<SpeciationNeuralNetwork> nets){
        if(nets.size()==1)
            return nets;
        ArrayList<SpeciationNeuralNetwork> one=new ArrayList<>();
        ArrayList<SpeciationNeuralNetwork> two=new ArrayList<>();
        int i=0;
        for(;i<nets.size()/2;i++)
            one.add(nets.get(i));
        for(;i<nets.size();i++)
            two.add(nets.get(i));
        one=sortNetworksByFitness(one);
        two=sortNetworksByFitness(two);
        return merge(one,two);
    }
    
    // merge for the merge sort
    private ArrayList<SpeciationNeuralNetwork> merge(ArrayList<SpeciationNeuralNetwork> one,ArrayList<SpeciationNeuralNetwork> two){
        ArrayList<SpeciationNeuralNetwork> merged=new ArrayList<>();
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
    public ArrayList<SpeciationNeuralNetwork> getNetworks(){return networks;}
    public ArrayList<SpeciationNeuralNetwork> getBests(){return bests;}
    
    // setter methods
    public void setGame(GamePanel param){game=param;}
    public void setNetworks(ArrayList<SpeciationNeuralNetwork> param){networks=param;}
    public void setBests(ArrayList<SpeciationNeuralNetwork> param){bests=param;}
}