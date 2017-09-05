package lokesh.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class HashMapToArrayListExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMap<String, String> hmap = new HashMap<String, String>();
		
		hmap.put("E0001", "Lokesh");
		hmap.put("E0002", "Pavani");
		hmap.put("E0003", "Dileep");
		hmap.put("E0004", "SaiKarthikeya");
		hmap.put("E0005", "Eswari");
		hmap.put("E0006", "JanakiRamayya");
		hmap.put("E0007", "Sunitha");
		hmap.put("E0008", "Anwitha");
		hmap.put("E0009", "Charan");
		
		// Getting key set
		Set<String> empKeys = hmap.keySet();		
		ArrayList<String> listKeys = new ArrayList<String>(empKeys);
		for(String key:listKeys){
			System.out.println(key);
		}
		
		//getting value set
		Collection<String> empValues = hmap.values();
		ArrayList<String> listValue = new ArrayList<String>(empValues);
		for(String value:listValue){
			System.out.println(value);
		}
		
		//getting key value set		
		Set<Entry<String,String>> empSet = hmap.entrySet();
		ArrayList<Entry<String, String>> listOfAll = new ArrayList<Entry<String,String>>(empSet);
		for(Entry<String, String> entry:listOfAll){
			System.out.println(entry.getKey()+"--"+entry.getValue());
		}

	}

}
