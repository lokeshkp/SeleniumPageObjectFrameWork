package lokesh.test;

public class ReverseEachString {
	
	public static void findEachWordStrig(String inputString){
		String[] words= inputString.split(" ");
		String reverseString = "";
		
		for(int i=0;i<words.length;i++){
			String word =words[i];
			String reverseWord = "";
			for(int j=word.length()-1;j>=0;j--){
				reverseWord=reverseWord+word.charAt(j);
			}
			reverseString=reverseString+reverseWord+" ";
		}
		
		System.out.println(inputString);
		System.err.println("____________________________");
		System.out.println(reverseString);
		
	}
	
	public static void main(String[] args){
		findEachWordStrig("Java Concept Of The Day");
	    
	}

}
