import static java.lang.System.*;
import java.util.Scanner;
import java.io.*;

public class menu
{
	static BloomFilter bm = new BloomFilter((int)1e6, 6);
	static Library lib;
	static Scanner in = new Scanner(System.in);

	public static void display_menu(String[] opts) 
	{
    	for (int i=0; i<opts.length; i++) 
    	{
        	out.println("["+(i+1)+"] - "+opts[i]);
        	out.print("[0] - Anterior");
    	}
	}

	public static void main(String[] args) throws IOException
	{
		String[] menu1 = {"Listar Livros", "Pesquisar Livros", "Pesquisar autores", "Admistração"};
		String[] listar = {"Listar todos os livros", "Listar livros com titulos parecidos"};
		String[] pesquisar = {"Verificar existncia de há livros do autor", "Listar livros do autor"};
		String[] admin = {"Adicionar livro", "Remover livro", "Requisitar livro"};

		display_menu(menu1);

		try 
		{
			lib = new Library("MPEI Library");
		} 
		catch(FileNotFoundException e) 
		{}

		System.out.print("Selecione uma opção:");

		switch(in.nextInt())
		{
			case 1:
				display_menu(listar);
				switch(in.nextInt())
				{
					case 1:
						break;

				}
				break;

			case 2:
				
				break;

			case 3:
				
				break;

			case 4:
				
				break;
		}
	}
}