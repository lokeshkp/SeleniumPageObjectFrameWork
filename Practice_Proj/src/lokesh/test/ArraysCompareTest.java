package lokesh.test;

import java.util.Arrays;

public class ArraysCompareTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String [] s1= {"Java", "Selenium", "TestNG","Maven"};		
		String [] s2= {"Java", "Selenium", "TestNG","Maven"};		
		String [] s3= {"Python", "Selenium", "JUnit","Maven"};
		
		System.out.println(Arrays.equals(s1, s2));
		System.out.println(Arrays.equals(s1, s3));
		System.err.println("____________");
		compareTwoArrays();
	}

	public static void compareTwoArrays(){
		int[] ar1 = {5,8,3,7,1,32};
		int[] ar2 = {5,8,3,7,1,32};
		
		boolean equalOrNot =true;
				  
		if(ar1.length == ar2.length){
			for(int i=0; i<ar1.length; i++){
				if(ar1[i]!=ar2[i]){
					equalOrNot=false;
				}
			}
		}else{
			equalOrNot=false;
		}
		if(equalOrNot){
			System.out.println("Two Arrays are Equal");
		}else{
			System.err.println("Two Arrays are Not equal");
		}
		
	}
}
