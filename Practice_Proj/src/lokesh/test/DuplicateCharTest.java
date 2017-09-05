package lokesh.test;

import java.util.HashMap;
import java.util.Set;

public class DuplicateCharTest {

	public static void duplicateCharCount(String inpuString){
		HashMap<Character, Integer> charCountMap = new HashMap<Character, Integer>();
		
		for(char c: inpuString.toCharArray()){
			if(charCountMap.containsKey(c)){
				charCountMap.put(c, charCountMap.get(c)+1);
			}else{
				charCountMap.put(c, 1);
			}
		}
		
		Set<Character> charInString = charCountMap.keySet();
		System.out.println("Duplicate Characters In " + inpuString);
		
		for(Character ch: charInString){
			if(charCountMap.get(ch) > 1 ){
				System.out.println(ch+" :"+ charCountMap.get(ch));
			}
		}
	}
	
	public static void main(String[] args){
		duplicateCharCount("Lokesh Kondepudi");
		duplicateCharCount("OME SITHARAMA");
		duplicateCharCount("LAXMANA HANUMA");
	}
}
