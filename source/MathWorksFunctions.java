/*
    THE FUNCTIONS IN THIS FILE WERE TAKEN FROM THE WEBSITE www.mathworks.com AS FUNCTIONS IN MATLAB
    I JUST CONVERTED THEM FROM MATLAB TO JAVA
*/

public class MathWorksFunctions {

    public static int string2hash(String str, String type) {

        // % This function generates a hash value from a text string
        // %
        // % hash=string2hash(str,type);
        // %
        // % inputs,
        // %   str : The text string, or array with text strings.
        // % outputs,
        // %   hash : The hash value, integer value between 0 and 2^32-1
        // %   type : Type of has 'djb2' (default) or 'sdbm'
        // %
        // % From c-code on : http://www.cse.yorku.ca/~oz/hash.html 
        // %
        // % djb2
        // %  this algorithm was first reported by dan bernstein many years ago 
        // %  in comp.lang.c
        // %
        // % sdbm
        // %  this algorithm was created for sdbm (a public-domain reimplementation of
        // %  ndbm) database library. it was found to do well in scrambling bits, 
        // %  causing better distribution of the keys and fewer splits. it also happens
        // %  to be a good general hashing function with good distribution.
        // %
        // % example,
        // %
        // %  hash=string2hash('hello world');
        // %  disp(hash);
        // %
        // % Function is written by D.Kroon University of Twente (June 2010)

        int[] strVals = getAsciiValues(str);
        int hash;
        switch(type) {
            case "djb2":
                hash = 5381;
                for (int val : strVals) {
                    hash = (int) ((hash*33 + val) % (Math.pow(2, 32)-1));
                }
                break;
            case "sdbm":
                hash = 0;
                for (int val : strVals) {
                    hash = (int) ((hash*65599 + val) % (Math.pow(2, 32)-1));
                }
                break;
            default:
                return 0;
        }
        return hash;
    }

    private static int[] getAsciiValues(String str) {
        int[] asciiVals = new int[str.length()];
        char[] arrayOfChars = MinHash.stringToArrayOfChars(str);
        for (int i=0; i<arrayOfChars.length; i++) {
            char c = arrayOfChars[i];
            asciiVals[i] = (int) c;
        }
        return asciiVals;
    }
}