package lokesh.test;

import java.util.ArrayList;
import java.util.Iterator;

public class AddAllInArrayList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<String> al1= new ArrayList<String>();
		
		al1.add("Lokesh");
		al1.add("Pavani");
		
		ArrayList<String> al2= new ArrayList<String>();
		
		al2.add("Naga Dileep");
		al2.add("Sai Karthikeya");
		
		al1.addAll(al2);
		
		Iterator<String> itr = al1.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
	}

}
