import java.util.*;
import java.io.*;

public class Jabuke {
    
    public static int x1, y1, x2, y2, x3, y3;
    public static void main(String[] args) {

        Kattio io = new Kattio(System.in, System.out);
        
        // First get the points for triangle
        x1 = io.getInt();
        y1 = io.getInt();

        x2 = io.getInt();
        y2 = io.getInt();

        x3 = io.getInt();
        y3 = io.getInt();

        int numTrees = io.getInt();
        
        // Init sum as 0
        int sumTrees = 0;
        
        // Print area of triangle
        io.println(getArea(x1,y1,x2,y2,x3,y3));
        
        // Check if points are inside area
        for(int i = 0; i < numTrees; i++) {
            int px = io.getInt();
            int py = io.getInt();
            if(isInside(px, py)) {
                sumTrees += 1;
            }
        }
        // Print the sum of trees inside area
        io.println(sumTrees);
        io.close();

        
    }
    
    // Check to see if points is inside area
    public static boolean isInside(int x, int y) {
        double A = getArea(x1,y1,x2,y2,x3,y3);
        double A1 = getArea(x,y,x2,y2,x3,y3);
        double A2 = getArea(x1,y1,x,y,x3,y3);
        double A3 = getArea(x1,y1,x2,y2,x,y);

        return (A == A1 + A2 + A3);
    }
    
    // Get area
    public static double getArea(int x1, int y1, int x2, int y2, int x3, int y3) {
        int absolute = Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)); 
        return absolute / 2.0;
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