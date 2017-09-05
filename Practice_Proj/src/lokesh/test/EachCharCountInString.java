package lokesh.test;

import java.util.HashMap;

public class EachCharCountInString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		charCount("Ome Sairama daya chesi naku oka job chupinchu tandri");
	}
	
	public static void charCount(String inputString){
		HashMap<Character, Integer> countMap= new HashMap<Character, Integer>();
		
		char[] charVar = inputString.toLowerCase().toCharArray();
		
		for(char c:charVar){
			if(countMap.containsKey(c)){
				countMap.put(c, countMap.get(c)+1);	
			}else{
				countMap.put(c,1);
			}
		}
		System.out.println(countMap);
	}
}
