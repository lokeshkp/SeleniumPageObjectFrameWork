package lokesh.test;

public class ConvertStringToInteger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Convertion of string into integer
		String str="3240";		
		int i=Integer.parseInt(str);
		int j=Integer.valueOf(str);
		System.out.println(i+"--"+j);
		
		// Conversion of integer into string
		
		int no=2017;
		String s=Integer.toString(no);
		System.out.println(s+"-Length ->"+s.length());
		
	}

}
