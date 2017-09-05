package lokesh.test;

import java.util.ArrayList;
import java.util.Iterator;

public class UserDefineObjectinArrayList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Student s1 = new Student(101, "VenkataRao", 68);
		Student s2 = new Student(102, "Koteswari", 62);
		
		ArrayList<Student> al = new ArrayList<Student>();
		al.add(s1);
		al.add(s2);
		
		Iterator<Student> itr = al.iterator();
		while(itr.hasNext()){
			Student st = (Student)itr.next();
			System.out.println(st.rollNo+"--"+st.name+"--"+st.age);			
		}
	}

}


class Student{
	int rollNo;
	String name;
	int age;
	
	Student(int rollNo, String name, int age){
		this.rollNo=rollNo;
		this.name=name;
		this.age=age;
	}
}