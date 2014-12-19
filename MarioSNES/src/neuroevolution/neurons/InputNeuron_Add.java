package neuroevolution.neurons;
public class InputNeuron_Add extends InputNeuron{
    public InputNeuron_Add(){
        super();
    }
    
    public double evaluate(){
        if(getEvaluated())
            return getCache();
        double sum=getWeight()*getInput()+getBias();
        double activation=checkThreshold(sum);
        setCache(activation);
        setEvaluated(true);
        return activation;
    }

    //public Neuron makeCopy() {
    //    InputNeuron_Add newneuron = new InputNeuron_Add();
    //    newneuron.setBias(getBias());
    ///    newneuron.setInnovationNum(getInnovationNum());
    //    newneuron.setInput(input);
    //    newneuron.setWeight(weight);
    //    newneuron.setInputID(inputID);
    //    newneuron.setOutputs(getOutputs());
    //    return newneuron;
    //}
}