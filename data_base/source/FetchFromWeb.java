import java.net.*;
import java.util.Scanner;
import java.io.*;

public class FetchFromWeb {

    private String htmlContent;

    public FetchFromWeb(String link) {
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

    // public getBooksName() {
        
    // }

    // public getBooksAuthor() {

    // }

    // public getBooksInfo() {

    // }
}