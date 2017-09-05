package lokesh.test;

import java.util.Scanner;

public class MatrixTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc= new Scanner(System.in);
		
		System.out.println("Enter number of rows..");
		int row =sc.nextInt();
		System.out.println("Enter number of cols..");
		int col	=sc.nextInt();
		
		int[][] matrix = new int[row][col];
		
		System.out.println("Enter Matrix Data...");
		for(int i=0; i< row; i++){
			for(int j=0; j< col; j++){
				matrix[i][j] =  sc.nextInt();
			}
		}
		
		System.out.println("Matrix is...");
		for(int i=0; i< row; i++){
			for(int j=0; j< col; j++){
				System.out.print(matrix[i][j]+"\t");
			}
			System.out.println();
		}

	}

}
