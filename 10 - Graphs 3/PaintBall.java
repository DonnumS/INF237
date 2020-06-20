import java.util.*;
import java.io.*;

public class PaintBall {
    public static int N;
    public static int M;

    // Keeps track of who sees who
    public static boolean bpGraph[][];

    // Keeps track of who shoots who
    // Maximum bipartite matching
    public static int[] whoShotsWho;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        N = io.getInt();
        M = io.getInt();

        bpGraph = new boolean[N+1][N+1];

        for(int i = 0; i < M; i++) {
            int one = io.getInt();
            int two = io.getInt();

            // Add to bpGrapgh
            bpGraph[one][two] = true;
            bpGraph[two][one] = true;
        }

        // Find max bipartite mathcing of graph 
        maxBPM(bpGraph);


    }

    public static void maxBPM(boolean[][] graph) {
        // Keep track of who shots who (0'th index left empty)
        whoShotsWho = new int[N + 1];

        // Init all as -1
        for(int i = 1; i <= N; i++) {
            whoShotsWho[i] = -1;
        } 

        // Number of shots
        int count = 0;


        for(int j = 1; j <= N; j++) {
            // Init new boolean[]
            boolean[] hasShot = new boolean[N+1];
            for(int i = 1; i <= N; i++) {
                // Marks as false
                hasShot[i] = false;
            }

            // If true, we have done a shot -> increase count
            if(findTarget(graph, j, hasShot)) {
                // Increment shots
                count++;
            }
        }

        // To be possible, num shots have to equal num players
        if(count != N) {
            System.out.println("Impossible");
        } else {
            // Print who is shot
            for(int i = 1; i <= N; i++) {
                System.out.println(whoShotsWho[i]);
            }
        }


    }
    
    // A recursive dfs function. 
    // Returns true when mathcing with u is possible
    public static boolean findTarget(boolean[][] graph, int u, boolean[] shot) {
        for(int i = 1; i <= N; i++) {
            // If u can see i and i has not been shot
            if(graph[u][i] && !shot[i]) {
                // Mark i as shot
                shot[i] = true;

                // If i not shot someone or findTarget for i retursn true
                if(whoShotsWho[i] < 0 || findTarget(graph, whoShotsWho[i], shot)) {

                    // Let i shoot u
                    whoShotsWho[i] = u;

                    // Then return true
                    return true;        
                }
            }
        }
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

