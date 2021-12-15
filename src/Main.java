import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dataStructures.Matrix;
import models.NeuralNetwork;

public class Main {
	final static int numOfTrainingSamples = 17011;
	final static int numOfTestingSamples = 4252;
	final static int numOfFeatures = 81;	//without the output!
	
	public static void main(String[] args) {
		double[][] samples = new double[numOfTrainingSamples][numOfFeatures];
		double[][] sampleTargets = new double[numOfTrainingSamples][1];
		
		double[][] testSamples = new double[numOfTestingSamples][numOfFeatures];
		double[][] testSampleTargets = new double[numOfTestingSamples][1];
		List<Double> output;
		
		Matrix inputs = new Matrix(numOfTrainingSamples, numOfFeatures);
		
		Scanner scanner = new Scanner(System.in);
	    
		int i=0;
		while(i<(numOfTrainingSamples + numOfTestingSamples)) {
			String line = scanner.nextLine();
			String[] values = line.split("\t");
			
			if(i<numOfTrainingSamples) {
				for(int j=0; j<numOfFeatures; j++) {
					samples[i][j] = Double.parseDouble(values[j]);
					inputs.addArrayToRow(samples[i], samples[i].length, i);
				}
				sampleTargets[i][0] = Double.parseDouble(values[81]);
				
			}else if((i>=numOfTrainingSamples)&&(i<(numOfTrainingSamples + numOfTestingSamples))){
				for(int j=0; j<numOfFeatures; j++) {
					testSamples[i-numOfTrainingSamples][j] = Double.parseDouble(values[j]);
				}
				testSampleTargets[i-numOfTrainingSamples][0] = Double.parseDouble(values[81]);
				//System.out.print("\n"+testSampleTargets[0][0]); //shows the target temperature of 17013 in excel which corresponds to 17012 in data
			}
			i++;
		}

		scanner.close();
		
		inputs.print();
		
		NeuralNetwork neuralNetwork = new NeuralNetwork(numOfTrainingSamples, numOfFeatures,10);
		
		neuralNetwork.train(inputs, Matrix.fromArray(sampleTargets[0]));
		
		double sum=0;
		for(int j=0; j<testSamples.length;j++) {
			output = neuralNetwork.forwardPass(inputs);
			sum+= Math.pow((output.get(0)-testSampleTargets[j][0]), 2);
			System.out.print(output.get(0)+"\n");
		}
		
		System.out.println();
		System.out.println(Math.sqrt(sum/testSamples.length));
	}

}
