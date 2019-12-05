import static java.lang.System.*;

import java.util.List;

public class MinHash {

    private int nHashes;
    private int nmrShingles;

    public MinHash(int nmrHashes, int nmrShingles) {
        nHashes = nmrHashes;
        this.nmrShingles = nmrShingles;
    }

    public int nmrHashes() {
        return nHashes;
    }

    public int nmrShingles() {
        return nmrShingles;
    }

    public String[] makeShingles(String s) {
        if (s != null) {
            int size = s.length();
            if (size > 0) {
                s = s.toLowerCase();
                if (nmrShingles<s.length()) {
                    out.println("");
                    out.println("Making shingles...");
                    String[] shingles = new String[nmrShingles];
                    int nmrCharsPerShingle = (size-nmrShingles)+1;
                    out.println("NmrCharsPerShingle = "+nmrCharsPerShingle);
                    out.println("Size = "+size);
                    out.println("nmrShingles = "+ nmrShingles);
                    char[] arrayOfChars = stringToArrayOfChars(s);
                    int nShinglesCreated = 0;
                    String shingle="";
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
        int[] hashes = new int[nHashes];
        for (int i=0; i<nHashes; i++) {
            hashes[i] = hash(shingleHashed);
        }
        return hashes;
    }

    public int hash(int shingle) {
        int randVal = random(0, (int) (Math.pow(2, 32)+2)-1);
        int randOddVal = randomOdd(1, (int) (Math.pow(2, 32)+2)-1);
        int hashVal = ((randOddVal*shingle+randVal) % (int) (Math.pow(2, 32)+2)) % (int) (Math.pow(2, 32)+1);
        if (hashVal < 0)
            hashVal = -hashVal;
        return hashVal;
    }

    private int random(int min, int max) {
        int range = max-min+1;
        return (int) (Math.random() * range) + min;
    }

    private int randomOdd(int min, int max) {
        int rand = random(min, max);
        rand+=(rand%2==0?1:0);
        return rand;
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