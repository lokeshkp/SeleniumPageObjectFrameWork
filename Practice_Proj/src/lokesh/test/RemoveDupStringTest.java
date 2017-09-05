package lokesh.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class RemoveDupStringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		findAndRemoveDupElements();
	}
	
	public static void findAndRemoveDupElements(){
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("JAVA");
		arrayList.add("J2EE");
		arrayList.add("JSP");
		arrayList.add("JAVA");
		arrayList.add("J2EE");
		arrayList.add("JAVA");
		arrayList.add("JSP");
		arrayList.add("STRUCTS");
		
		System.out.println(arrayList);
		
		System.err.println("____________________________________________________");
	
		LinkedHashSet<String> setString	= new LinkedHashSet<String>(arrayList);
		
		System.out.println(setString);
		
		ArrayList<String> arrL = new ArrayList<String>(setString);
		
		System.out.println(arrL);
	}

}
