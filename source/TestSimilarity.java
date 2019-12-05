import static java.lang.System.*;

import java.util.ArrayList;
import java.util.List;

public class TestSimilarity {

    static Hash[] listHash;
    static MinHash minHash;
    static int nmrCharsPerShingle = 3; 
    static int nmrHashes = 225;

    public static void main(String[] args) {
        listHash = new Hash[nmrHashes];
        minHash = new MinHash(nmrHashes, nmrCharsPerShingle, listHash);
        fillHashList();

        String s = "As Crónicas de Gelo e Fogo";
        Book b1 = new Book("A Guerra dos Tronos, As Crónicas de Gelo e Fogo Vol 1", "Disney", Category.KIDS);
        Book b2 = new Book("A Tormenta de Espadas, As Crónicas de Gelo e Fogo Vol 5", "Disney", Category.KIDS);
        Book b3 = new Book("O Festim dos Corvos, As Crónicas de Gelo e Fogo Vol 7", "João da Silva Correia", Category.LITERATURE);

        out.println("_______________________________________________");
        out.println("Distance S-B1: "+checkSimilarity(getMinHashes(s), getMinHashes(b1.title()))+"%");
        out.println("_______________________________________________");
        out.println("Distance S-B2: "+checkSimilarity(getMinHashes(s), getMinHashes(b2.title()))+"%");
        out.println("_______________________________________________");
        out.println("Distance S-B3: "+checkSimilarity(getMinHashes(s), getMinHashes(b3.title()))+"%");
        out.println("_______________________________________________");
    }

    public static double checkSimilarity(int[] a, int[] b) {
        int intersections = getIntersections(a, b);
        out.println(intersections+"/"+a.length*2+"-"+intersections);
        return ((double) intersections/((double) (a.length*2)-(double) intersections))*100;
    }

    private static int getIntersections(int[] a, int[] b) {
        // for (int i : a) {
        //     out.print(i+",");
        // }
        // out.println("");
        // for (int j : b) {
        //     out.print(j+",");
        // }
        // out.println("");
        int intersections=0; 
        for (int i=0; i<a.length; i++) {
            for (int j=0; j<b.length; j++) {
                if (a[i] == b[j])
                    intersections++;
            }
        }
        out.println("intersection:");
        out.println(intersections);
        return intersections;
    }

    public static int[] getMinHashes(String s) {
        out.println(s);
        List<int[]> hashesPerShingle = new ArrayList<>();
        String[] shingles = minHash.makeShingles(s);
        for (String shingle : shingles) {
            int str2hash = MathWorksFunctions.string2hash(shingle, "djb2");
            int[] hashes = minHash.getHashesForShingle(str2hash);
            hashesPerShingle.add(hashes);
        }
        return minHash.stringHashes(hashesPerShingle);
    }

    public static void fillHashList() {
        for (int i=0; i<listHash.length; i++) {
            listHash[i] = new Hash((int) (Math.pow(2, 32)), 900003659);
        }
    }
}