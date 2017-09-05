package lokesh.test;

import java.util.HashSet;

public class SetExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashSet<CollageStudent> hs = new HashSet<CollageStudent>();
		CollageStudent cs1 = new CollageStudent(1001,100,97,100,297,"Dileep");
		CollageStudent cs2 = new CollageStudent(1001,100,87,100,287,"Karthik");
		
		hs.add(cs1);
		hs.add(cs2);
		
		for(CollageStudent cs:hs){
			System.out.println(cs.studentName+"--"+cs.rollNo+"-"+cs.maths+"-"+cs.physics+"-"+cs.chemistry+"-"+cs.total);
		}
	}

}


class CollageStudent{
	int rollNo, maths, physics,chemistry, total;
	String studentName;
	
	public CollageStudent(int rollNo, int maths, int physics,int chemistry, int total, String studentName){
		this.rollNo=rollNo;
		this.maths=maths;
		this.physics=physics;
		this.chemistry=chemistry;
		this.total=total;
		this.studentName=studentName;
	}

}