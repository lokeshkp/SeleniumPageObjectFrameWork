package lokesh.test;

public class SuperTest {
	
	int su;
	String name;
	
	public SuperTest(){
		System.out.println("Super Constructor Called...");
	}

	public String SuperMethod(int su,String name) {
		// TODO Auto-generated method stub
		System.out.println("Super Class Method Called..");
		this.su=su;
		this.name=name;
		return su+name;
	}

}
