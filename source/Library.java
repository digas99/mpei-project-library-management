
import java.util.List;
import java.util.ArrayList;
import static java.lang.System.*;

import java.io.FileNotFoundException;

public class Library {

    private int lastId;
    private List<Book> acervo;
    private String name;

    public Library(String name) {
        lastId = 0;
        acervo = new ArrayList<>();
        this.name = name;
    }

    public Library(String name, List<Book> acervo) {
        lastId = acervo.size();
        this.acervo = acervo;
        this.name = name;
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
            out.print("Book " + b.id() + " was registered successfully!");

            if (updateDB()) {
                out.print("Book " + b.id() + " was successfully stored.");
                return true;
            } else {
                out.print("Book " + b.id() + " could not be stored.");
                out.print("Removing from registry...");
                acervo.remove(b);
                return false;
            }
        } else {
            out.print("Book " + b.id() + " could not be registered!");
            return false;
        }
    }
    
    public boolean removeBook(Book b) throws FileNotFoundException {
        if (b != null) {
            acervo.remove(b);
            out.print("Book " + b.id() + " had its registry erased successfully!");

            if (updateDB()) {
                out.print("Book " + b.id() + " was successfully removed.");
                return true;
            } else {
                out.print("Book " + b.id() + " could not be removed.");
                out.print("Adding back to registry...");
                acervo.add(b);
                return false;
            }
        } else {
            out.print("Book " + b.id() + " could not be erased!");
            return false;
        }
    }

    private boolean updateDB() throws FileNotFoundException {
        DataBaseCreator dbCreator = new DataBaseCreator(acervo);
        if (dbCreator.exportDB()) {
            return true;
        }
        return false;
    }
}