package lokesh.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SampleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// String reverse program using reverse method
		StringBuffer str= new StringBuffer("AMMA");
		System.out.println(str.reverse());
		
		System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		// String reverse program using ittrative method
		String str1= "NANNA";
		char[] strArray = str1.toCharArray();
		
		for(int i=strArray.length-1;i>=0;i--){
			System.out.print(strArray[i]);
			
		}
		//System.out.println("");
		// Pyramid Shape programs..		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of Rows..");
		int noOfRows=sc.nextInt();
		int rowCount=1;		
		
		for(int i=noOfRows;i>0;i--){
			
			for(int j=1;j<=i;j++){
				System.out.print(" ");
			}
			
			for(int j=1;j<=rowCount;j++){
				System.out.print(rowCount+ " ");
			}
			 System.out.println();			 
			 rowCount++;
		}
		
		// removing white spaces programs
		String abc = "  Core Java jsp servlets             jdbc struts hibernate spring  ";		
		System.out.println(abc.replaceAll("\\s", ""));
		
		System.err.println("======================================");
		char [] stringArray =abc.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<stringArray.length;i++){
			if((stringArray[i] !=' ') && (stringArray[i] !='\t')){
				sb.append(stringArray[i]);
			}
		}
		System.out.println(sb);
		
		
		// finding duplicate characters program
		findDuplicateChars("This is lokesh kondepudi");
		
	}
	
	// finding duplicate characters program
	public static void findDuplicateChars(String str){
		Map<Character, Integer> dupMap = new HashMap<Character, Integer>();
		char [] chrs = str.toCharArray();
		for(Character ch:chrs){
			if(dupMap.containsKey(ch)){
				dupMap.put(ch, dupMap.get(ch)+1);
			}else{
				dupMap.put(ch, 1);
			}
		}
		
		Set<Character> keys = dupMap.keySet();
		for(Character ch:keys){
			if(dupMap.get(ch)>1){
				System.out.println(ch+"--->"+dupMap.get(ch));
			}
		}
	}

}
