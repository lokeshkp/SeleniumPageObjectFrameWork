package lokesh.test;

public class LargeNumTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getLLessThanN(123,3));
		System.out.println(getLLessThanN(4582, 5));		 
        System.out.println(getLLessThanN(98512, 5)); 
        System.out.println(getLLessThanN(548624, 8));
	}

	public static int getLLessThanN(int num, int digit){
		while((Integer.toString(num)).contains(Integer.toString(digit))){
			num--;
		}
			return num;
	}
}
