package lokesh.test;

import java.util.HashMap;
import java.util.HashSet;

public class DuplicateElementTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// By comparing two arrays we can achive this
		String [] str = {"abc","xyz","man","she","def","she","def"};
		
		for(int i=0;i<str.length-1;i++){
			for(int j=i+1;j<str.length;j++){
				if(str[i].equals(str[j]) && (i!=j)){
					System.out.println("Following the Elments are Duplicate.. " +str[j]);
				}			
			}
			
			System.out.println(str[i]);
		}
		
		// Using HashSet we can achive this
		
		HashSet<String> strSet = new HashSet<String>();		
		for(String arrayElement: str){
			if(!strSet.add(arrayElement)){
				System.out.println("Duplicate Element is .." + arrayElement);
			}
		}
	}

}
