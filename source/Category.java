
public enum Category {

    LITERATURE, THRILLER, KIDS, SYFY;

    public static String getName(Category c) {
        switch(c) {
            case LITERATURE:
                return "Literatura";
            case THRILLER:
                return "Thriller";
            case KIDS:
                return "Crianças";
            case SYFY:
                return "Ficção Científica";
            default:
                return null;
        }
    }

    public static Category getCategory(String s) {
        switch(s) {
            case "Literatura":
                return LITERATURE;
            case "Thriller":
                return THRILLER;
            case "Crianças":
                return KIDS;
            case "Ficcção Científica":
                return SYFY;
            default:
                return null;
        }
    }

    public static String getFileName(Category c) {
        switch(c) {
            case LITERATURE:
                return "livros_literatura";
            case THRILLER:
                return "livros_thriller";
            case KIDS:
                return "livros_crianças";
            case SYFY:
                return "livros_syfy";
            default:
                return null;
        }
    }

    public static Category getCategoryFromFileName(String s) {
        switch(s) {
            case "livros_literatura":
                return LITERATURE;
            case "livros_thriller":
                return THRILLER;
            case "livros_crianças":
                return KIDS;
            case "livros_syfy":
                return SYFY;
            default:
                return null;
        }
    }

    public static Category[] getCategories() {
        Category[] arr = {LITERATURE, THRILLER, KIDS, SYFY};
        return arr;
    }
}