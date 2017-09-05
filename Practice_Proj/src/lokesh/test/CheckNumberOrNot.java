package lokesh.test;

import java.util.Scanner;

public class CheckNumberOrNot {

	public static void main(String[] args) {
		System.out.println("Enter Number...");
		Scanner sc= new Scanner(System.in);
		String n = sc.nextLine();
		try{
			if( Integer.parseInt(n)==Integer.valueOf(n)){
				System.out.println(n + " is an Integer");
			}
			else{
				System.out.println(n + "is not an Integer");
			}
		}catch(NumberFormatException exc){
			System.out.println(n + "is NOT an Integer");
		}
		
	}
	
	
}
