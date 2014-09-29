/**
 *
 * @author Zack and Mark
 * 
 */
package neuroevolution.neurons.hardcoded;
import neuroevolution.neurons.*;
import java.util.*;
import gameState.*;
import entities.*;

public class HorizontalChecker extends InputNeuron{
    private GameState level;
    private Mario player;
    
    public double evaluate(){
        ArrayList<GameEntity> entities = level.getAllEntities();
        
        double mindistance = Double.MAX_VALUE;
        for(GameEntity ent : entities)
        {
            double tempdistance = ent.getRelativeX(player);
            if(ent.getCYpos() + ent.getCHeight()/2 > player.getCYpos() && ent.getCYpos() - ent.getCHeight()/2 < player.getCYpos())
                
                if(mindistance < tempdistance)
                    tempdistance = mindistance;
        }
        return mindistance;
    }
    
    
    public HorizontalChecker(GameState ref){
        level = ref;
        player = level.getPlayer();
    }
}
