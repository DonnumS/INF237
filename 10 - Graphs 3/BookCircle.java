import java.util.*;
import java.io.*;

public class BookCircle {
    
    public static ArrayList<ArrayList<Integer>> adj;

    public static int[] forward;
    public static int[] backward;
    public static boolean[] visited;

    public static Map<String, Integer> mapBookId;
    
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        // Num boys and girls
        int B = io.getInt();
        int G = io.getInt();

        // Boolean list
        visited = new boolean[G];

        // Adjacency list to store every connection
        adj = new ArrayList<ArrayList<Integer>>();

        // Store booknames with id of boy who read it
        mapBookId = new HashMap<String, Integer>();

        for(int i = 0; i < B; i++) {
            adj.add(new ArrayList<Integer>());
        }


        for(int boyId = 0; boyId < B; boyId++) {
            // Will not make use of name, instead use id (i'th)
            String name = io.getWord();
            int numBooks = io.getInt();

            for(int books = 0; books < numBooks; books++) {
                String bookName = io.getWord();
                mapBookId.put(bookName, boyId);
            }
        }

        for(int girlId = 0; girlId < G; girlId++) {
            // Same here i'th girl has id i
            String name = io.getWord();
            int numBooks = io.getInt();

            for(int books = 0; books < numBooks; books++) {
                String bookName = io.getWord();

                // Get id of boy that has read the same book
                int boyId = mapBookId.get(bookName);

                // Add girlId to boyId in adj list to indicate that they link by having read the same book
                adj.get(boyId).add(girlId);
            }


        }

        // Boy to girl connection
        forward = new int[B];

        // Girl to boy connection
        backward = new int[G];

        // Fill list with -1 initially
        
        for(int i = 0; i < B; i++) {
            forward[i] = -1;
        }

        for(int i = 0; i < G; i++) {
            backward[i] = -1;
        }
        // The min number of presentations we need to hold
        int numPresentations = 0;

        // Compute the number of presentations
        // Find maximum cardinality bipartite matching

        // Start by iterating over all boys, and the girls they connect with
    
        

        // Now iterate over connections to the boys 
        for(int boyId = 0; boyId < B; boyId++) {
            // Skip there is a connection to the boy
            if(forward[boyId] != -1) {
                continue;
            }

            // Init new visited boolean
            visited = new boolean[B];

            // Run dfs
            if(dfs(boyId)) {

                // +1 for each boy that we can dfs from
                numPresentations += 1;
            }
        }

        System.out.println(numPresentations);
    }

    public static void fill(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            arr[i] = -1;
        }
    }

    public static boolean dfs(int src) {
        // Return false if visited
        if(visited[src]) {
            return false;
        }

        // Mark
        visited[src] = true;


        // Iterate over adjacent girls for said boy (src)
        for(int girlId : adj.get(src)) {
            //If no coonection to girl, or dfs(backwardgirldId) (recursive)
            if(backward[girlId] == -1 || dfs(backward[girlId])) {
                
                // store connections
                forward[src] = girlId;
                backward[girlId] = src;

                // And return true;
                return true;
            }
        }

        // Else we return false
        return false;
        
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
