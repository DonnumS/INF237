import java.io.*;
import java.util.*;


public class Chemist {

    // List for the different letter combinations in the chemical table
    static List<String> chemicals = Arrays.asList(new String[] {"b", "c", "f", "h", "i", "k", "n", "o", "p", "s", "u", "v", "w", "y", "ac", "ag", "al", "am", "ar", "as", "at", "au", "ba", "be", "bh", "bi", "bk", "br", "ca", "cd", "ce", "cf", "cl", "cm", "cn", "co", "cr", "cs", "cu", "db", "ds", "dy", "er", "es", "eu", "fe", "fl", "fm", "fr", "ga", "gd", "ge", "he", "hf", "hg", "ho", "hs", "in", "ir", "kr", "la", "li", "lr", "lu", "lv", "md", "mg", "mn", "mo", "mt", "na", "nb", "nd", "ne", "ni", "no", "np", "os", "pa", "pb", "pd", "pm", "po", "pr", "pt", "pu", "ra", "rb", "re", "rf", "rg", "rh", "rn", "ru", "sb", "sc", "se", "sg", "si", "sm", "sn", "sr", "ta", "tb", "tc", "te", "th", "ti", "tl", "tm", "xe", "yb", "zn", "zr"});
    
    // Used to check if we have checked letter before
    static boolean[] b;

    // Used to check if word is valid
    static boolean check;

    // The word that has to be checked
    static String word;
    

    public static void main(String[] args) {
        // Read using kattio
        Kattio io = new Kattio(System.in, System.out);

        // Number of cases/words to check
        int cases = io.getInt();

        // Runs while there still are more words to be checked
        while(cases > 0) {

            // Read the words
            word = io.getWord();

            // In case there are any empty spaces
            word.concat(" ");
            
            // Get length of current words
            int numLetters = word.length();

            // Init the boolean list
            // Make the list 1 longer than the actual word
            b = new boolean[numLetters + 1];
            
            // Is the word passing the test -- start off as false
            check = false;

            // Init the boolean values in b[] as false to start of
            for(int i = 0; i <= numLetters; i++) {
                b[i] = false;
            }

            // Run the solver on the word from beginning to end
            solve(0, numLetters);

            // Print message according to check value
            if(check) {
                io.println("YES");
            } else {
                io.println("NO");
            }

            // Decrement the cases counter
            cases--;
        }
        io.close();

    }

    public static void solve(int lo, int hi) {

        // Have we checked this letter before?
        if(b[lo]) {
           return;
        }

        // When we have gone through the whole word without errors
        // change the check boolean to true -- the word is OK
        if(lo == hi) {
            check = true;
            return;
        }

        // Does the letter and the one next to it occur in the list of letters
        if (hi-lo > 1 && chemicals.contains(word.substring(lo, lo + 2))){
            // Change to true
            b[lo] = true;

            // Run solver on the shortened word
            solve(lo + 2, hi);
        }

        // Same check for just the 1 letter
        if (chemicals.contains(word.substring(lo,lo+1))){
            // Change to true
            b[lo] = true;

            // Run solver on shorter string
            solve(lo + 1, hi);
        }
    }
    
}



class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
    }



    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}
