import java.net.*;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class FetchFromWeb {

    private String htmlContent;
    private String name;
    private Map<String, String> codes = new HashMap<String, String>();

    public FetchFromWeb(String link, String name) {
        this.name = name;
        fillCodes();
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

    public List<List<String>> getBooksList() {
        List<List<String>> list = new ArrayList<>();
        String[] titles = getBooksTitle();
        String[] authors = getBooksAuthor();
        for (int i=0; i<titles.length; i++) {
            List<String> aux = new ArrayList<>();
            aux.add(titles[i]);
            aux.add(authors[i]);
            aux.add("false");
            list.add(aux);
        }
        return list;
    }

    public String[] getBooksTitle() {
        List<String> titles = new ArrayList<String>();
        String[] split1 = htmlContent.split("f-searchResult__title");
        for (int i = 1; i < split1.length; i++) {
            String[] split2 = split1[i].split(">");
            titles.add(utf8Fixing(removeSpacesFromBeginningAndEndOfString(split2[1].split("<")[0], false)));
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

    private String utf8Fixing(String s) {
        String newS = "";
        String code = "";
        int enteringCodeCounter = 0;
        boolean dontAppend = false;
        for (int i=0; i<s.length(); i++) {
            char currentChar = s.charAt(i);
            if (enteringCodeCounter == 0 && dontAppend == true) {
                dontAppend = false;
            }

            if (enteringCodeCounter == 5) {
                newS+=codes.get(code);
                enteringCodeCounter = 0;
                code = "";
            }

            if (enteringCodeCounter >= 2 && enteringCodeCounter < 6) {
                dontAppend = true;
                code+=currentChar;
                enteringCodeCounter++;
            }

            if (currentChar == '&' || currentChar == '#') {
                dontAppend = true;
                enteringCodeCounter++;
            }
            
            if (!dontAppend) {
                newS+=currentChar;
            }
        }
        return newS;
    }

    private void fillCodes() {
        codes.put("231", "ç");
        codes.put("233", "é");
        codes.put("225", "á");
        codes.put("243", "ó");
        codes.put("244", "ô");
        codes.put("224", "à");
        codes.put("227", "ã");
        codes.put("245", "õ");
        codes.put("234", "ê");
        codes.put("237", "í");
        codes.put("250", "ú");
        codes.put("199", "Ç");
        codes.put("201", "É");
        codes.put("193", "Á");
        codes.put("211", "Ó");
        codes.put("192", "À");
        codes.put("195", "Ã");
        codes.put("213", "Õ");
        codes.put("202", "Ê");
        codes.put("205", "Í");
        codes.put("212", "Ô");
        codes.put("218", "Ú");
    }
}