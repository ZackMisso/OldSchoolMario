/**
 *
 * @author Zackary Misso
 * 
 */
package core;
import gameState.Level1State;
import java.util.*;
import javax.swing.JFrame;
import neuroevolution.EvolutionaryAlgorithm;
import neuroevolution.MarioAI;
import neuroevolution.SpeciationEvolutionaryAlgorithm;
import neuroevolution.io.NetReader;
import neuroevolution.networks.*;
import neuroevolution.speciation.HistoricalTracker;
import testtools.CMDTester;
import tilesAndGraphics.ImageCache;
public class Game {
    private GamePanel panel;
    private EvolutionaryAlgorithm evo;
    private SpeciationEvolutionaryAlgorithm specEvo;
    private ArrayList<HVplusPit> templist;  //mark added 21 Sep 14

    public Game(){
        ImageCache.initImages();
        if(GlobalController.gameRunning)
            initNormalMario();
        if(GlobalController.aiRun)
            initMarioAI();
        if(GlobalController.evolving){
            if(GlobalController.speciation)
                initMarioEvoSpecies();
            else
                initMarioEvo();
        }
    }

    public void initNormalMario(){
        JFrame window=new JFrame("Mario");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
    
    public void initMarioAI(){
        //NeuralNetwork net=NetReader.readNetwork(GlobalController.aiFileName);
        //NeuralNetwork net=new NeuralNetwork(6,3);
        panel=new GamePanel();
        Level1State state=(Level1State)(panel.getGSM().getGameStates().get(0));
        HistoricalTracker HT = new HistoricalTracker();
        SpeciationNeuralNetwork net=NetReader.readSpeciationNetwork(HT, "test/"+GlobalController.aiFileName, state);
        //new CMDTester(net);
        MarioAI agent=new MarioAI();
        agent.createAI(net);
        state.setANN(agent);
        state.getPlayer().setANN(agent);
        JFrame window=new JFrame("Mario");
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        // implement the rest
    }

    public void initMarioEvo(){
        panel=new GamePanel();
        templist = new ArrayList<>();
        HistoricalTracker tracker = new HistoricalTracker();
        for(int i=0;i<GlobalController.individuals;i++)
            {
                if(panel == null){
                    System.out.println("Panel is null :: Game");
                }
                if(panel.getGSM() == null){
                    System.out.println("GSM is null :: Game");
                }
                if(panel.getGSM().getGameStates() == null){
                    System.out.println("Panel is null :: Game");
                }
                templist.add(new HVplusPit(tracker,panel.getGSM().getGameStates().get(0)));
            }
        evo=new EvolutionaryAlgorithm(templist);
        evo.setGame(panel);
        JFrame window=new JFrame("Mario");
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        evo.runExperiment();
    }
    
    // evolutionary algorithm using speciation
    public void initMarioEvoSpecies(){
        //System.out.println("Beginning speciation evolution");
        panel=new GamePanel();
        specEvo=new SpeciationEvolutionaryAlgorithm(panel);
        JFrame window=new JFrame("Mario");
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        specEvo.runExperiment();
    }
}
