import static java.lang.System.*;

public class MinHash {

    private int nHashes;

    public MinHash(int nmrHashes) {
        nHashes = nmrHashes;
    }

    public String[] makeShingles(String s) {
        if (s != null) {
            int size = s.length();
            if (size > 0) {
                out.println("");
                out.println("Making shingles...");
                int nmrCharsPerShingle = calcNmrCharsPerShingle(size);
                double nmrShingles = Math.ceil((double) size/ (double) nmrCharsPerShingle);
                String[] shingles = new String[(int) nmrShingles];
                out.println("NmrCharsPerShingle = "+nmrCharsPerShingle);
                out.println("Size = "+size);
                out.println("nmrShingles = "+ nmrShingles);
                char[] arrayOfChars = stringToArrayOfChars(s);
                int nShinglesCreated = 0;
                int counter=0;
                String shingle="";
                for (int i=0; i<nmrShingles; i++) {
                    if (i == nmrShingles-1) {
                        out.println("here");
                        shingles[(int) nmrShingles-1] = createShingle((arrayOfChars.length - nmrCharsPerShingle*i), counter, arrayOfChars);
                    }
                    else {
                        shingles[i] = createShingle(nmrCharsPerShingle, counter, arrayOfChars);
                        counter+=4;    
                    }
                }
                return shingles;
            }
            else
                return null;
        }
        else
            return null;
        
    }

    public static char[] stringToArrayOfChars(String s) {
        int size = s.length();
        char[] arr = new char[size];
        for (int i=0; i<size; i++) {
            arr[i] = s.charAt(i);
        }
        return arr;
    }

    private String createShingle(int nmrCharsInShingle, int pos, char[] chars) {
        String shingle="";
        for (int i=pos; i<pos+nmrCharsInShingle; i++) {
            shingle+=chars[i];
        }
        return shingle;
    }

    private int calcNmrCharsPerShingle(int size) {
        if (size > 0 && size < 4)
            return 1;
        if (size >= 4 && size < 8)
            return 2;
        if (size >=8 && size < 12)
            return 3;
        return 4;
    }
}