/**
 *
 * @author Mark
 * 
 */
package neuroevolution.neurons.hardcoded;
import entities.Block;
import entities.Mario;
import gameState.GameState;
import gameState.Level1State;
import java.util.ArrayList;
import neuroevolution.neurons.InputNeuron;
import neuroevolution.neurons.Neuron;
public class PitChecker extends InputNeuron{
    private Level1State level;
    private Mario player;
    
    public PitChecker(GameState ref){
        level = (Level1State)ref;
        player = ref.getPlayer();
    }
    
    public double evaluate(){ //returns distance to closest pit to the right if facing right and sim. left
        if(player.getFalling())
            return 0;
        ArrayList<Block> blocklist = level.getBlocksOnScreen();
        double width = 1;
        double mariobot = player.getYpos()+player.getHeight()/2;
        int direction = 0;
        double xoffset = 0;
        outer: for( ; ; xoffset += width*Math.pow(-1,direction)){
            for(Block b: blocklist){
                if(b.getCYpos()<mariobot+16 && b.getCYpos()>mariobot){
                    if(b.getXpos() - player.getXpos() <= xoffset && b.getXpos() - player.getXpos() + (double)b.getWidth() >= xoffset)
                        continue outer;
                }
            }
            if(xoffset == 0)
                return 0;
            return 1/xoffset + getBias();
        }
    }

    public Neuron makeCopy() {
        PitChecker temp = new PitChecker(level);
        temp.setInnovationNum(innovationNum);
        temp.setBias(getBias());
        return temp;
    }

    public void setLevel(GameState ref) {
        level = (Level1State)ref;
    }
}
