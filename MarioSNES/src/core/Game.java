/**
 *
 * @author Zackary Misso
 * 
 */
package core;
import gameState.Level1State;
import javax.swing.JFrame;
import java.util.*;
import neuroevolution.MarioAI;
import neuroevolution.EvolutionaryAlgorithm;
import neuroevolution.networks.*;
import neuroevolution.io.NetReader;
import tilesAndGraphics.ImageCache;
public class Game {
    private GamePanel panel;
    private EvolutionaryAlgorithm evo;
    private ArrayList<HorizontalAndVerticalNetworkHardcoded> templist;  //mark added 21 Sep 14

    public Game(){
        ImageCache.initImages();
        if(GlobalController.gameRunning)
            initNormalMario();
        if(GlobalController.aiRun)
            initMarioAI();
        if(GlobalController.evolving)
            initMarioEvo();
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
        NeuralNetwork net=NetReader.readNetwork("test/"+GlobalController.aiFileName);
        panel=new GamePanel();
        MarioAI agent=new MarioAI();
        agent.createAI(net);
        Level1State state=(Level1State)(panel.getGSM().getGameStates().get(1));
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
        //////////////////ADDED 21 SEP 2014/////////////////////////////////////
        templist = new ArrayList<>();
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
                templist.add(new HorizontalAndVerticalNetworkHardcoded(panel.getGSM().getGameStates().get(0)));
            }
        ////////////////////////////////////////////////////////////////////////
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
}
