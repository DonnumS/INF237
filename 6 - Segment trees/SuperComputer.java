import java.util.*;
import java.io.*;

public class SuperComputer {


    public static int[] st;
    public static int offset;
   

    public static void main(String[] args) throws IOException{
        Kattio io = new Kattio(System.in, System.out);

        // Get size and num commands
        int size = io.getInt();
        int commands = io.getInt();

        // s is initial size we use for the segment tree, offset init as 2
        int s = 2;
        offset = 2;

        // While 2 x size + 3 > s -> mutiply s by 2 until we get a satisfying s
        while((2*size) + 3 > s) {
            s *= 2;
        }

        // While size + 2 > offset, we need to multiply offset by 2
        while(size + 2 > offset) {
            offset *= 2;
        }

        // Create st with size s
        // No need to change anything with it since all elemnts starts as 0´s
        st = new int[s];
        
        
        // Run through commands
        while (commands > 0) {
            // Get command
            String com = io.getWord();

            // If flip command
            if(com.charAt(0) == 'F') {
                // Get index that must be flipper
                int toGetFlipped = io.getInt();

                // Flip index and update st
                flip(toGetFlipped);
               
            } 
            // Else we do a count
            else {
                // Get lo and hi
                int lo = io.getInt();
                int hi = io.getInt();

                // Init returnSum as sum from lo to hi
                int returnSum = getSum(lo, hi);

                // Return said sum
                System.out.println(returnSum);
                
            }
            
            // Decrease commands
            commands--;
        }
        io.close();
    }
    

    

    // Similar to the update function from the segmentree lecture, with some exceptions
    private static void flip(int i) {
        int position = i + offset;

        // Get value so that we can change to the opposite
        int newVal;
        if (st[position] == 0)
            newVal = 1;
        else
            newVal = 0;

        // Difference d = the new value - st[position]
        int d = newVal - st[position];

        // Walk up and change each 
        while (position > 0) {
            // Change value in st
            st[position] += d;
            
            // Get new position by dividing by 2
            position /= 2;
        }
    }

    public static int getSum(int lo, int hi) {
        int L = lo + offset - 1;
        int R = hi + offset + 1;
        int ans = 0;


        // Code from segment tree lecture, to get the sum
        while (true) {
            // Check if the walked so they need to count
            boolean LRIGHT = (L % 2) == 0;
            boolean RLEFT = (R % 2) == 1;
            
            // Move up by dividing by 2
            L /= 2;
            R /= 2;
            
            // Break when we meet
            if (R == L)
                break;

            
            if (LRIGHT)
                ans += st[(2 * L) + 1];
            if (RLEFT)
                ans += st[2 * R];
        }
        
        // Return sum
        return ans;
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



