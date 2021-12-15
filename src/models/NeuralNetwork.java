package models;

import java.util.List;

import dataStructures.Matrix;

public class NeuralNetwork {
	private final double ALPHA = 0.1;	//for the leaky relu
	
	private Matrix weights_ih, Z1, hiddenNodes, weights_ho, Z2, output;	
	private double learningRate = 0.01;
	
	public NeuralNetwork(int numOfSamples, int numOfFeatures, int numOfHiddenNodes) {
		this.weights_ih = new Matrix(numOfFeatures, numOfHiddenNodes);	//81 x 10
		this.Z1 = new Matrix(numOfSamples,numOfHiddenNodes);			//17011 x 10
		this.hiddenNodes = new Matrix(numOfSamples,numOfHiddenNodes);	//17011 x 10
		this.weights_ho = new Matrix(numOfHiddenNodes, 1);				//10 x 1
		this.Z2 = new Matrix(numOfSamples,1);							//17011 x 1
		this.output = new Matrix(numOfSamples,1);						//17011 x 1
	}
	
	public List<Double> forwardPass(Matrix inputMatrix)
	{
		hiddenNodes = computeHiddenValues(inputMatrix);
		output = computeOutputValues(hiddenNodes);
		
		return output.toList();
	}
	
	public void backPropagate(Matrix inputMatrix, Matrix targetMatrix)
	{
		forwardPass(inputMatrix);	//if removed make sure it's called before this function
		
		//RMSE error
		Matrix difference = Matrix.subtract(targetMatrix, output);
		double error = computeError(difference);
		
		//derivative of weighted sum of hidden values
		Matrix Z2_derivative = Z2.dleakyReLU(ALPHA);
		
		//delta_3
		Matrix delta_3 = difference;
		delta_3.multiplyElementByElement(Z2_derivative);
		delta_3.multiply(1/(error*output.getRows()));
		
		//change in the weights is delta_o.out_h
		Matrix hiddenNodes_T = Matrix.transpose(hiddenNodes);
		Matrix who_change =  Matrix.multiplyWithDotProduct(hiddenNodes_T, delta_3);
		
		
		//derivative of weighted sum of inputs
		Matrix Z1_derivative = Z1.dleakyReLU(ALPHA);
		
		//Matrix temp
		Matrix temp = weights_ih;
		temp.multiplyElementByElement(Z1_derivative);
		
		//delta_2
		Matrix delta_2 = delta_3;
		delta_2 = Matrix.multiplyWithDotProduct(delta_2, temp);
		
		//Change in the weights of the features
		Matrix inputMatrix_T = Matrix.transpose(inputMatrix);
		Matrix wih_change = Matrix.multiplyWithDotProduct(inputMatrix_T, delta_2);
		
		//update the weights (between the hidden layer and the output)
		who_change.multiply(learningRate);
		weights_ho.add(who_change);
		
		//update the weights (between the input and the hidden layer)
		wih_change.multiply(learningRate);
		weights_ih.add(wih_change);
	}
	
	public void train(Matrix inputMatrix, Matrix targetMatrix)
	{
		for(int i=0; i<1; i++)
			this.backPropagate(inputMatrix, targetMatrix);
	}
	
	private Matrix computeHiddenValues(Matrix inputMatrix){
		this.Z1 = Matrix.multiplyWithDotProduct(inputMatrix, weights_ih);	//Z1 = (17011x81).(81x10)							
        
		Matrix hidden = Z1;
		hidden.leakyReLU(ALPHA); //H = f(Z1)									
        
        return hidden;	//17011 x 10
	}
	
	private Matrix computeOutputValues(Matrix hidden){
		this.Z2 = Matrix.multiplyWithDotProduct(hidden,weights_ho);	//Z2 = (17011 x 10).(10 x 1)						
		
		Matrix output = Z2;
		output.leakyReLU(ALPHA);	//O = f(Z2)							
        
        return output;	//17011 x 1
	}
	
	private double computeError(Matrix difference) {
		Matrix errorMatrix = difference;
		
		errorMatrix.power(2);
		double sum = 0;
		for(int i=0; i<errorMatrix.getRows(); i++) {
			sum += errorMatrix.getValueAt(i, 0);
		}
		
		return Math.sqrt(sum/output.getRows());
	}
	
}
