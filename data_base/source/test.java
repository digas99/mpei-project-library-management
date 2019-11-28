import java.io.*;
import java.util.Map;

public class test {

    public static FetchFromWeb[] ffwArray;

    public static void main(String[] args) throws IOException {
        String[] fileNames = {"livros_literatura", "livros_thriller", "livros_syfy", "livros_crianças"};
        String[] links = {"https://www.fnac.pt/n790158/Os-100-Melhores-Livros/Os-100-Melhores-Livros-de-Literatura", "https://www.fnac.pt/n790159/Os-100-Melhores-Livros/Os-100-Melhores-Livros-de-Policial-e-Thriller", "https://www.fnac.pt/n790160/Os-100-Melhores-Livros/Os-100-Melhores-Livros-de-Fantastico-e-Ficcao-Cientifica", "https://www.fnac.pt/n1284079/Os-100-Melhores-Livros/Biblioteca-Fnac-Kids/Livros-mais-de-10-anos"};
        ffwArray = new FetchFromWeb[fileNames.length];
        int nmrFetched = 0;
        int loadingSize = 50;
        int loadingDivision = loadingSize/fileNames.length;
        System.out.println("Fetching pages...");
        System.out.print("Loading: [");
        for (int i=0; i<fileNames.length; i++) {
            File f = new File("html_pages/"+fileNames[i]+".html");
            PrintWriter pw = new PrintWriter(f);
            FetchFromWeb ffw = new FetchFromWeb(links[i], fileNames[i]);
            ffwArray[nmrFetched] = ffw;
            nmrFetched++;
            String htmlContent = ffw.getHTML();
            for (int j=0; j<loadingDivision; j++) {
                System.out.print("|");
            }
            pw.print(htmlContent);
            pw.flush();
            pw.close();
        }
        System.out.println("]\n");

        for (FetchFromWeb ffw : ffwArray) {
            int counter=1;
            System.out.println("-> "+ffw.getName()+":\n");
            for (Map.Entry<String, String> book : ffw.getBooksInfoMap().entrySet()) {
                System.out.println("["+counter+"] - Title: "+book.getKey()+" | Author: "+book.getValue()+"\n");
                counter++;
            }
            System.out.print("");
        }
    }
}