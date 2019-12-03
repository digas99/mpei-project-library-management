import static java.lang.System.*;
import java.io.IOException;

public class BloomFilterTest {

	public static void main(String[] args) throws IOException{
		int k = 6; // number of hashFunctios
		String[] books = {"Robin Hood", "Crime no Expresso Oriente", "Noddy", "Harry Potter", "Nárnia", "A mensagem", "1984"};
		String[] books2 = {"O recruta", "Anjos e demónios", "Noddy", "Código da Vinci", "Nárnia", "Robin Hood", "Harry Potter"};

		BloomFilter bf = new BloomFilter((int)1e6, k);
		
		System.out.println("Inserting the books in BloomFilter");

		for(String book : books) 
		{
			bf.insert(book);
			System.out.println("Book insert: "+book);
		}
		
		System.out.println("\nCheking the elements of the set books 2");

		for(String book2 : books2) 
		{
			if(bf.isMember(book2)) 
			{
				System.out.println("Book "+ book2 +" may be in the set");
			}
			else 
				System.out.println("Book "+ book2 +" is not in the set");
		}
		
	}
}
