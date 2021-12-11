package dataStructures;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
	private int rows, columns;
	private double[][] data;
	
	public Matrix(int rows, int columns) throws Exception {
		if(rows<=0 || columns<=0)
			throw new Exception("The number of rows and/or columns is invalid.");
		
		this.rows = rows;
		this.columns = columns;
		data = new double[rows][columns];
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				data[i][j]= 0 ;
			}
		}
	}
	
	public static Matrix fromArray(double[]x) throws Exception
    {
        Matrix temp = new Matrix(x.length, 1);
        
        for(int i =0; i<x.length; i++)
            temp.data[i][0]=x[i];
        
        return temp;
        
    }
    
    public List<Double> toArray() {
        List<Double> temp= new ArrayList<Double>()  ;
        
        for(int i=0; i<rows; i++)
            for(int j=0; j<columns; j++)
                temp.add(data[i][j]);
  
        return temp;
   }
    
	public void add(double x) {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				data[i][j]+= x ;
			}
		}
	}
	
	public void add(Matrix m) {
		if(!m.matches(this))
			return;
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				data[i][j]+= m.data[i][j] ;
			}
		}
	}
	
	public Matrix subtract(Matrix a, Matrix b) {
		if(!a.matches(b))
			return a;
		
		Matrix temp = a;
		
		for(int i=0; i<a.rows; i++) {
			for(int j=0; j<a.columns; j++) {
				temp.data[i][j] = a.data[i][j]-b.data[i][j];
			}
		}
		
		return temp;
	}
	
	public void multiply(double a) {
        for(int i=0;i<rows;i++)
            for(int j=0;j<columns;j++)
                this.data[i][j]*=a;
    }
	
	public static Matrix multiply(Matrix a, Matrix b) throws Exception {
		if(a.columns != b.rows) {
			System.out.print("The number of columns of the firt matrix is not matching the number of rows of the second matrix");
			return a;
		}
		
		Matrix temp = new Matrix(a.rows,b.columns);
		
		for(int i=0;i<temp.rows;i++)
        {
            for(int j=0;j<temp.columns;j++)
            {
                double sum=0;
                for(int k=0; k<a.columns; k++)
                    sum+=a.data[i][k]*b.data[k][j];
                
                temp.data[i][j]=sum;
            }
        }
        return temp;
	}
	
	public Matrix transpose(Matrix m) throws Exception {
		Matrix temp = new Matrix(m.columns,m.rows);
		
		for(int i=0;i<m.rows;i++)
        {
            for(int j=0;j<m.columns;j++)
            {
                temp.data[j][i]=m.data[i][j];
            }
        }
        return temp;
	}

	//activation functon and its derivative
    public void leakyReLU(double alpha) {
    	for(int i=0; i<rows; i++) {
    		for(int j=0; j<columns; j++) {
    			if(data[i][j]<0) {
    				data[i][j] *= alpha;
    			}
    		}
    	}
    }
    
    public Matrix dleakyReLU(double alpha) throws Exception {
    	Matrix temp = new Matrix(rows,columns);
    	
    	for(int i=0; i<rows; i++) {
    		for(int j=0; j<columns; j++) {
    			temp.data[i][j] = data[i][j]<0? alpha : 1;
    		}
    	}
    	
    	return temp;
    }
    
	//checks if the matrices have the same sizes
	private boolean matches(Matrix m) {
		if(m.rows!=this.rows||m.columns!=this.columns) {
			System.out.print("The number of rows and columns do not match.");
			return false;
		}
		return true;
	}
}
