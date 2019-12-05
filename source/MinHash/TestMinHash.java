import static java.lang.System.*;

public class TestMinHash {

    public static void main(String[] args) {
        String s = "Unhas Negrass 1";
        MinHash minHash = new MinHash(10);
        String[] shingles = minHash.makeShingles(s);
        out.println(s+"\n");
        for (String shingle : shingles) {
            out.println(shingle);
            out.print("string2hash("+shingle+") = ");
            int str2hash = MathWorksFunctions.string2hash(shingle, "djb2");
            out.println(str2hash);
        }
    }
}