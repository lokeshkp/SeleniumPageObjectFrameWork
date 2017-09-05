package lokesh.test;

import java.util.ArrayList;
import java.util.Collections;

public class SortingArrayListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> listArray = new ArrayList<String>();
		
		listArray.add("SithaRama");
		listArray.add("LakshmiNarayana");
		listArray.add("BudeviSriDeviSamethaMalayappaSwami");
		listArray.add("AmmaNanna");
		System.out.println(listArray);
		System.out.println();
		Collections.sort(listArray, String.CASE_INSENSITIVE_ORDER);
		System.out.println(listArray);
	}

}
