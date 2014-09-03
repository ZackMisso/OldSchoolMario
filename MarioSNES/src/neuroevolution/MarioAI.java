/**
 *
 * @author Zackary Misso
 * 
 */
package neuroevolution;
public class MarioAI {
    private NeuralNetwork net;
    
    public MarioAI(){
        net=null;
    }
    
    // getter methods
    public NeuralNetwork getNet(){return net;}
    
    // setter methods
    public void setNet(NeuralNetwork param){net=param;}
}
