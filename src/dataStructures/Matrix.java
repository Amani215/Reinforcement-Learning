package dataStructures;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
	private double[][] data;
	private int rows,columns;
	
	public Matrix(int rows,int columns) {
		data = new double[rows][columns];
		
		this.rows = rows;
		this.columns = columns;
		
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<columns; j++)
			{
				data[i][j] = 0;
			}
		}
	}
	
	public void print()
	{
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<columns; j++)
			{
				System.out.print(this.data[i][j]+"	");
			}
			System.out.println();
		}
	}
	
	//returns a vertical matrix
	public static Matrix fromArray(double[] x)
		{
			Matrix temp = new Matrix(x.length, 1);
			
			for(int i =0; i<x.length; i++)
				temp.data[i][0] = x[i];
			
			return temp;
			
		}
		
	public List<Double> toList() {
			List<Double> temp = new ArrayList<Double>()  ;
			
			for(int i=0; i<rows; i++)
			{
				for(int j=0; j<columns; j++)
				{
					temp.add(data[i][j]);
				}
			}
			
			return temp;
		}
		
	public void add(int a)
	{
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++){
				this.data[i][j] += a;
			}
		}
	}
	
	public void add(Matrix m)
	{
		if(columns!=m.columns || rows!=m.rows) {
			System.out.println("The number of rows and columns do not match.");
			return;
		}
		
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<columns; j++)
			{
				this.data[i][j] += m.data[i][j];
			}
		}
	}
	
	public static Matrix subtract(Matrix a, Matrix b) {
		Matrix temp=new Matrix(a.rows, a.columns);
		
		for(int i=0; i<a.rows; i++)
		{
			for(int j=0; j<a.columns; j++)
			{
				temp.data[i][j] = a.data[i][j] - b.data[i][j];
			}
		}
		 
		return temp;
	}

	public static Matrix multiplyWithDotProduct(Matrix a, Matrix b) {
		if(a.columns != b.rows) {
			System.out.print("The number of columns of the firt matrix is not matching the number of rows of the second matrix");
			return a;
		}
		
		Matrix temp=new Matrix(a.rows, b.columns);
		
		for(int i=0; i<temp.rows; i++)
		{
			for(int j=0; j<temp.columns; j++)
			{
				double sum=0;
				for(int k=0; k<a.columns; k++)
				{
					sum += a.data[i][k] * b.data[k][j];
				}
				temp.data[i][j]=sum;
			}
		}
		
		return temp;
	}
	
	public void multiplyElementByElement(Matrix a) {
		for(int i=0; i<a.rows; i++)
		{
			for(int j=0; j<a.columns; j++)
			{
				this.data[i][j] *= a.data[i][j];
			}
		}
		
	}
	
	public void multiply(double a) {
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<columns; j++)
			{
				this.data[i][j] *= a;
			}
		}
		
	}
	
	public static Matrix transpose(Matrix m) {
		Matrix temp=new Matrix(m.columns, m.rows);
		
		for(int i=0; i<m.rows; i++)
		{
			for(int j=0; j<m.columns; j++)
			{
				temp.data[j][i] = m.data[i][j];
			}
		}
		
		return temp;
	}

	//activation function and its derivative
    public void leakyReLU(double alpha) {
    	for(int i=0; i<rows; i++) {
    		for(int j=0; j<columns; j++) {
    			if(data[i][j]<0) {
    				data[i][j] *= alpha;
    			}
    		}
    	}
    }
    
    public Matrix dleakyReLU(double alpha){
    	Matrix temp = new Matrix(rows,columns);
    	
    	for(int i=0; i<rows; i++) {
    		for(int j=0; j<columns; j++) {
    			temp.data[i][j] = data[i][j]<0? alpha : 1;
    		}
    	}
    	
    	return temp;
    }
}
