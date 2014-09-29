/**
 *
 * @author Zack and Mark
 * 
 */
package neuroevolution.neurons.hardcoded;
import gameState.GameState;
import gameState.Level1State;
import neuroevolution.neurons.*;
import java.util.Random;

public class DistanceThresholdChecker extends InputNeuron{
    
    private GameState level;
    private HorizontalChecker hc;
    private VerticalChecker vc;
    private double hcweight;
    private double vcweight;
    
    public DistanceThresholdChecker(GameState ref){
        hc = new HorizontalChecker(ref);
        vc = new VerticalChecker(ref);
        
        level = ref;
        Random rand = new Random();
        hcweight = rand.nextDouble();
        vcweight = rand.nextDouble();
    }
    public double evaluate(){
        // implement
        return hcweight*hc.evaluate()+vcweight*vc.evaluate();
    }
    
    public DistanceThresholdChecker crossover(DistanceThresholdChecker other){
        DistanceThresholdChecker temp = new DistanceThresholdChecker(level);
        
        Random rand = new Random();
        temp.sethcweight((hcweight+other.hcweight)/2+rand.nextDouble()*2.0-1.0);
        temp.setvcweight((vcweight+other.vcweight)/2+rand.nextDouble()*2.0-1.0);
        
        return temp;
    }
    
    //getter methods
    public double gethcweight(){
        return hcweight;
    }
    
    public double getvcweight(){
        return vcweight;
    }
    
    //setter methods
    public void sethcweight(double value){
        hcweight = value;
    }
    
    public void setvcweight(double value){
        vcweight = value;
    }
}
