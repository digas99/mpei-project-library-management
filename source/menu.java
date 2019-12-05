import static java.lang.System.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class menu
{
	static String libName;
	static BloomFilter bm = new BloomFilter((int)1e6, 6);
	static Library lib;
	static Scanner in = new Scanner(System.in);
	static Hash[] listHash;
    static MinHash minHash;
    static int nmrCharsPerShingle = 3; 
	static int nmrHashes = 225;
	static List<Book> listOfBooks;

	public static void display_menu(String[] opts, boolean clear) 
	{	
		if (clear)
			clearScreen();
		out.println(libName);
    	for (int i=0; i<opts.length; i++) 
    	{
        	out.println("["+(i+1)+"] - "+opts[i]);
    	}

		out.println("[0] - Anterior");
		out.print("Selecione uma opção: ");
	}

	public static void main(String[] args) throws IOException
	{
		String[] menu1 = {"Listar Livros", "Pesquisar Livros", "Pesquisar autores", "Admistração"};
		String[] listar = {"Listar todos os livros", "Listar livros por categoria"};
		String[] pesquisar = {"Verificar existncia de livro", "Listar livros com titulos parecidos"};
		String[] pesquisar1 = {"Verificar se há livros do autor", "Listar livros do autor"};
		String[] admin = {"Adicionar livro", "Remover livro", "Requisitar livro"};
		String[] leave = {"Sair"};
		boolean mainMenu = false;
		listHash = new Hash[nmrHashes];
		fillHashList();
		minHash = new MinHash(nmrHashes, nmrCharsPerShingle, listHash);

		try 
		{
			lib = new Library("MPEI Library");
			libName = lib.name();
			listOfBooks = lib.acervo();
		} 
		catch(FileNotFoundException e) 
		{}

		do {
			mainMenu = false;
			display_menu(menu1, true);

			switch(in.nextInt())
			{
				case 0:
					break;

				case 1:

					out.println("");
					display_menu(listar, true);

					switch(in.nextInt())
					{
						case 0:

							mainMenu = true;
							out.println("");
							break;

						case 1:

							break;

						case 2:

							break;

					}
					break;

				case 2:

					out.println("");
					display_menu(pesquisar, true);

					switch(in.nextInt())
					{
						case 0:

							mainMenu = true;
							out.println("");
							break;

						case 1:

							break;

						case 2:
							Scanner inString = new Scanner(System.in);
							List<Book> similarBooks = new ArrayList<>();
							out.print("Título do livro: ");
							String title = inString.nextLine();
							int[] minHashes = getMinHashes(title);
							for (Book b : listOfBooks) {
								int[] minHashesOfB = getMinHashes(b.title());
								if (checkSimilarity(minHashes, minHashesOfB) > 20) {
									similarBooks.add(b);
								}
							}
							out.println("");
							displayBooks(similarBooks);
							display_menu(leave, false);
							Scanner in2 = new Scanner(System.in);
							switch(in2.nextInt()) {
								case 0:
									mainMenu = true;
									out.println("");
									break;
								case 1:
									out.println("A sair...");
									break;
							}
							break;
					}
					
					break;

				case 3:

					out.println("");
					display_menu(pesquisar1, true);
					

					switch(in.nextInt())
					{
						case 0:

							mainMenu = true;
							out.println("");
							break;

						case 1:

							break;

						case 2:

							break;
					}
					
					break;

				case 4:

					out.println("");
					display_menu(admin, true);

					switch(in.nextInt())
					{
						case 0:

							mainMenu = true;
							out.println("");
							break;

						case 1:

							break;

						case 2:

							break;

						case 3:

							break;

					}
					
					break;
			}
		} while(mainMenu);
	}

	public static double checkSimilarity(int[] a, int[] b) {
        int intersections = getIntersections(a, b);
        return ((double) intersections/((double) (a.length*2)-(double) intersections))*100;
    }

    private static int getIntersections(int[] a, int[] b) {
        int intersections=0; 
        for (int i=0; i<a.length; i++) {
            for (int j=0; j<b.length; j++) {
                if (a[i] == b[j])
                    intersections++;
            }
        }
        return intersections;
    }

    public static int[] getMinHashes(String s) {
        List<int[]> hashesPerShingle = new ArrayList<>();
        String[] shingles = minHash.makeShingles(s);
        for (String shingle : shingles) {
            int str2hash = MathWorksFunctions.string2hash(shingle, "djb2");
            int[] hashes = minHash.getHashesForShingle(str2hash);
            hashesPerShingle.add(hashes);
        }
        return minHash.stringHashes(hashesPerShingle);
    }

    public static void fillHashList() {
        for (int i=0; i<listHash.length; i++) {
            listHash[i] = new Hash((int) (Math.pow(2, 32)), 900003659);
        }
	}
	
	public static void displayBooks(List<Book> books) {
		if (books != null) {
			if (books.size()>0) {
				for (Book b : books) {
					out.println(b);
				}	
			}
			else
				out.println("Não há livros para mostrar!");
			out.println("");
		}
	}

	public static void clearScreen() {  
		out.print("\033[H\033[2J");  
		out.flush();  
	}  
}