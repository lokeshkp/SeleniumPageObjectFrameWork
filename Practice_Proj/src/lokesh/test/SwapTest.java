package lokesh.test;

public class SwapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   int x = 34;
	       int y = 20;
	       System.out.println("Before swap:");
	       System.out.println("x value: "+x);
	       System.out.println("y value: "+y);
	       x = x+y;
	       y=x-y;
	       x=x-y;
	       System.out.println("After swap:");
	       System.out.println("x value: "+x);
	       System.out.println("y value: "+y);
	}
}
