import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.NeuralNetwork;

public class Main {
	
	public static void main(String[] args) {
		double[][] samples = new double[17011][81];
		double[][] sampleTargets = new double[17011][1];
		
		double[][] testSamples = new double[4252][81];
		double[][] testSampleTargets = new double[4252][1];
		List<Double> output;
		
		Scanner scanner = new Scanner(System.in);
	    
		int i=0;
		while(i<21263) {
			String line = scanner.nextLine();
			String[] values = line.split("\t");
			
			if(i<17011) {
				for(int j=0; j<81; j++) {
					samples[i][j] = Double.parseDouble(values[j]);
				}
				sampleTargets[i][0] = Double.parseDouble(values[81]);
				
			}else if((i>=17011)&&(i<21263)){
				for(int j=0; j<81; j++) {
					testSamples[i-17011][j] = Double.parseDouble(values[j]);
				}
				testSampleTargets[i-17011][0] = Double.parseDouble(values[81]);
				//System.out.print("\n"+testSampleTargets[0][0]); //shows the target temperature of 17013 in excel which corresponds to 17012 in data
			}
			i++;
		}

		scanner.close();
		
		NeuralNetwork neuralNetwork = new NeuralNetwork(81,10,1);
		
		neuralNetwork.train(samples, sampleTargets);
		
		double sum=0;
		for(int j=0; j<testSamples.length;j++) {
			output = neuralNetwork.forwardPass(testSamples[j]);
			sum+= Math.pow((output.get(0)-testSampleTargets[j][0]), 2);
			System.out.print(output.get(0)+"\n");
		}
		
		//System.out.println();
		//System.out.println(Math.sqrt(sum/testSamples.length));
	}

}
