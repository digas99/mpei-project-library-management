import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.*;

import java.io.FileNotFoundException;

public class Library {

    private int lastId;
    private List<Book> acervo;
    private List<Integer> ids;
    private String name;

    public Library(String name) throws FileNotFoundException {
        acervo = new ArrayList<>();
        ids = new ArrayList<>();
        this.name = name;
        lastId = loadLibrary("../data_base.json");
    }

    public Library(String name, List<Book> acervo) throws FileNotFoundException {
        acervo = new ArrayList<>();
        ids = new ArrayList<>();
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

    public boolean addBook(Book b) throws FileNotFoundException {
        for (Book book : acervo) {
            if (b.equals(book)) {
                out.println("Book " + b.toString() + " already exists!");
                return false;
            }
        }
        if (b != null) {
            b.setId(lastId+1);
            ids.add(b.id());
            acervo.add(b);
            out.println("||||||Book " + b.id() + " was registered successfully!");
            if (updateDB()) {
                out.println("||||||Book " + b.id() + " was successfully stored.");
                lastId+=1;
                return true;
            } else {
                out.println("||||||Book " + b.id() + " could not be stored.");
                out.println("||||||Removing from registry...");
                acervo.remove(b);
                ids.remove(b.id());
                return false;
            }
        } else {
            out.println("||||||Book " + b.id() + " could not be registered!");
            return false;
        }
    }
    
    public boolean removeBook(Book b) throws FileNotFoundException {
        if (b != null) {
            if (acervo.contains(b)) {
                ids.remove(b.id());
                acervo.remove(b);
                out.println("||||||Book " + b.id() + " had its registry erased successfully!");

                if (updateDB()) {
                    out.println("||||||Book " + b.id() + " was successfully removed.");
                    return true;
                } else {
                    out.println("||||||Book " + b.id() + " could not be removed.");
                    out.println("||||||Adding back to registry...");
                    acervo.add(b);
                    ids.add(b.id());
                    return false;
                }
            }
            else {
                out.println("||||||That book doesn't exist in the library!");
                return false;
            }
        }
        else {
            out.println("||||||Book " + b.id() + " could not be erased!");
            return false;
        }
    }

    public boolean removeBook(int id) throws FileNotFoundException {
        if (ids.contains(id)) {
            for (Book b : acervo) {
                if (b.id() == id) {
                    removeBook(b);
                    return true;
                }
            }
        }
        out.println("||||||There is no book with the id: "+id+" registered in this library.");
        return false;
    }

    public boolean updateDB() throws FileNotFoundException {
        DataBaseCreator dbCreator = new DataBaseCreator();
        return dbCreator.exportDB(acervo);
    }

    public int loadLibrary(String path) throws FileNotFoundException {
        Map<Integer, List<String>> content = new HashMap<>();
        out.println("Loading...");
        LoadFromDB loader = new LoadFromDB(path);
        int counter=0;
        for (Category c : Category.getCategories()) {
            content = loader.getCategoryContentMap(c);
            for (Map.Entry<Integer, List<String>> entry : content.entrySet()) {
                int id = entry.getKey();
                List<String> info = entry.getValue();
                boolean borrowed = false;
                if (info.get(2) == "true")
                    borrowed = true;
                addBook(new Book(id, info.get(0), info.get(1), borrowed, c));
                counter++;
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        String s = "Biblioteca("+acervo.size()+"): "+name+"\n";
        for (int i=0; i<acervo.size(); i++) {
            s+=acervo.get(i).toString()+"\n";
        }
        return s;
    }

    public void borrow(Book b) throws FileNotFoundException {
        b.borrow();
        updateDB();
    }
}