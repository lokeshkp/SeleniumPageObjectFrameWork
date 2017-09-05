package lokesh.test;

public class SumofAllDigitsTest {
	
	public static void sumAllDigits(int number){
		int sum=0;
		String strNumber= Integer.toString(number);		
		char[] charArray= strNumber.toCharArray();
		
		for(char c: charArray){
			sum=sum+Character.getNumericValue(c);
		}
		System.out.println(sum);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sumAllDigits(47862);
		sumAllDigits(418622);
		sumAllDigits(5674283);
		sumAllDigits(475496215);
	}

}
