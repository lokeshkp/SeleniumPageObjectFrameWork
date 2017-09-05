package lokesh.test;

public class CheckStringIsRotationOfAnother
{
	
	public static void main(String[] args){
	
		//String s1= "JavaJ2eeStrutsHibernate";
		//String s2= "StrutsHibernateJavaJ2ee";
		
		String s1="OmeSriramaOmeSitharamaOmeLaxmanaHanuma";
		String s2="OmeLaxmanaHanumaOmeSriramaOmeSitharama";
		if(s1.length() != s2.length()){
			System.out.println("Bother are not rotated strings..");
		}else{
			String s3=s1+s2;
			if(s3.contains(s1)){
				System.out.println("s2 is a rotated version of s1");
			}else{
				System.out.println("s2 is not rotated version of s1");
			}
		}
		
	}
	
}
