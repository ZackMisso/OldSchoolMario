/**
 *
 * @author Mark
 * 
 */
package neuroevolution.neurons.hardcoded;
import gameState.GameState;
import neuroevolution.neurons.*;
public final class NeuronType {
    public static int getType(Neuron n){
        if(n instanceof InputNeuron_Add)
            return 1;
        if(n instanceof OutputNeuron_Add)
            return 2;
        if(n instanceof Neuron_Add)
            return 3;
        if(n instanceof DistanceThresholdChecker)
            return 4;
        if(n instanceof PitChecker)
            return 5;
        if(n instanceof HorizontalChecker)
            return 6;
        if(n instanceof VerticalChecker)
            return 7;
        return 3; //default
    }
    
    public static String getName(Neuron n){
        switch(getType(n)){
            case 1: return "Input Neuron :: ";
            case 2: return "Output Neuron :: ";
            case 4: return "DTC :: ";
            case 5: return "PitChecker :: ";
            case 6: return "HorizontalChecker :: ";
            case 7: return "VerticalChecker :: ";
            default: return "Default Neuron :: ";
        }
    }
    
    public static Neuron makeNeuronofType(int type, GameState ref){
        switch(type){
            case 1: return new InputNeuron_Add();
            case 2: return new OutputNeuron_Add();
            case 4: return new DistanceThresholdChecker(ref);
            case 5: return new PitChecker(ref);
            case 6: return new HorizontalChecker(ref);
            case 7: return new VerticalChecker(ref);
            default: return new Neuron_Add();
        }
    }
}
