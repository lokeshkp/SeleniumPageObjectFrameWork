package lokesh.test;

public class ExceptionsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		try{
			System.out.println(10/0);
		}catch(ArithmeticException e){
			System.out.println(e);
			e.getMessage();
		}finally{
			System.out.println("Finally block executed..");
		}
		
		if(20>10){
			throw new ArithmeticException("not valid");			
		}
		else{
			System.out.println("Correct");
			
		}
		System.out.println(10/2);

	}

}
