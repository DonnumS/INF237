import java.util.*;
import java.io.*;

public class Turbo {

    // Maximum number of elements
    static int MAX = 100000;
    public static int numNumbers;

    // List of numbers
    public static int[] numbers = new int[MAX + 1];

    // Position of number represented by index in list
    public static int[] indexList = new int[MAX + 1];

    // Fenwick Tree
    public static FenwickTree fenwickTree = new FenwickTree(MAX + 1);


    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        numNumbers = io.getInt();


        // Construct the tree from input
        for(int i = 1; i <= numNumbers; i++) {
            int n = io.getInt();
            // Add number to list at index i
            numbers[i] = n;

            // Save index of number n at index n
            indexList[n] = i;

            // Update
            fenwickTree.update(i, 1);
        }

        // Now we do the sorting
        int lo = 1;
        int hi = numNumbers;

        for(int i = 1; i <= numNumbers; i++) {
            // Is it an even phase?
            if(i % 2 == 0) {
                fenwickTree.update(indexList[hi], -1);
                System.out.println(fenwickTree.rsq(indexList[hi], numNumbers));
                hi--;
            } 
            // Else it is an odd phase
            else {
                fenwickTree.update(indexList[lo], -1);
                System.out.println(fenwickTree.rsq(1, indexList[lo]));
                lo++;

            }
        }
        io.close();


    }
}   

class FenwickTree {

    public int[] ft;

    public FenwickTree(int size) {
        this.ft = new int[size];
    }

    // Range sum query from 1 to index
    public int rsq(int index) {
        int result = 0;
        while (index > 0) {
            result += ft[index];
            
            index -= index & (-index);

        }
        return result;
    }

    // Range sum query from "from" to "to"
    public int rsq(int from, int to) {
        return rsq(to) - rsq(from - 1);
    }

    public void update(int index, int change) {
        while(index < ft.length) {
            // Update value in fenwicktree
            ft[index] += change;

            index += index & (-index);
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

