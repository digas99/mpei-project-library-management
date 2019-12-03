import java.util.List;
import java.util.ArrayList;
import static java.lang.System.*;
import org.json.simple.*;

class Library {

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

    public boolean addBook(Book b) {
        if (b != null) {
            acervo.add(b);
            out.print("Book "+b.id()+" was registered successfully!");

            if (addToDB(b)) {
                out.print("Book "+b.id()+" was successfully stored.");
                return true;
            }
            else {
                out.print("Book "+b.id()+" could not be stored.");
                out.print("Removing from registry...");
                acervo.remove(b);
                return false;
            }
        }
        else {
            out.print("Book "+b.id()+" could not be registered!");
            return false;
        }
    }

    private boolean addToDB(Book b) {
        
    }
}