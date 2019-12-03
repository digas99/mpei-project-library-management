import java.io.IOException;

public class DataBaseInitializer {

    public static void main(String[] args) throws IOException {
        String[] fileNames = {"livros_literatura", "livros_thriller", "livros_syfy", "livros_crianças"};
        String[] links = {"https://www.fnac.pt/n790158/Os-100-Melhores-Livros/Os-100-Melhores-Livros-de-Literatura", "https://www.fnac.pt/n790159/Os-100-Melhores-Livros/Os-100-Melhores-Livros-de-Policial-e-Thriller", "https://www.fnac.pt/n790160/Os-100-Melhores-Livros/Os-100-Melhores-Livros-de-Fantastico-e-Ficcao-Cientifica", "https://www.fnac.pt/n1284079/Os-100-Melhores-Livros/Biblioteca-Fnac-Kids/Livros-mais-de-10-anos"};
        DataBaseCreator dbCreator = new DataBaseCreator(links, fileNames);
        dbCreator.exportDB();
    }
}