import java.util.*;
import java.io.*;

public class CountingTriangles {
    public static Line[] allLines;

    public static int numLines;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        while(true) {
            numLines = io.getInt();
            
            // Stop when we read a 0
            if(numLines == 0) {
                break;
            }

            // Init list of lines
            allLines = new Line[numLines];

            // Add all lines to the list
            for(int i = 0; i < numLines; i++) {
                double x1 = io.getDouble();
                double y1 = io.getDouble();
                double x2 = io.getDouble();
                double y2 = io.getDouble();
                Point p1 = new Point(x1, y1);
                Point p2 = new Point(x2, y2);
                allLines[i] = new Line(p1, p2);
            }

            int triangles = 0;
            // Iterate over all combinations of 3 lines
            for(int x = 0; x < numLines; x++) {
                for(int y = x+1; y < numLines; y++) {
                    for (int z = y+1; z < numLines; z++) {
                        // Does the three lines form a triangle?
                        if(makesTriangle(allLines[x], allLines[y], allLines[z])) {
                            // If so, increase counter
                            triangles++;
                        }
                    }
                }
            }
            
            // Print num triangles
            System.out.println(triangles);
        }
    }

    // This returns true if a triangle can be made by the three lines
    public static boolean makesTriangle(Line one, Line two, Line three) {
        // Check if al three lines intersect
        if(!intersect(one, two) || !intersect(one, three) || !intersect(two, three)) {
            // If not return false
            return false;
        }
        
        // Else return true
        return true;
    }
    
    // Find out if two lines intersect
    public static boolean intersect(Line one, Line two) {
        double oneX1 = one.p1.x;
        double oneY1 = one.p1.y;
        double oneX2 = one.p2.x;
        double oneY2 = one.p2.y;

        double twoX1 = two.p1.x;
        double twoY1 = two.p1.y;
        double twoX2 = two.p2.x;
        double twoY2 = two.p2.y;

        int ori1 = orientation(oneX1, oneY1, oneX2, oneY2, twoX1, twoY1);
        int ori2 = orientation(oneX1, oneY1, oneX2, oneY2, twoX2, twoY2);
        int ori3 = orientation(twoX1, twoY1, twoX2, twoY2, oneX1, oneY1);
        int ori4 = orientation(twoX1, twoY1, twoX2, twoY2, oneX2, oneY2);

        if( ori1 != ori2 && ori3 != ori4) {
            return true;
        }

        return false;


    }
    
    // Find orientation for three points
    public static int orientation(double x1, double y1, double x2, double y2, double x3, double y3) {
        double value = (y2 - y1) * (x3 - x2) - (x2 - x1) * (y3 - y2);

        if (value == 0) {
            return 0;
        } else {
            return (value > 0)? 1: 2;
        }
    }
    
    // Class for line
    static class Line {
        
        // Made out of 2 points
        public Point p1, p2;
    
        Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }
    
    // Class for point
    static class Point {
        
        // x and y coords for point
        public double x, y;
    
        Point(double x, double y) {
            this.x = x;
            this.y = y;
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


