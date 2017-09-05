package lokesh.test;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ArrayList<String> alist= new ArrayList<String>();
		ArrayList<String> alist= new ArrayList<String>();
		alist.add("Doc_Reg");
		alist.add("MFU");
		alist.add("BRAVA");
		alist.add("BRAVA");
		
		//using while loop
		Iterator<String> itr= alist.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
		System.err.println(" ");
		//using for loop
		for(String st:alist){
			System.err.println(st);
		}
	}

}
