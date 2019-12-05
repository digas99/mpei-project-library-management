public class Hash {

    private int a;
    private int b;
    private int odd;
    private int min = 0;
    private int max;

    public Hash(int size, int oddN) {
        max = size;
        odd = oddN;
        a = -random(min, max);
        b = -random(min, max); 
    }

    public int a() {
        return a;
    }

    public int b() {
        return b;
    }

    public int oddNmr() {
        return odd;
    }

    private int random(int min, int max) {
        int range = max-min+1;
        return (int) (Math.random() * range) + min;
    }

    @Override
    public String toString() {
        return "a: "+a+"; b: "+b+"; oddNmr: "+odd;
    }
}