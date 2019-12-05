import static java.lang.System.*;

import java.util.List;

public class MinHash {

    private int nHashes;
    private int nmrCharsPerShingle;
    private Hash[] hashList;
    private int nmrShingles;

    public MinHash(int nmrHashes, int nmrCharsPerShingle, Hash[] hashList) {
        nHashes = nmrHashes;
        this.nmrCharsPerShingle = nmrCharsPerShingle;
        this.hashList = hashList;
    }

    public int nmrHashes() {
        return nHashes;
    }

    public int nmrCharsPerShingle() {
        return nmrCharsPerShingle;
    }

    public String[] makeShingles(String s) {
        if (s != null) {
            int size = s.length();
            if (size > 0) {
                s = s.toLowerCase();
                s = s.trim();
                s = s.replaceAll(" ", "");
                out.println(s);
                nmrShingles = s.length()-2;
                if (nmrShingles<s.length()) {
                    // out.println("");
                    // out.println("Making shingles...");
                    String[] shingles = new String[nmrShingles];
                    // out.println("NmrCharsPerShingle = "+nmrCharsPerShingle);
                    // out.println("Size = "+size);
                    // out.println("nmrShingles = "+ nmrShingles);
                    // out.println("");
                    char[] arrayOfChars = stringToArrayOfChars(s);
                    for (int i=0; i<nmrShingles; i++) {
                        shingles[i] = createShingle(nmrCharsPerShingle, i, arrayOfChars);    
                    }
                    return shingles;
                }
                else
                    return null;
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

    public int[] getHashesForShingle(int shingleHashed) {
        Hash h;
        int[] hashes = new int[nHashes];
        for (int i=0; i<nHashes; i++) {
            h = hashList[i];
            hashes[i] = hash(shingleHashed, h);
        }
        return hashes;
    }

    public int hash(int shingle, Hash h) {
        int hashVal = (h.a()*shingle+h.b()) % h.oddNmr();
        if (hashVal < 0)
            hashVal = -hashVal;
        return hashVal;
    }

    public int[] stringHashes(List<int[]> hashesList) {
        int sizeArray = hashesList.get(0).length;
        int[] finalArray = new int[sizeArray];
        for (int i=0; i<sizeArray; i++) {
            int[] currentPosHashes = new int[nmrShingles];
            for (int j=0; j<nmrShingles; j++) {
                currentPosHashes[j] = hashesList.get(j)[i];
            }
            int minHashVal = getMin(currentPosHashes);
            finalArray[i] = minHashVal;
        }
        return finalArray;
    }

    private int getMin(int[] arr) {
        int min = arr[0];
        for (int i=1; i<arr.length; i++) {
            if (arr[i] < min)
                min = arr[i];
        }
        return min;
    }
}