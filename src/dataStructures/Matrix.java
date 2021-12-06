package dataStructures;

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
	
	public void add(double x) {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				data[i][j]+= x ;
			}
		}
	}
	
	public void add(Matrix m) {
		if(m.rows!=this.rows||m.columns!=this.columns) {
			System.out.print("The number of rows and columns do not match.");
			return;
		}
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				data[i][j]+= m.data[i][j] ;
			}
		}
	}
	
	public Matrix subtract(Matrix a, Matrix b) {
		if(a.rows!=b.rows||a.columns!=b.columns) {
			System.out.print("The number of rows and columns do not match.");
			return a;
		}
		
		Matrix temp = a;
		
		for(int i=0; i<a.rows; i++) {
			for(int j=0; j<a.columns; j++) {
				temp.data[i][j] = a.data[i][j]-b.data[i][j];
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
}
