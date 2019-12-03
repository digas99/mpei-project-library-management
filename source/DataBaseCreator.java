
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.json.simple.*;
import static java.lang.System.*;

public class DataBaseCreator {

    private static FetchFromWeb[] ffwArray;
    private Book[] books;
    private Library lib;
    private int idCounter;

    public DataBaseCreator(String[] links, String[] fileNames) throws IOException {
        idCounter = 1;
        ffwArray = new FetchFromWeb[fileNames.length];
        int nmrFetched = 0;
        int loadingSize = 50;
        int loadingDivision = loadingSize/fileNames.length;
        out.println("Fetching pages...");
        out.print("Loading: [");
        for (int i=0; i<fileNames.length; i++) {
            File f = new File("html_pages/"+fileNames[i]+".html");
            PrintWriter pw = new PrintWriter(f);
            FetchFromWeb ffw = new FetchFromWeb(links[i], fileNames[i]);
            ffwArray[nmrFetched] = ffw;
            nmrFetched++;
            String htmlContent = ffw.getHTML();
            for (int j=0; j<loadingDivision; j++) {
                out.print("|");
            }
            pw.print(htmlContent);
            pw.flush();
            pw.close();
        }
        out.println("]\n");
    }

    public DataBaseCreator(List<Book> booksList) {
        idCounter = 1;
        books = booksList.toArray(new Book[booksList.size()]);
    }

    public DataBaseCreator(Book[] books) {
        idCounter = 1;
        this.books = books;
    }

    public DataBaseCreator(Library lib) {
        idCounter = 1;
        this.lib = lib;
    }

    public void printBooks(FetchFromWeb ffw) {
        int counter=1;
            out.println("-> "+ffw.getName()+":\n");
            for (Map.Entry<String, String> book : ffw.getBooksInfoMap().entrySet()) {
                out.println("["+counter+"] - Title: "+book.getKey()+" | Author: "+book.getValue()+"\n");
                counter++;
            }
        out.print("");
    }

    public boolean exportDB() throws FileNotFoundException {
        PrintWriter pwJSON = new PrintWriter("../data_base.json");
        Map<Integer, List<String>> booksMap;
        JSONObject jsO = new JSONObject();
        List<List<String>> booksList;
        if (ffwArray != null && ffwArray.length > 0) {
            for (FetchFromWeb ffw : ffwArray) {
                booksMap = new HashMap<>();
                printBooks(ffw);
                booksList = ffw.getBooksList();
                for (List<String> book : booksList) {
                    booksMap.put(idCounter, book);
                    idCounter+=1;
                }
                jsO.put(ffw.getName(), booksMap);
            }
            pwJSON.write(jsO.toJSONString());
            pwJSON.flush();
            pwJSON.close();
            return true;
        }
        else if ((books != null && books.length > 0) || lib != null) {
            booksMap = new HashMap<>();
            booksList = new ArrayList<>();
            Category[] categories = Category.getCategories();
            for (Category c : categories) {
                out.println("-> "+Category.getFileName(c)+":\n");
                if (lib != null) {
                    books = lib.acervo().toArray(new Book[lib.acervo().size()]);
                }
                for (Book b : books) {
                    out.println(b.toString());
                    if (b.category().equals(c)) {
                        String borrowed;
                        if (b.borrowed())
                            borrowed = "true";
                        else
                            borrowed = "false";
                        List<String> aux = new ArrayList<>();
                        aux.add(b.title());
                        aux.add(b.author());
                        aux.add(borrowed);
                        booksList.add(aux);
                    }
                out.print("");
                }

                for (List<String> book : booksList) {
                    booksMap.put(idCounter, book);
                    idCounter+=1;
                }

                jsO.put(Category.getFileName(c), booksMap);
            }
            pwJSON.write(jsO.toJSONString());
            pwJSON.flush();
            pwJSON.close();
            return true;
        }
        pwJSON.flush();
        pwJSON.close();
        out.print("Could not create the data base file!");
        return false;
    }
}