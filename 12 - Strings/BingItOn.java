import java.util.*;
import java.io.*;

public class BingItOn {
    // Use hashmap to store frequencies
    public static HashMap<String, Integer> freq;
    
    
    public static void main(String[] args) {
        
        Kattio io = new Kattio(System.in, System.out);

        int n = io.getInt();

        // Use hashmap to store frequencies
        
        freq = new HashMap<String, Integer>();
        
        // Loop through every word
        for(int i = 0; i < n; i++) {
            String curr = io.getWord();

            // Find frequency of word, and also add al prefixes to hashmap
            int freq = solve(curr);
            
            // Print the freq
            io.println(freq);

            io.flush();
            
        }
        io.close();
    }

    public static int solve(String word) {

        // First find frequency
        int f;
        
        // If hashmap contains word
        if(freq.containsKey(word)) {
            // Set f = words frequency
            f = freq.get(word);
        } else {
            // Else we set 0
            f = 0;
        }

        // Loop through every prefix of the word, including whole word and add to hashmap
        for(int j = 1; j <= word.length(); j++) {
            // Find frequency the same way
            
            // smaller = prefix from 0 to j
            String smaller = word.substring(0, j);
            int fSmaller;
            
            //If hashmap contains word
            if(freq.containsKey(smaller)) {
                
                // Update ith with +1
                fSmaller = freq.get(smaller) + 1;
            } else {
                
                // Else set = 1
                fSmaller = 1;
            }
            // And update hashmap with new frequency
            freq.put(smaller, fSmaller);
            
            // Repeat until j is equal to word length, until the whole word is added
        }
        
        // Return the frequency found
        return f;
        
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