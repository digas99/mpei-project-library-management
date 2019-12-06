import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.*;

public class Book {

    private int id;
    private String title;
    private String author;
    private Category category;
    private boolean borrowed;
    private Date timeWhenBorrowed;

    public Book(String title, String author, Category category) {
        this.title = title;
        this.author = author;
        this.category = category;
        borrowed = false;
    }

    public Book(int id, String title, String author, boolean borrowed, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.borrowed = borrowed;
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

    public void borrow() {
        borrowed = true;
        timeWhenBorrowed = Calendar.getInstance().getTime();
    }

    public void returnBook() {
        borrowed = false;
        timeWhenBorrowed = null;
    }

    public Date getTimeWhenBorrowed() {
        return timeWhenBorrowed;
    }

    public boolean setId(int id) {
        if (id>0) {
            this.id = id;
            return true;
        }
        return false;
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
            if (id == 0) {
                return "[Requesitado] "+title+" | "+author+" - ["+Category.getName(category)+"]";
            }
            return id+". [Requesitado] "+title+" | "+author+" - ["+Category.getName(category)+"]";
        }
        if (id == 0) {
            return title+" | "+author+" - ["+Category.getName(category)+"]";
        }
        return id+". "+title+" | "+author+" - ["+Category.getName(category)+"]";
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