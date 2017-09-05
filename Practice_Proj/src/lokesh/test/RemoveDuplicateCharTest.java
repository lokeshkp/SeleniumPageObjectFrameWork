package lokesh.test;

import java.util.LinkedHashSet;
import java.util.Set;

public class RemoveDuplicateCharTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(removeDuplicateChar("LLLOKKKESSSSHHH"));
		removeDupsSorted();
		System.err.println(getMYCharArray("aaabbbccdddeellllokaskda"));
		
	}
	
	// Using method 1
	
	public static String removeDuplicateChar(String input){
		
		Set<Character> setChar = new LinkedHashSet<Character>();		
		for(char c:input.toCharArray()){
			setChar.add(c);			
		}
		
		StringBuilder sb = new StringBuilder();
		for(Character ch:setChar){
			sb.append(ch);
		}
		return sb.toString();
		
	}	
	
	// Using method 2
	
	static String removeDupsSorted(){
		String str = "CCCAAAUsssEwway";		
		boolean[] found = new boolean[256];
		StringBuilder sb = new StringBuilder();		
		for (char c : str.toCharArray()) {
			if (!found[c]) {
			    found[c] = true;
			    sb.append(c);
			}
		}
		System.out.println("String after duplicates removed : " + sb.toString());
		return str;
    }
	

	// Using method 3
	
	public static String getMYCharArray(String input) {
        char [] chArray=input.toCharArray();
		String myArray = "";
        for(int i = 0; i < chArray.length; i++) {
            if(myArray.indexOf(chArray[i]) == -1) // check if a char already exist, if not exist then return -1
                myArray = myArray+chArray[i];      // add new char
        }
        return myArray;
    }

}
