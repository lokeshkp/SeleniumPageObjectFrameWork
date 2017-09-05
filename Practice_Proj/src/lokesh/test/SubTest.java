package lokesh.test;

public class SubTest extends SuperTest{

	public void abc(){
		String ab= super.SuperMethod(10,"Lokesh");
		System.out.println(ab);
	}
		
	public static void main(String[] args){	
		SubTest a = new SubTest();
		a.abc();
		System.out.println("Sub Class Method Called...");
		
	}

}
