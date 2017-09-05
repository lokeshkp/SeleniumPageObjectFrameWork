package lokesh.test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<String, String> map1 = new HashMap<String ,String>();
		
		map1.put("101", "ECM");
		map1.put("102", "BPM");
		map1.put("103", "CDOCS");
		
		System.out.println(map1.entrySet());
		for(Map.Entry m:map1.entrySet()){
			System.out.println(m.getKey()+"-"+m.getValue());			
		}		
	}
}
