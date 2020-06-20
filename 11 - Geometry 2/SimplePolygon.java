import java.io.*;
import java.util.*;

public class SimplePolygon {

    // Create half of hull then add remaining points according to coordinates
    public static void main(String[] args) {
        
        Kattio io = new Kattio(System.in, System.out);
        int cases = io.getInt();

        while (cases > 0) {
            
            int n = io.getInt();

            // All points
            List<Point> points = new ArrayList<>();

            // Holds points added to polygon
            List<Point> polygon = new ArrayList<>();

            
            for (int i = 0; i < n; i++) {
                int x = io.getInt();
                int y = io.getInt();
                points.add(new Point(x, y, i));
            }

            // Sort according to x value, then y value if there is a tie
            Collections.sort(points, (a, b) -> {
                int result = Integer.compare(a.x, b.x);
                if (result == 0) {
                    result = Integer.compare(a.y, b.y);
                }
                return result;
            });

            // Solve for points
            solve(points, polygon);
            
            for (int i = 0; i < n; i++) {
                int id = polygon.get(i).id;
                io.print(id + " ");
            }
            io.print("\n");
            io.flush();
            cases--;

        }
        io.close();

    }

    public static void solve(List<Point> points, List<Point> polygon) {
        // Firstly create half of hull
        // We start from the point with the lowest x value
        for (int i = 0; i < points.size(); i++) {
            // While polygon size is atleast 2, check if adding next point 
            // gives a CW or a CCW turn (might also be collinear) with respect 
            // to previouslyy added points in polygon list
            while (polygon.size() >= 2 && ori(points.get(i), polygon.get(polygon.size() - 1), polygon.get(polygon.size() - 2)) > 0) {
                // If CCW turn remove point from polygon
                polygon.remove(polygon.size() - 1);

            }
            // Add current point to polygon
            polygon.add(points.get(i));
        }

        // for(Point p : polygon) {
        //     System.out.println(p.x + " " + p.y + " is in the hull " + p.id + " = id");
        // }

        // Now we add the remaining points
        // These points are sorted by their coords
        // so we just add starting from first in list and moving to last
        for (int i = points.size() - 1; i >= 0; i--) {
            // Is point in polygon?
            if (!polygon.contains(points.get(i))) {
                // If not we add it
                polygon.add(points.get(i));
            }
        }
    }
    // Used to find out if turn is cw ccw, or collinear
    public static int ori(Point p0, Point p1, Point p2) {

        int dx1 = p1.x - p0.x; 
        int dy1 = p1.y - p0.y;
        int dx2 = p2.x - p0.x; 
        int dy2 = p2.y - p0.y;
        if (dx1*dy2 > dy1*dx2) return 1; // CCW turn
        if (dx1*dy2 < dy1*dx2) return -1; // CW turn
        return 0; // Collinear
    }

    public static class Point {

        int x; // X coord
        int y; // Y coord
        int id; // id of point (= ith point read from input)

        Point(int x, int y, int id) {
            this.x = x;
            this.y = y;
            this.id = id;
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