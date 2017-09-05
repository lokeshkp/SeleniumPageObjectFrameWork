package lokesh.test;

public class MethodOverLoadTest {
	
	int salary=100000;
	String name="Lokesh Kondepudi", Company, DOJ;
	
	public MethodOverLoadTest(){
		System.out.println("Plain Constructor Called...");
	}
	
	public MethodOverLoadTest(int a, int b){
		System.out.println("Constructor Called with parameters..."+a+b);		
		return;
	}
	public void Test1(){
		System.out.println("Test Method Called..");
	}
	
	public void Test1(String name, int salary){
		this.name=name;
		this.salary=salary;
		System.out.println(name+"-->"+salary);
	}
	
	public void Test1(String DOJ,String Company){
		this.Company=Company;
		this.DOJ=DOJ;
		System.out.println(Company+"-->"+DOJ);
	}

}

