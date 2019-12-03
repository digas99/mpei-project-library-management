class Book {

    private final int id;
    private String title;
    private String author;
    private Category category;

    public Book(String title, String author, Category category, Library l) {
        if (l != null) {
            id = l.setIdToBook();
            l.increaseId();
        }
        this.title = title;
        this.author = author;
        this.category = category;
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
        return id+". "+title+" by "+author;
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