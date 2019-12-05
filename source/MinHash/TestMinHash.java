import static java.lang.System.*;

import java.util.ArrayList;
import java.util.List;

public class TestMinHash {

    public static List<int[]> hashesPerShingle = new ArrayList<>();

    public static void main(String[] args) {
        String s = "diogo costa correia";
        MinHash minHash = new MinHash(10, 15);
        String[] shingles = minHash.makeShingles(s);
        out.println(s+"\n");
        for (String shingle : shingles) {
            out.println(shingle);
            out.print("string2hash("+shingle+") = ");
            int str2hash = MathWorksFunctions.string2hash(shingle, "djb2");
            out.println(str2hash);
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
        int[] stringHashes = minHash.stringHashes(hashesPerShingle);
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
}