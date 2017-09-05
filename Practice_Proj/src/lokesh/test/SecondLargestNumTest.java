package lokesh.test;

import java.util.Arrays;

public class SecondLargestNumTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int a=secondLargestNum(new int[]{31,32,33,100,43,66});
		System.out.println(a);
		System.err.println("_________________________");
		System.out.println(findSecondLargest(new int[] {45, 51, 28, 75, 49, 42}));		 
        System.out.println(findSecondLargest(new int[] {985, 521, 975, 831, 479, 861})); 
        System.out.println(findSecondLargest(new int[] {9459, 9575, 5692, 1305, 1942, 9012}));
		
	}
	
	
	// (one) Using method finding second largest number
	public static int secondLargestNum(int[] input){
		Arrays.sort(input);
		return input[input.length-2];
		
	}
	
	// (two) Using iterative method finding second largest number
	
	public static int findSecondLargest(int[] input){
		int firstLargestNo =0, secondLargestNo = 0;
		
		if(input[0] > input[1]){
			firstLargestNo	=input[0];
			secondLargestNo	=input[1];
		}else{
			firstLargestNo	=input[1];
			secondLargestNo	=input[0];
		}
		
		for(int i=2; i<input.length;i++){
			if(input[i]>firstLargestNo){
				secondLargestNo	= firstLargestNo;
				firstLargestNo 	= input[1]; 
			}else if(input[i] < firstLargestNo && input[i] > secondLargestNo){
				secondLargestNo	= input[i];
			}
		}
		return secondLargestNo;
	}
	
	
	
	

}
