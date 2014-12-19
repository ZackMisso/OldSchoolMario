package neuroevolution.neurons;
public class OutputNeuron_Add extends OutputNeuron{
    public OutputNeuron_Add(){
        super();
    }
    
    public double evaluate(){
        double sum=0.0;
        checkInputs();
        sum+=getBias();
        for(int i=0;i<getInputs().size();i++)
            sum+=getInputs().get(i).calculateValue();
        double activation=checkThreshold(sum);
        setOutput(activation);
        setEvaluated(true);
        setCache(activation);
        return activation;
    }

    //@Override
    //public Neuron makeCopy() {
    //    OutputNeuron_Add newneuron = new OutputNeuron_Add();
    //    newneuron.setBias(getBias());
    //    newneuron.setInnovationNum(getInnovationNum());
    //    newneuron.setOutput(output);
    //    newneuron.setOutputID(outputID);
    //    newneuron.setInputs(getInputs());
    //    return newneuron;
    //}
}