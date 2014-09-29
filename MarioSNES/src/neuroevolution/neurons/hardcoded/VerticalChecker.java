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

public class VerticalChecker extends Neuron{
    private GameState level;
    private Mario player;
    
    public double evaluate(){
        ArrayList<GameEntity> entities = level.getAllEntities();
        
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
        level = ref;
        
    }
}
