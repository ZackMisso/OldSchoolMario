/**
 *
 * @author Mark
 * 
 */
package neuroevolution.neurons.hardcoded;
import gameState.GameState;
import neuroevolution.neurons.*;
import java.util.Random;

public class DistanceThresholdChecker extends Neuron{
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
    
    @Override
    public double evaluate(){
        return hcweight*hc.evaluate()+vcweight*vc.evaluate() + getBias();
    }
    
    //getter methods
    public double gethcweight(){
        return hcweight;
    }
    
    public double getvcweight(){
        return vcweight;
    }
    
    public double gethcbias(){
        return hc.getBias();
    }
    
    public double getvcbias(){
        return vc.getBias();
    }
    
    public double evalHorizontal(){
        return hc.evaluate();
    }
    
    public double evalVertical(){
        return vc.evaluate();
    }
    
    //setter methods
    public void setHC(HorizontalChecker param){
        hc = param;
    }
    
    public void setVC(VerticalChecker param){
        vc = param;
    }
    
    public void sethcweight(double value){
        hcweight = value;
    }    

    public void setvcweight(double value){
        vcweight = value;
    }
    
    public void setLevel(GameState ref){
        level = ref;
        vc.setLevel(ref);
        hc.setLevel(ref);
    }

    @Override
    public Neuron makeCopy() {
        DistanceThresholdChecker newDTC = new DistanceThresholdChecker(level);
        newDTC.setHC((HorizontalChecker) hc.makeCopy());
        newDTC.setVC((VerticalChecker) vc.makeCopy());
        newDTC.sethcweight(hcweight);
        newDTC.setvcweight(vcweight);
        newDTC.setInnovationNum(innovationNum);
        return newDTC;
    }
}
