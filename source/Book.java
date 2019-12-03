import java.io.FileNotFoundException;

public class Book {

    private final int id;
    private String title;
    private String author;
    private Category category;
    private boolean borrowed;
    private Library lib;

    public Book(String title, String author, Category category, Library l) {
        if (l != null) {
            id = l.setIdToBook();
            l.increaseId();
        } else
            id = 0;
        this.title = title;
        this.author = author;
        this.category = category;
        borrowed = false;
    }

    public Book(int id, String title, String author, String borrowed, Category category, Library l) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        lib = l;
        if (borrowed == "true")
            this.borrowed = true;
        else
            this.borrowed = false;
    }

    public int id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String author() {
        return author;
    }

    public Category category() {
        return category;
    }

    public boolean borrowed() {
        return borrowed;
    }

    // FALTA DAR UPDATE NA BASE DE DADOS
    public void borrow() throws FileNotFoundException {
        borrowed = true;
        lib.updateDB();
    }

    public boolean setTitle(String t) {
        if (t.length() > 0) {
            title = t;
            return true;
        }
        return false;
    }

    public boolean setAuthor(String a) {
        if (a.length() > 0) {
            author = a;
            return true;
        }
        return false;
    }

    public boolean setCategory(Category c) {
        if (Category.getName(c) != null) {
            category = c;
            return true;    
        }
        return false;
    }

    public boolean setCategory(String s) {
        Category c = Category.getCategory(s);
        if (c != null) {
            category = c;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (borrowed) {
            return "[R] "+id+". "+title+" by "+author+" - "+category;
        }
        return id+". "+title+" by "+author+" - "+category;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
            
        Book other = (Book) obj;

        if (title == null) {
            if (other.title != null)
                return false;
        }
        else if (!title.equals(other.title))
            return false;

        if (author == null) {
            if (other.author != null)
                return false;
        }
        else if (!author.equals(other.author))
            return false;
        
        if (category == null) {
            if (other.category != null)
                return false;
        }
        else if (!category.equals(other.category))
            return false;
        
        return true;
    }
}