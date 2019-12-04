
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.json.simple.*;
import static java.lang.System.*;

public class DataBaseCreator {

    private FetchFromWeb[] ffwArray;
    private List<Integer> ids;
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
            FetchFromWeb ffw = new FetchFromWeb(links[i], fileNames[i]);
            ffwArray[nmrFetched] = ffw;
            nmrFetched++;
            String htmlContent = ffw.getHTML();
            for (int j=0; j<loadingDivision; j++) {
                out.print("|");
            }
        }
        out.println("]\n");
    }

    public DataBaseCreator() {
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
        out.print("Could not create the data base file!");
        return false;
    }

    public boolean exportDB(List<Book> list) throws FileNotFoundException {
        int counter=0;
        ids = new ArrayList<>();
        // out.print("");
        // for (Book b : list) {
        //     out.print(b.id());
        // }
        // out.println("");
        PrintWriter pwJSON = new PrintWriter("../data_base.json");
        Map<Integer, List<String>> booksMap;
        JSONObject jsO = new JSONObject();
        Book[] books;
        List<List<String>> booksList;
        if (list != null && list.size() > 0) {
            Category[] categories = Category.getCategories();
            for (Category c : categories) {
                booksMap = new HashMap<>();
                booksList = new ArrayList<>();
                books = list.toArray(new Book[list.size()]);
                for (Book b : books) {
                    if (b.category().equals(c)) {
                        ids.add(b.id());
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
                }

                for (List<String> book : booksList) {
                    booksMap.put(ids.get(counter), book);
                    counter++;
                }
                jsO.put(Category.getFileName(c), booksMap);
            }
            pwJSON.write(jsO.toJSONString());
            pwJSON.flush();
            pwJSON.close();
            return true;
        }
        out.print("Could not create the data base file!");
        return false;
    }
}