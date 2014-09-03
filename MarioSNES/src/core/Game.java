/**
 *
 * @author Zackary Misso
 * 
 */
package core;
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
        ImageCache.initImages();
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
    
    public void initMarioAI(){
        NeuralNetwork net=NetReader.readNetwork(GlobalController.aiFileName);
        // implement the rest
    }

    public void initMarioEvo(){
        evo=new EvolutionaryAlgorithm();
        panel=new GamePanel();
        evo.setGame(panel);
        JFrame window=new JFrame("Mario");
        ImageCache.initImages();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        evo.runExperiment();
    }
}
