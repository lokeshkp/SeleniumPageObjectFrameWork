package lokesh.test;

import java.util.Arrays;
import java.util.Scanner;

public class ArrangeZerosEndTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Numbers..");
		int no= sc.nextInt();
		int num[]= new int[no];
		
		for(int i=0; i<num.length;i++){
			num[i] = sc.nextInt();
		}
		
		Arrays.sort(num);
		
		for(int j=0;j<no;j++){
			System.err.println(num[j]);
		}
	}

}
