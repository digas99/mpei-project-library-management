import static java.lang.System.*;

import java.util.ArrayList;
import java.util.List;

public class TestMinHash {

    public static List<int[]> hashesPerShingle = new ArrayList<>();
    public static Hash[] hashList;
    public static int nmrCharsPerShingle = 3;
    static int nmrHashes = 15;

    public static void main(String[] args) {
        String s = "diogo costa correia";
        hashList = new Hash[nmrHashes];
        fillHashList();
        MinHash minHash = new MinHash(nmrHashes, nmrCharsPerShingle, hashList);
        String[] shingles = minHash.makeShingles(s);
        out.println(s+"\n");
        for (String shingle : shingles) {
            int str2hash = MathWorksFunctions.string2hash(shingle, "djb2");
            int[] hashes = minHash.getHashesForShingle(str2hash);
            hashesPerShingle.add(hashes);
            int c=0;
            for (int hash : hashes) {
                if (c == hashes.length-1)
                    out.print(hash);
                else
                    out.print(hash+", ");
                c++;
            }
            out.println("");
            out.println("______________________________");
        }
        out.println("");
        int[] stringHashes = minHash.minHashes(hashesPerShingle);
        out.println("Hashes for "+s);
        int c2=0;
        for (int hash : stringHashes) {
            if (c2 == stringHashes.length-1)
                out.print(hash);
            else
                out.print(hash+", ");
            c2++;
        }
        out.println("");
    }

    public static void fillHashList() {
        for (int i=0; i<hashList.length; i++) {
            hashList[i] = new Hash((int) (Math.pow(2, 32)), 900003659);
        }
    }
}