/**
 *
 * @author Zackary Misso
 * 
 */
package neuroevolution.io;
import neuroevolution.networks.NeuralNetwork;
import neuroevolution.neurons.Neuron;
import neuroevolution.neurons.Neuron_Add;
import neuroevolution.neurons.InputNeuron_Add;
import neuroevolution.neurons.OutputNeuron_Add;
import neuroevolution.connections.Connection;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class NetReader {
    public static NeuralNetwork readNetwork(String fileName){
    	NeuralNetwork net=new NeuralNetwork();
    	net.getNeurons().clear();
    	net.getConnections().clear();
    	net.getInputs().clear();
    	net.getOutputs().clear();
    	try{
    		Scanner scanner=new Scanner(new File(fileName));
            while(scanner.hasNextInt()){
                int num=scanner.nextInt();
                if(num==10000001)
                    net.getNeurons().add(readNeuron(scanner));
                if(num==90000009)
                    net.getConnections().add(readConnection(scanner,net.getNeurons()));
            }
    	}catch(IOException e){System.out.println("Could not read file :: NetReader");}
    	return net;
    }
    
    // how a neuron is formatted
    // 10000001
    // innovation num
    // type (0==neuron;1==inputNeuron;2==outputNeuron)
    // bias
    public static Neuron readNeuron(Scanner scanner){
        Neuron neuron;
        int innovation=scanner.nextInt();
        int type=scanner.nextInt();
        if(type==0)
            neuron=new Neuron_Add();
        else if(type==1)
            neuron=new InputNeuron_Add();
        else
            neuron=new OutputNeuron_Add();
        neuron.setInnovationNum(innovation);
        neuron.setBias(scanner.nextDouble());
        return neuron;
    }
    
    // how a connection is formatted
    // 90000009
    // innovation num
    // weight
    // give neuron innovation
    // recieve neuron innovation
    public static Connection readConnection(Scanner scanner,ArrayList<Neuron> neurons){
        Connection connection=new Connection();
        connection.setInnovationNum(scanner.nextInt());
        connection.setWeight(scanner.nextDouble());
        Neuron giveN=null;
        Neuron recieveN=null;
        int giveInn=scanner.nextInt();
        int recieveInn=scanner.nextInt();
        for(int i=0;i<neurons.size();i++){
            if(neurons.get(i).getInnovationNum()==giveInn)
                giveN=neurons.get(i);
            if(neurons.get(i).getInnovationNum()==recieveInn)
                recieveN=neurons.get(i);
        }
        connection.setGiveNeuron(giveN);
        connection.setRecieveNeuron(recieveN);
        giveN.getOutputs().add(connection);
        recieveN.getInputs().add(connection);
        return connection;
    }
}