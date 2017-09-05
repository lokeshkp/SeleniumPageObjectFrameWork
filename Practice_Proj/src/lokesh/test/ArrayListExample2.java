package lokesh.test;

import java.util.ArrayList;
import java.util.List;

public class ArrayListExample2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Book> lst = new ArrayList<Book>();		
		Book b1 = new Book(1001,5,"Java","sairam","BPB");
		Book b2 = new Book(1002,10,"Oracle","Hanuma","Macgra Hill");
		lst.add(b1);
		lst.add(b2);
		
		for(Book b: lst){
			System.out.println(b.id+"-"+b.bookNname+"-"+b.author+"-"+b.quantity+"-"+b.publisher);
		}
	}

}

class Book{
	int id, quantity;
	String bookNname,author,publisher;
	
	public Book(int id, int quantity, String bookNname, String author, String publisher){
		this.id=id;
		this.quantity=quantity;
		this.bookNname=bookNname;
		this.author=author;
		this.publisher=publisher;
	}

	public Book(String name, int price, String author2) {
		// TODO Auto-generated constructor stub
	}
}