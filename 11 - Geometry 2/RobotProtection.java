import java.util.*;
import java.io.*;

public class RobotProtection{


    public static Vector<Point> convexHull;


    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        
        while(true) {
            int num = io.getInt();

            if(num == 0) {
                break;
            }

            Point[] p = new Point[num];

            for(int i = 0; i < num; i++) {
                int x = io.getInt();
                int y = io.getInt();

                p[i] = new Point(x, y);
            }

            Vector<Point> hull = new Vector<Point>();
            hull = constructConvex(p, num);

            double area = area(hull);

            System.out.println(area);


        }
    }

    // Find orientation, same principle as my solution to previous geometry homework
    public static int ori(Point a, Point b, Point c) {
        int value = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
        if(value == 0) {
            return 0;
        }
        return (value > 0)? 1: 2;
    }

    public static Vector<Point> constructConvex(Point p[], int n) {
        convexHull = new Vector<Point>();

        // Start search from leftmost point
        int left = 0;
        for(int i = 1; i < n; i++) {
            if(p[i].x < p[left].x) {
                left = i;
            }
        }

        int a = left;
        int b;

        do
        {

            // Add point to convex hull
            convexHull.add(p[a]);

            b = (a + 1) % n;

            for(int i = 0; i < n; i++) {
                // If next p[i] is more counterclockwise than p[b]
                if(ori(p[a], p[i], p[b]) == 2) {
                    // if so, change b
                    b = i;
                }
            }

            // set a = b
            a = b;


        } while (a != left); // stop when we reach start point
        return convexHull;
    }

    public static double area(Vector<Point> convexHull) {
        if(convexHull.size() < 3) {
            return 0.0;
        }

        double a = 0;

        Point s = convexHull.get(0);

        // get area of all triangles formed with s, a point i and the point i+1, 
        // so we work our way from one side to another getting the area of triangles
        for(int i = 1; i < convexHull.size() - 1; i++) {
            Point p = convexHull.get(i);
            Point q = convexHull.get(i + 1);
            a += (p.x - s.x) * (q.y - s.y) - (p.y - s.y) * (q.x - s.x);
        }

        // divide by 2 and return
        return a/2.0;
    }

    
}

class Point {
    public int x;
    public int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
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