package models;

import java.util.List;

import dataStructures.Matrix;

public class NeuralNetwork {
	final double ALPHA = 0.1;	//for the leaky ReLU
	
	Matrix inputWeights, hiddenWeights, hiddenBiases, outputBiases;
	double learningRate = 0.01;
	
	public NeuralNetwork(int numOfInputs, int numOfHiddenNodes, int numOfOutputs) throws Exception {
		inputWeights = new Matrix(numOfInputs, numOfHiddenNodes);
		hiddenWeights = new Matrix(numOfHiddenNodes, numOfOutputs);
		
		hiddenBiases = new Matrix(1, numOfHiddenNodes);
		outputBiases = new Matrix(1, numOfOutputs);
	}
	
	public List<Double> forwardPass(double[] X) throws Exception
    {
        Matrix input = Matrix.fromArray(X);						//get the input from the given array
        
        Matrix hidden = Matrix.multiply(inputWeights, input);	//multiply inputs by their weights
        hidden.add(hiddenBiases);								//add the biases
        hidden.leakyReLU(ALPHA);								//apply the activation function
        
        Matrix output = Matrix.multiply(hiddenWeights,hidden);	//multiply the result by the hidden weights
        output.add(outputBiases);								//add the biases
        output.leakyReLU(ALPHA);								//apply the activation function to get the final result
        
        return output.toArray();
    }

	public void backPropagate(double [] X,double [] Y) throws Exception
    {
        Matrix input = Matrix.fromArray(X);
        Matrix hidden = Matrix.multiply(inputWeights, input);
        hidden.add(hiddenBiases);
        hidden.leakyReLU(ALPHA);;
        
        Matrix output = Matrix.multiply(hiddenWeights,hidden);
        output.add(outputBiases);
        output.leakyReLU(ALPHA);;
        
        Matrix target = Matrix.fromArray(Y);
        
        Matrix error = Matrix.subtract(target, output);
        Matrix gradient = output.dleakyReLU(ALPHA);
        gradient = Matrix.multiply(gradient, error);
        gradient.multiply(learningRate);
        
        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix who_delta =  Matrix.multiply(gradient, hidden_T);
        
        hiddenWeights.add(who_delta);
        outputBiases.add(gradient);
        
        Matrix who_T = Matrix.transpose(hiddenWeights);
        Matrix hidden_errors = Matrix.multiply(who_T, error);
        
        Matrix h_gradient = hidden.dleakyReLU(ALPHA);
        h_gradient = Matrix.multiply(h_gradient, hidden_errors);
        h_gradient.multiply(learningRate);
        
        Matrix i_T = Matrix.transpose(input);
        Matrix wih_delta = Matrix.multiply(h_gradient, i_T);
        
        inputWeights.add(wih_delta);
        hiddenBiases.add(h_gradient);
        
    }
}
