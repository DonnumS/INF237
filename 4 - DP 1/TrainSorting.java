import java.util.*;
import java.io.*;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

public class TrainSorting {   

    // The list of trains read from input
    static int[] trains;
    static int[] dec;
    static int[] inc;

    static int num;

    public static void main(String[] args) {
        // Read and print using kattio
        Kattio io = new Kattio(System.in, System.out);

        // Get num trains to sort
        num = io.getInt();

        // Init new list for traincarts read from input
        trains = new int[num];
        int counter = num;

        while(counter > 0) {

            // Read the train weight and add it too the list
            for (int i = 0; i < num; i++) {
                trains[i] = io.getInt();

                // Decrease counter to stop while loop
                counter--;
            }
            
        }

        // Init max length as 0 to start
        int m = 0;

        // Run lis() and lds() that produces the lists inc[] and dec[] respectively
        // Used to find longest possible sequence by calculating inc[i] + dec[i] - 1
        // later for each i
        lis();
        lds();

        // inc[i] + dec[i] - 1 gives the longest sequence possible starting from the i th element 
        // loop through each elemnt i in inc[] and dec[] and store the larges number in m
        // this is the longest sequence
        for (int i = 0; i < num; i++) {
            // Change m if new value is larger
            m = Math.max(m, inc[i] + dec[i] - 1);
        }

        
        // Print out the length of longest sequence
        io.println(m);
        io.close();
    }

    // Longest decreasing subsequence method
    static void lds() {
        // Init dec
        dec = new int[num];
        for (int i = num - 1; i >= 0; i--) {
            dec[i] = 1;
            for (int j = i + 1; j < num; j++) {
                if (trains[i] > trains[j] && dec[i] < 1 + dec[j]) {
                    dec[i] = 1 + dec[j];
                }
            }
        }
    }

    // Longest decreasing subsequence method
    static void lis() {
        // Init inc
        inc = new int[num];
        for (int i = num - 1; i >= 0; i--) {
            inc[i] = 1;
            for (int j = i + 1; j < num; j++) {
                if (trains[i] < trains[j] && inc[i] < 1 + inc[j]) {
                    inc[i] = 1 + inc[j];
                }
            }
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