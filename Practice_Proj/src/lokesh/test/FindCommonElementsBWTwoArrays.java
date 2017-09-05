package lokesh.test;

import java.util.HashSet;

public class FindCommonElementsBWTwoArrays {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] str1= {"SithaRama","UrmilaLaxmana","RukminiKrishna","ParvathiShiva"};
		String[] str2= {"LaxmiNarayana","ParvathiShiva","SaraswahiBramma","SithaRama"};
		
		HashSet<String> set = new HashSet<String>(); 
		for(int i=0; i< str1.length; i++){
			for(int j=0; j< str2.length; j++){
				if(str1[i].equals(str2[j])){
					set.add(str1[i]);
				}
			}
		}
		System.out.println(set);
	}
}