/**
 *
 * @author Zackary Misso
 * 
 */
package neuroevolution.io;
import neuroevolution.networks.NeuralNetwork;
import neuroevolution.neurons.Neuron;
import neuroevolution.neurons.InputNeuron;
import neuroevolution.neurons.OutputNeuron;
import neuroevolution.connections.Connection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import neuroevolution.neurons.hardcoded.NeuronType;
public class NetWriter {
    public static void write(NeuralNetwork net,String fileName){
        String data="";
    	for(int i=0;i<net.getNeurons().size();i++)
            data+=writeNeuron(net.getNeurons().get(i));
        for(int i=0;i<net.getConnections().size();i++)
            data+=writeConnection(net.getConnections().get(i));
        try{
            BufferedWriter writer=new BufferedWriter(new FileWriter(fileName));
            writer.write(data);
            writer.close();
        }catch(IOException e){
            System.out.println("Major Error :: IO Error :: WorldWriter");
        }
    }
    
    // how a neuron is formatted
    // 10000001
    // innovation num
    // type (0==neuron;1==inputNeuron;2==outputNeuron)
    // bias
    // num of inputs // depreciated
    // all input innovations // depreciated
    // num of outputs // depreciated
    // all output innovations // depreciated
    public static String writeNeuron(Neuron neuron){
        String data="10000001 ";
        data+=neuron.getInnovationNum()+" ";
        int i = NeuronType.getType(neuron);
        data +=i+" ";
        data +=neuron.getBias()+" ";
        //data+=neuron.getInputs().size()+" ";
        //for(int i=0;i<neuron.getInputs().size();i++)
        //    data+=neuron.getInputs().get(i).getInnovationNum()+" ";
        //data+=neuron.getOutputs().size();
        //for(int i=0;i<neuron.getOutputs().size();i++)
        //    data+=neuron.getOutputs().get(i).getInnovationNum()+" ";
        return data+"\n";
    }
    
    // how a connection is formatted
    // 90000009
    // innovation num
    // weight
    // give neuron innovation
    // recieve neuron innovation
    public static String writeConnection(Connection connection){
        String data="90000009 ";
        data+=connection.getInnovationNum()+" ";
        data+=connection.getWeight()+" ";
        data+=connection.getGiveNeuron().getInnovationNum()+" ";
        data+=connection.getRecieveNeuron().getInnovationNum()+" ";
        return data+"\n";
    }
}