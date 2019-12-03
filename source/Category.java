enum Category {

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
}