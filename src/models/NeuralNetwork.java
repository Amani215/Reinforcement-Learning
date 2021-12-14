package models;

import java.util.List;

import dataStructures.Matrix;

public class NeuralNetwork {
	private final double ALPHA = 0.1;	//for the leaky relu
	private Matrix weights_ih, weights_ho, hiddenBiases, outputBiases;	
	private double learningRate = 0.01;
	
	public NeuralNetwork(int numOfInputs,int numOfHiddenNodes,int numOfOutputs) {
		weights_ih = new Matrix(numOfHiddenNodes, numOfInputs); //(2x3)
		weights_ho = new Matrix(numOfOutputs, numOfHiddenNodes);//(1x2)
		
		hiddenBiases = new Matrix(numOfHiddenNodes, 1);
		outputBiases = new Matrix(numOfOutputs, 1);
		
	}
	
	public List<Double> forwardPass(double[] input)
	{
		Matrix inputMatrix = Matrix.fromArray(input);
		
		Matrix hidden = computeHiddenValues(inputMatrix);
		Matrix output = computeOutputValues(hidden);
		
		return output.toList();
	}
	
	public void backPropagate(double [] input, double [] target)
	{
		//Feedforward
		Matrix inputMatrix = Matrix.fromArray(input);
		
		Matrix hidden = computeHiddenValues(inputMatrix);
		Matrix output = computeOutputValues(hidden);
		
		Matrix targetMatrix = Matrix.fromArray(target);
		
		//error derivative
		Matrix derror = Matrix.subtract(output, targetMatrix); //-(target-output) (1x1)
		
		//output derivative
		Matrix gradient_o = output.dleakyReLU(ALPHA); //(1x1)
		
		//delta
		Matrix delta_o = derror;
		delta_o.multiplyElementByElement(gradient_o); //delta = d(error).d(out) (1x1)
		
		//change in the weights is delta_o.out_h
		Matrix hidden_T = Matrix.transpose(hidden); //(1X2)
		Matrix who_change =  Matrix.multiplyWithDotProduct(delta_o, hidden_T); //(1x1)(1x2) = (1x2)
		
		//Calculating error for hidden nodes (taking into account all outputs)
		Matrix who_T = Matrix.transpose(weights_ho); //(2x1)
		Matrix hidden_derrors = Matrix.multiplyWithDotProduct(who_T, delta_o); //delta_o instead of d(error)? (2x1)(1x1) = (2x1)
		
		//derivative of the hidden nodes
		Matrix gradient_h = hidden.dleakyReLU(ALPHA);
		
		//delta of hidden nodes
		Matrix delta_h = hidden_derrors;
		delta_h.multiplyElementByElement(gradient_h);
		
		//change in the weights is delta_h.i
		Matrix i_T = Matrix.transpose(inputMatrix);
		Matrix wih_change = Matrix.multiplyWithDotProduct(delta_h, i_T);
		
		//update the weights (between the hidden layer and the output)
		who_change.multiply(learningRate);
		weights_ho.add(who_change);
		
		//update the biases of outputs
		delta_o.multiply(learningRate);
		outputBiases.add(delta_o);
		
		//update the weights (between the input and the hidden layer)
		wih_change.multiply(learningRate);
		weights_ih.add(wih_change);
		
		//update the biases of the hidden nodes
		delta_h.multiply(learningRate);
		hiddenBiases.add(gradient_h);
		
	}
	
	public void train(double[] input, double[] target)
	{
		//do backpropagation 3 times
		for(int i=0; i<2; i++)
			this.backPropagate(input, target);
	}
	
	private Matrix computeHiddenValues(Matrix inputMatrix){
		Matrix hidden = Matrix.multiplyWithDotProduct(weights_ih, inputMatrix);	//(2x3)(3x1)
        hidden.add(hiddenBiases);									
        hidden.leakyReLU(ALPHA);									
        
        return hidden; //(2x1)
	}
	
	private Matrix computeOutputValues(Matrix hidden){
		Matrix output = Matrix.multiplyWithDotProduct(weights_ho,hidden);
        output.add(outputBiases);								
        output.leakyReLU(ALPHA);								
        
        return output;
	}
	
}
