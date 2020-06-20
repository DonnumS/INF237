import java.util.*;
import java.io.*;

public class LRS {

    // Suffix class
    public static class Suffix implements Comparable<Suffix>{

        int index; // index of suffix
        int rank;  // rank -> used for sorting
        int next;  // next -> used for sorting
        
        // Constructor
        public Suffix(int index, int rank, int next) {
            this.index = index;
            this.rank = rank;
            this.next = next;
        }
        
        // Compare function
        // First compare rank, then next rank
        // Used to sort the suffixes
        public int compareTo(Suffix other) {
            if (rank != other.rank) return Integer.compare(rank, other.rank); 
            return Integer.compare(next, other.next);
        }

    }

    // Used to construct suffix array from string
    public static int[] suffixarray(String str) {
        int n = str.length();
        
        // Array for suffix objects
        Suffix[] s = new Suffix[n];
        
        // Create the suffixes
        for(int i = 0; i < n; i++) {
            // Suffix with index i
            // rank = first char - 'a'
            // next set to 0
            s[i] = new Suffix(i, str.charAt(i) - 'a', 0);
        }
        
        // Now set next ranks
        for (int i = 0; i < n; i++) {
            // Whule i + 1 is shorter than word length
            if(i + 1 < n) {
                // Next = next elements rank
                s[i].next = s[i+1].rank;
            } else {
                // Else -1
                s[i].next = -1;
            }
        }
        
        // Sort the suffixes according to rank and next
        Arrays.sort(s);

        // Used to get index
        int[] ind = new int[n];
        
        for(int length = 4; length < 2*n; length *= 2) {
            
            // init rank as 0
            int rank = 0;
            
            // Init prev as rank of first suffix
            int prev = s[0].rank;
            
            // Change rank of first suffix to rank
            s[0].rank = rank;
            
            // Store index
            ind[s[0].index] = 0;
            for(int i = 1; i < n; i++) {
                
                // If suffix.rank == previous and next = previous next
                if(s[i].rank == prev && s[i].next == s[i-1].next) {
                    // Change prev
                    prev = s[i].rank;
                    
                    // Set rank
                    s[i].rank = rank;
                } else {
                    // Change prev
                    prev = s[i].rank;
                    
                    // Set ++rank
                    s[i].rank = ++rank;
                }
                
                // Store index
                ind[s[i].index] = i;
            }

            for (int i = 0; i < n; i++) {
                int nVal = s[i].index + length / 2;
                
                if(nVal < n) {
                    
                    // Set suffix.next = s[ind[nVal].rank
                    s[i].next = s[ind[nVal]].rank;
                } else {
                    
                    // Else -1
                    s[i].next = -1;
                }
            }
            
            // Sort again
            Arrays.sort(s);
        }  
        
        // Now we put the current index values into an array
        int[] finishedArray = new int[n];

        for(int i = 0; i < n; i++) {
            finishedArray[i] = s[i].index;
        }
        
        // Return array
        return finishedArray;
    }

    // Construct lcp array with kasai
    public static int[] lcp(String word, int[] sufArr) {
        // Get length 
        int n = sufArr.length;

        // Init the lcp array and inverted
        // Inverted is the inverse of sufArr
        int[] lcpArray = new int[n];
        int[] inverted = new int[n];
    
        // Construct inverted[]
        // If sufArr[0] = 4;
        // inverted[4] = 0
        for(int i = 0; i < n; i++) {
            inverted[sufArr[i]] = i;
        }
    
        // K is length of lcp
        int k = 0;
        
        // Loop through suffixes
        for(int i = 0; i < n; i++) {
            
            // If last suffix, set k = 0
            if (inverted[i] == n-1) {
                k = 0;
                continue;
            }
            
            // Index of next suffix
            int j = sufArr[inverted[i] + 1];
            
            // While k + i and j is shorter than word
            // and has same character
            // increment k
            while (i+k< n && j+k<n && word.charAt(i+k) == word.charAt(j+k)) {
                k++;
            }
            
            // Set value k in lcp
            lcpArray[inverted[i]] = k;
            
            // decrease k
            if(k>0) {
                k--;
            }
        }

        return lcpArray;
    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        String w = io.getWord();

        // Create the suffix array
        int[] suf = new int[w.length()];
        suf = suffixarray(w);

        // Create lcp array from suffix array
        int[] lcpArray = new int[w.length()];
        lcpArray = lcp(w, suf);


        // lcp is largest lcp value
        // index is index of largest lcp value
        int lcp = -1;
        int index = -1;
        for(int i = 0; i < w.length(); i++) {
            if(lcpArray[i] > lcp) {
                lcp = lcpArray[i];
                index = i;
            }
        }

        // Print substring from index to index plus lcp value
        io.println(w.substring(suf[index], suf[index] + lcp));

        io.flush();
        io.close();


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