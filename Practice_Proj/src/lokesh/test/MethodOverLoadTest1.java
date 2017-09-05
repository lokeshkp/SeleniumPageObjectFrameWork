package lokesh.test;

public class MethodOverLoadTest1 extends MethodOverLoadTest{
	
	
	public void Test1(String name, int salary){
		this.name=name;
		this.salary=salary;
		System.out.println(name+"-->"+salary);
	}

	public static void main(String[] args){
		
		MethodOverLoadTest obj = new MethodOverLoadTest();
		
		obj.Test1();
		obj.Test1("Kondepudi Lokesh", "150000");
		obj.Test1("02-08-2006", "Causeway");			
	}
	
}
