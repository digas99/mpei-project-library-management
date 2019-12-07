import static java.lang.System.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class RunLibrary
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
	static String authorSearched = "";

	public static void displayMenu(String title, String[] opts, boolean clear) 
	{	
		out.println("");		
		if (clear)
			clearScreen();
		out.println(title);
    	for (int i=0; i<opts.length; i++) 
    	{
			out.println("["+(i+1)+"] - "+opts[i]);
		}
		if (title == libName)
			out.println("[0] - Sair");
		else
			out.println("[0] - Anterior");
		out.print("Selecione uma opção: ");
	}

	public static void main(String[] args) throws IOException
	{
		String[] menu1 = {"Listar Livros", "Pesquisar Livros", "Pesquisar autores", "Admistração"};
		String[] listar = {"Listar todos os livros", "Listar livros por categoria"};
		String[] pesquisar = {"Verificar existência de livro", "Listar livros com títulos parecidos"};
		String[] pesquisar1 = {"Verificar se há livros do autor", "Listar livros do autor"};
		String[] admin = {"Adicionar livro", "Remover livro", "Requisitar livro", "Devolver livro"};
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
			displayMenu(libName, menu1, true);

			switch(in.nextInt())
			{
				case 0:
					break;

				case 1:

					out.println("");
					displayMenu("Listar Livros", listar, true);

					switch(in.nextInt())
					{
						case 0:
							mainMenu = true;
							out.println("");
							break;

						case 1:
							out.println("");
							for (Book b : listOfBooks) {
								out.println(b);
							}
							break;

						case 2:
							String[] categories = stringOfCategoriesNames();
							displayMenu("Categorias", categories, true);
							Scanner in3 = new Scanner(System.in);
							int opt1 = in3.nextInt();
							switch(opt1) {
								case 0:
									mainMenu = true;
									out.println("");
									break;
								default:
									filterBooksByCategory(categories[opt1-1]);
							}
							break;

					}
					break;

				case 2:

					out.println("");
					displayMenu("Pesquisar Livros" ,pesquisar, true);

					switch(in.nextInt())
					{
						case 0:
							mainMenu = true;
							out.println("");
							break;

						case 1:
							out.print("Título do livro: ");
							Scanner inString1 = new Scanner(System.in);
							String input = inString1.nextLine();
							for (Book b : listOfBooks) {
								bm.insert(b.title());
							}
							if (bm.isMember(input))
								out.println("Poderá existir um livro com o título "+input);
							else
								out.println("Não existe nenhum livro com o título "+input);
							break;

						case 2:
							Scanner inString = new Scanner(System.in);
							List<Book> similarBooks = new ArrayList<>();
							out.print("Título do livro: ");
							String title = inString.nextLine();
							int[] minHashes = getMinHashes(title);
							for (Book b : listOfBooks) {
								int[] minHashesOfB = getMinHashes(b.title());
								if (similarityValue(minHashes, minHashesOfB) > 7) {
									similarBooks.add(b);
								}
							}
							out.println("");
							displayBooks("Livros parecidos com "+title,similarBooks);
							break;
					}
					
					break;

				case 3:

					out.println("");
					displayMenu("Pesquisar Autores", pesquisar1, true);
					

					switch(in.nextInt())
					{
						case 0:

							mainMenu = true;
							out.println("");
							break;

						case 1:
							out.print("Nome do autor: ");
							Scanner inString = new Scanner(System.in);
							authorSearched = inString.nextLine();
							for (Book b : listOfBooks) {
								bm.insert(b.author());
							}
							if (bm.isMember(authorSearched))
								out.println("O autor "+authorSearched+" poderá ter livros na biblioteca.");
							else
								out.println("Não existe nenhum livro do autor "+authorSearched);
							break;

						case 2:
							List<Book> booksOfAuthor = new ArrayList<>();
							int[] minHashesAuthor;
							if (authorSearched != "")
								minHashesAuthor = getMinHashes(authorSearched);
							else {
								out.println("Procura, primeiro, se há livros de um autor!");
								break;
							}
							for (Book b : listOfBooks) {
								int[] minHashesOfB = getMinHashes(b.author());
								if (similarityValue(minHashesAuthor, minHashesOfB) > 70) {
									booksOfAuthor.add(b);
								}
							}
							displayBooks("Livros do autor "+authorSearched, booksOfAuthor);
							break;
					}
					
					break;

				case 4:

					out.println("");
					displayMenu("Admin", admin, true);

					switch(in.nextInt())
					{
						case 0:
							mainMenu = true;
							out.println("");
							break;

						case 1:
							Scanner inString = new Scanner(System.in);
							out.println("Nome do livro: ");
							String title = inString.nextLine();
							out.println("Autor do livro: ");
							String author = inString.nextLine();
							String[] categories = stringOfCategoriesNames();
							displayMenu("Categoria", categories, false);
							String categoria = "";
							Scanner in2 = new Scanner(System.in);
							int opt1 = in2.nextInt();
							switch(opt1) {
								case 0:
									mainMenu = true;
									out.println("");
									break;
								default:
									categoria = categories[opt1-1];
							}
							out.println("CATEGORIA: "+categoria);
							if (categoria != "") {
								Book newBook = new Book(lib.getLastId(), title, author, false, Category.getCategory(categoria));
								if (lib.addBook(newBook))
									out.println("Livro "+newBook+" adicionado com sucesso!");
								else
									out.println("Livro "+newBook+" não foi adicionado!");
							}
							else {
								out.println("Livro não adicionado.");
							}
							break;

						case 2:
							Scanner in3 = new Scanner(System.in);
							out.print("ID do livro a remover: ");
							int id = in3.nextInt();
							List<Book> copyList = getCopyOfBooksList();
							for (Book b : copyList) {
								if (b.id() == id) {
									if (lib.removeBook(id))
										out.println("Livro "+b+" removido com sucesso!");
									else
										out.println("Livro "+b+" não foi removido!");
								}
							}
							break;

						case 3:
							Scanner in4 = new Scanner(System.in);
							out.print("ID do livro a requesitar: ");
							int id1 = in4.nextInt();
							for (Book b : listOfBooks) {
								if (b.id() == id1) {
									if (!b.borrowed()) {
										b.borrow();
										out.println("Livro "+b+" requesitado!");
										out.println(b.getTimeWhenBorrowed());
									}
									else
										out.println("Este livro já foi requesitado!");
								}
							}
							break;

						case 4:
							Scanner in5 = new Scanner(System.in);
							out.print("ID do livro a devolver: ");
							int id2 = in5.nextInt();
							for (Book b : listOfBooks) {
								if (b.id() == id2) {
									if (b.borrowed()) {
										b.returnBook();
										out.println("Livro "+b+" devolvido!");
									}
									else
										out.println("Este livro nunca foi requesitado.");
								}
							}
							break;
					}
					
					break;
			}
			mainMenu = voltar(leave);
		} while(mainMenu);
	}

	public static double similarityValue(int[] a, int[] b) {
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
        return minHash.minHashes(hashesPerShingle);
    }

    public static void fillHashList() {
        for (int i=0; i<listHash.length; i++) {
            listHash[i] = new Hash((int) (Math.pow(2, 32)), 900003659);
        }
	}
	
	public static void displayBooks(String title, List<Book> books) {
		clearScreen();
		out.println(title);
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

	public static String[] stringOfCategoriesNames() {
		Category[] categories = Category.getCategories();
		String[] names = new String[categories.length];
		int counter=0;
		for (Category c : categories) {
			names[counter] = Category.getName(c);
			counter++;
		}
		return names;
	}

	public static void filterBooksByCategory(String categoryName) {
		List<Book> validBooks = new ArrayList<>();
		int[] minHashesOfCat0 = getMinHashes(categoryName);
		for (Book b : listOfBooks) {
			int[] minHashesOfB = getMinHashes(Category.getName(b.category()));
			if (similarityValue(minHashesOfCat0, minHashesOfB) == 100) {
				validBooks.add(b);
			}
		}
		displayBooks("Livros da categoria "+categoryName, validBooks);
	}

	public static boolean voltar(String[] options) {
		displayMenu("Voltar", options, false);
		Scanner in2 = new Scanner(System.in);
		switch(in2.nextInt()) {
			case 0:
				out.println("");
				return true;
			case 1:
				out.println("A sair...");
				break;
		}
		return false;
	}

	public static List<Book> getCopyOfBooksList() {
		List<Book> newList = new ArrayList<>();
		for (Book b : listOfBooks) {
			newList.add(b);
		}
		return newList;
	}
}