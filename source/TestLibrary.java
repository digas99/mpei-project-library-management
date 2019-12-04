import java.io.FileNotFoundException;
import java.util.List;
import static java.lang.System.*;

public class TestLibrary {

    public static void main(String[] args) throws FileNotFoundException {
        Library lib = new Library("MPEI");
        List<Book> l = lib.acervo();
        for (int i=0; i<l.size(); i++) {
            if (i%4==0) {
                l.get(i).borrow();
            }
        }
        out.println(lib);
        lib.removeBook(40);
        lib.addBook(new Book("Iraque", "João Ratão", Category.THRILLER));
        out.println(lib);
        lib.addBook(new Book("Unhas Negras", "João da Silva Correia", Category.LITERATURE));
        out.println(lib);
        lib.addBook(new Book("Unhas Negras", "João da Silva Correia", Category.LITERATURE));
    }
}