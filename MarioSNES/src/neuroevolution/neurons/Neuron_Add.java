package neuroevolution.neurons;
public class Neuron_Add extends Neuron{
    public Neuron_Add(){
        super();
    }
    
    public double evaluate(){
        if(getEvaluated())
            return getCache();
        double sum=0.0;
        checkInputs();
        sum+=getBias();
        for(int i=0;i<getInputs().size();i++)
            sum+=getInputs().get(i).calculateValue();
        double activation=checkThreshold(sum);
        setEvaluated(true);
        setCache(activation);
        return activation;
    }

    //public Neuron makeCopy() {
    //    Neuron_Add newneuron = new Neuron_Add();
    //    newneuron.setBias(getBias());
    //    newneuron.setInnovationNum(getInnovationNum());
    //    newneuron.setInputs(getInputs());
    //    newneuron.setOutputs(getOutputs());
    //    return newneuron;
    //}
}