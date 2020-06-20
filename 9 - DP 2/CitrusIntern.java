import java.util.*;
import java.io.*;

public class CitrusIntern {
    public static int members;
    public static ArrayList<Person> memberList;
    
    // Use long because we can get very big answers
    public static long[] cost;
    public static long[] up;
    public static long[] down;
    public static long[] in;

    // Mark people as not being boss
    public static boolean[] notBoss;
    
    
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

         
        members = io.getInt();
        memberList =  new  ArrayList<Person>();
        cost = new long[members];
        up = new long[members];
        down = new long[members];
        in = new long[members];
        
        // Find out who the boss is by marking persons with 
        // a supervisor as true;
        notBoss = new boolean[members];
        
        for(int i = 0; i < members; i++) {
            // Get price of bribing this person
            // And num subordinates of this person, numSubs
            Long price = io.getLong();
            int numSubs = io.getInt();

            // Save price in list
            cost[i] = price;

            // Initiate the new person
            Person member = new Person(i, price);

            // Now read all subordinates
            for(int j = 0; j < numSubs; j++) {
                // Get id of sub and add to curr persons children list
                int subordinate = io.getInt();
                member.subordinates.add(subordinate);

                // Mark the sub as notBoss[subordinate] = true to indicate that it is not boss
                notBoss[subordinate] = true;
            }

            // Add member to list of all members in corporation
            memberList.add(member);
        }

        // Now we need to find out who the boss is, this is where we have to start solving from

        int bossID = -1;
        for(int i = 0; i < members; i++) {
            // Check if false in notBoss[]
            if(!notBoss[i]) {
                // Store id and break to stop loop;
                bossID = i;
                break;
            }
        }

        // Solve problem using dfs
        // We start dfs from boss
        performBribe(bossID);

        // Init returnvalue
        long returnValue = 0;
        //Return lowest value of down, and in from boss
        if(in[bossID] > down[bossID]) {
            returnValue = down[bossID];
        } else {
            returnValue = in[bossID];
        }

        // Print result
        System.out.println(returnValue);
    }

    // Use dfs to solve problem
    public static void performBribe(int src) {
        up[src] = 0;
        down[src] = Integer.MAX_VALUE;

        in[src] = cost[src];

        long d = Long.MAX_VALUE;
        
        // Iterate over children
        for(int child : memberList.get(src).subordinates) {
            // Run dfs from child
            performBribe(child);

            // Add up[] of child to in[] of parent
            in[src] += up[child];

            // Add minimum of in[] child and down[] child to up[parent]
            up[src] += Math.min(in[child], down[child]);

            // Get diff of in[] and down[] from child
            long dif = in[child] - down[child];

            // If dif < d, change d
            if(dif < d) {
                d = dif;
            }
        }
        
        // Down[] src = up[src]. If d <= 0, also add d
        if(d >= 0) {
            down[src] = up[src] + d;
        } else {
            down[src] = up[src];
        }
        
    }

    
}

class Person {
    int id; // Identify the Person
    long cost; // Cost to bribe
    ArrayList<Integer> subordinates; // List of subordinates in company
    ArrayList<Integer> parents; // List of who person is sub for. Might also use this


    public Person(int id, long cost) {
        this.id = id;
        this.cost = cost;
        this.subordinates = new ArrayList<Integer>();
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

