package lokesh.test;

public class PairsOfElementsInArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		findThePairs(new int[]{4, 6, 5, -10, 8, 5, 20},10);		
		System.err.print("---");
		findThePairs(new int[]{4, 6, 2, 6, 8, 10, 11,1},12);
	}

	public static void findThePairs(int inputArray[], int inputNumber){
		
		for(int i=0; i < inputArray.length; i++){
			for(int j=i+1; j< inputArray.length; j++){
				if(inputArray[i] + inputArray[j] == inputNumber){
					System.out.println(inputArray[i] +" + " + inputArray[j]+"=" + inputNumber);
				}
			}			
		}
	}
	
	
}
