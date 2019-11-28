import java.net.*;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class FetchFromWeb {

    private String htmlContent;
    private String name;

    public FetchFromWeb(String link, String name) {
        this.name = name;
        String content = null;  
        URLConnection connection = null;
        try {
            connection =  new URL(link).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        htmlContent = content;
    }

    public String getHTML() {
        return htmlContent;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getBooksInfoMap() {
        Map<String, String> info = new HashMap<String, String>();
        String[] titles = getBooksTitle();
        String[] authors = getBooksAuthor();
        for (int i=0; i<titles.length; i++) {
            info.put(titles[i], authors[i]);
        }
        return info;
    }

    public String[] getBooksTitle() {
        List<String> titles = new ArrayList<String>();
        String[] split1 = htmlContent.split("f-searchResult__title");
        for (int i = 1; i < split1.length; i++) {
            String[] split2 = split1[i].split(">");
            titles.add(removeSpacesFromBeginningAndEndOfString(split2[1].split("<")[0], false));
        }
        return titles.toArray(new String[titles.size()]);
    }

    public String[] getBooksAuthor() {
        List<String> authors = new ArrayList<String>();
        String[] split1 = htmlContent.split("searchResult__subtitle");
        for (int i = 1; i < split1.length; i++) {
            String[] split2 = split1[i].split(">");
            authors.add(removeSpacesFromBeginningAndEndOfString(split2[2].split("<")[0], false));
        }
        return authors.toArray(new String[authors.size()]);
    }

    private String removeSpacesFromBeginningAndEndOfString(String s, boolean stopReversing) {
        String newS = "";
        boolean beginningFlag = false;
        for (int i=0; i<s.length(); i++) {
            // System.out.println("BeggingFlag: "+beginningFlag);
            char currentChar = s.charAt(i);
            if (!beginningFlag) {
                if (!Character.isWhitespace(currentChar)) {
                    beginningFlag = true;
                }
            }
            if (beginningFlag) {
                newS += ""+currentChar;
            }
        }
        // remove from end
        if (!stopReversing) {
            newS = removeSpacesFromBeginningAndEndOfString(reverse(newS), true);
            // put back in correct order
            return reverse(newS);
        }
        
        return newS;
    }

    private String reverse(String s) {
        String newS = "";
        for (int i=s.length()-1; i>=0; i--) {
            newS += s.charAt(i);
        }
        return newS;
    }
}