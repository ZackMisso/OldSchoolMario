/**
 *
 * @author Zackary Misso
 * 
 */
package core;
import gameState.Level1State;
import javax.swing.JFrame;
import neuroevolution.MarioAI;
import neuroevolution.EvolutionaryAlgorithm;
import neuroevolution.networks.NeuralNetwork;
import neuroevolution.io.NetReader;
import tilesAndGraphics.ImageCache;
public class Game {
    private GamePanel panel;
    private EvolutionaryAlgorithm evo;

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
        NeuralNetwork net=new NeuralNetwork(6,3);
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
        evo=new EvolutionaryAlgorithm();
        panel=new GamePanel();
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
