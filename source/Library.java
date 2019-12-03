import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import static java.lang.System.*;

import java.io.FileNotFoundException;

public class Library {

    private int lastId;
    private List<Book> acervo;
    private String name;

    public Library(String name) throws FileNotFoundException {
        lastId = 0;
        acervo = new ArrayList<>();
        this.name = name;
        loadLibrary("../data_base.json");
    }

    public Library(String name, List<Book> acervo) throws FileNotFoundException {
        lastId = acervo.size();
        this.acervo = acervo;
        this.name = name;
        loadLibrary("../data_base.json");
    }

    public int getLastId() {
        return lastId;
    }

    public List<Book> acervo() {
        return acervo;
    }

    public String name() {
        return name;
    }

    public boolean setName(String s) {
        if (s.length() > 0) {
            name = s;
            return true;
        }
        return false;
    }

    public int setIdToBook() {
        return lastId + 1;
    }

    public void increaseId() {
        lastId += 1;
    }

    public boolean addBook(Book b) throws FileNotFoundException {
        if (b != null) {
            acervo.add(b);
            out.println("Book " + b.id() + " was registered successfully!");

            if (updateDB()) {
                out.println("Book " + b.id() + " was successfully stored.");
                return true;
            } else {
                out.println("Book " + b.id() + " could not be stored.");
                out.println("Removing from registry...");
                acervo.remove(b);
                return false;
            }
        } else {
            out.println("Book " + b.id() + " could not be registered!");
            return false;
        }
    }
    
    public boolean removeBook(Book b) throws FileNotFoundException {
        if (b != null) {
            acervo.remove(b);
            out.println("Book " + b.id() + " had its registry erased successfully!");

            if (updateDB()) {
                out.println("Book " + b.id() + " was successfully removed.");
                return true;
            } else {
                out.println("Book " + b.id() + " could not be removed.");
                out.println("Adding back to registry...");
                acervo.add(b);
                return false;
            }
        } else {
            out.println("Book " + b.id() + " could not be erased!");
            return false;
        }
    }

    public boolean updateDB() throws FileNotFoundException {
        DataBaseCreator dbCreator = new DataBaseCreator(acervo);
        return dbCreator.exportDB();
    }

    public void loadLibrary(String path) throws FileNotFoundException {
        out.println("Loading...");
        LoadFromDB loader = new LoadFromDB(path);
        for (Category c : Category.getCategories()) {
            Map<Integer, List<String>> content = loader.getCategoryContentMap(c);
            for (Map.Entry<Integer, List<String>> entry : content.entrySet()) {
                int id = entry.getKey();
                List<String> info = entry.getValue();
                addBook(new Book(id, info.get(0), info.get(1), info.get(2), c, this));
            }
        }
    }

    @Override
    public String toString() {
        String s = name+"\n";
        for (int i=0; i<acervo.size(); i++) {
            s+=acervo.get(i).toString()+"\n";
        }
        return s;
    }
}