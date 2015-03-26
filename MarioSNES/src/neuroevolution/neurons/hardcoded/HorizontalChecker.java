/**
 *
 * @author Mark
 * 
 */
package neuroevolution.neurons.hardcoded;
import neuroevolution.neurons.*;
import java.util.*;
import gameState.*;
import entities.*;

public class HorizontalChecker extends InputNeuron{
    private Level1State level;
    private Mario player;
    
    @Override
    public double evaluate(){
        ArrayList<Block> entities = level.getBlocksOnScreen();
        double mindistance = Double.MAX_VALUE;
        for(GameEntity ent : entities)
        {
            double tempdistance = ent.getRelativeX(player);
            if(ent.getCYpos() + ent.getCHeight()/2 > player.getCYpos() && ent.getCYpos() - ent.getCHeight()/2 < player.getCYpos())
                if(mindistance < tempdistance)
                    tempdistance = mindistance;
        }
        return mindistance*weight+getBias();
    }
    
    
    public HorizontalChecker(GameState ref){
        level = (Level1State)ref;
        player = level.getPlayer();
    }
    
    public Neuron makeCopy(){
        HorizontalChecker newHC = new HorizontalChecker(level);
        newHC.setBias(getBias());
        newHC.setInnovationNum(innovationNum);
        newHC.setWeight(weight);
        return newHC;
    }

    void setLevel(GameState ref) {
        level = (Level1State)ref;
    }
}
