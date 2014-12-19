/**
 *
 * @author Zack and Mark
 * 
 */
package neuroevolution.neurons.hardcoded;
import neuroevolution.neurons.*;
import java.util.*;
import gameState.Level1State;
import entities.*;
import gameState.GameState;
//import gameState.MenuState;

public class VerticalChecker extends InputNeuron{
    private Level1State level;
    private Mario player;
    
    public double evaluate(){
        ArrayList<Block> entities = level.getBlocksOnScreen();
//        if(level instanceof MenuState)
//            return 0.0;
        double mindistance = Double.MAX_VALUE;
        for(GameEntity ent : entities)
        {
            double tempdistance = ent.getRelativeY(player);
            if(ent.getCXpos() + ent.getCWidth()/2 > player.getCXpos() && ent.getCXpos() - ent.getCWidth()/2 < player.getCXpos())
                
                if(mindistance < tempdistance)
                    tempdistance = mindistance;
        }
        return mindistance;
    }
    
    
    public VerticalChecker(GameState ref){
        level = (Level1State)ref;
        player = ref.getPlayer();
    }
    
    //@Override
    //public Neuron makeCopy() {
    //    VerticalChecker newVC = new VerticalChecker(level);
    //    newVC.setInputs(getInputs());
    //    newVC.setOutputs(getOutputs());
    //    newVC.setBias(getBias());
    //    newVC.setInnovationNum(innovationNum);
    //    newVC.setWeight(weight);
    //    
    //    return newVC;
    //}
    
    public Neuron makeCopy(){
        VerticalChecker newVC=new VerticalChecker(level);
        newVC.setBias(getBias());
        newVC.setInnovationNum(innovationNum);
        newVC.setWeight(weight);
        return newVC;
    }

    void setLevel(GameState ref) {
        level = (Level1State)ref;
    }
}
