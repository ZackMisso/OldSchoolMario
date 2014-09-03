/**
 *
 * @author Zackary Misso
 * 
 */
package neuroevolution.io;
public class NetReader {
    public static NeuralNetwork readNetwork(String fileName){
    	NeuralNetwork net=new NeuralNetwork();
    	net.getNeurons().clear();
    	net.getConnections.clear();
    	net.getInputs.clear();
    	net.getOutputs.clear();
    	try{
    		Scanner scanner=new Scanner(new File(fileName));
    		// implement the rest
    	}catch(IOException e){System.out.println("Could not read file :: NetReader");}
    	return null;
    }
}