package lokesh.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FindCharWordLinesTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BufferedReader reader = null;
		int charCount=0;
		int wordCount=0;
		int lineCount=0;
		
		try{
			reader = new BufferedReader(new FileReader("D:\\eclipse_Workspace\\Practice_Proj\\src\\lokesh\\test\\test.txt"));
			String currentLine = reader.readLine();
			while(currentLine !=null){
				lineCount++;
				String[] words = currentLine.split(" ");				
				wordCount=wordCount+words.length;				
				for(String word:words){
					charCount=charCount+word.length();
				}
				currentLine=reader.readLine();
			}
			
			System.out.println("Number of Charcters--" + charCount);
			System.out.println("Number of Words--" + wordCount);
			System.out.println("Number of Lines--" + lineCount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}

	}

}
