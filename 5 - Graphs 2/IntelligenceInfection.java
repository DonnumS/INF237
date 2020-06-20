import java.io.*;
import java.util.*;

public class IntelligenceInfection {

    public static int spyCount;
    public static int enemyCount;
    public static int connectionsCount;
    
    // The messages that has to be sent
    public static int msg;
    
    // List of spies
    public static Spy[] spyList;
    
    // List of enemies
    public static int[] enemyList;
    
    // Used to check if we have visited before
    public static boolean[] visited;
    public static boolean[] cycle; 

    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio(System.in, System.out);
        
        // Read the different inputs
        spyCount       = io.getInt();
        enemyCount     = io.getInt();
        connectionsCount = io.getInt();

        // Now we make a list of all the spies and one for enemies
        spyList = new Spy[spyCount];
        enemyList = new int[enemyCount];

        // Init boolean[] to determine if spy has been visited
        visited = new boolean[spyCount];
        
        // Init number of messages as 0
        msg = 0;

        // Init a new Spy at every index of spy lsit
        for (int i = 0 ; i < spyCount; i++) { 
            spyList[i] = new Spy(i); 
        }

        
        // Now we read the different connections and add those to the 
        // corresponding spies in the list
        for (int i = 0 ; i < connectionsCount; i++) {
            // The two spies, identified by num
            int one = io.getInt();
            int two   = io.getInt();

            // Add two as connection for spy one
            // and one as a parent for two
            spyList[one].children.add(two);
            spyList[two].parents.add(one);
            
        }

       
        // Now we read the enemies and flip boolean
        // of corresponding spy to indicate that it 
        // is an enemy
        for (int i = 0 ; i < enemyCount; i++) {
            // Get "id" of spy
            int e = io.getInt();

            // Add enemy to enemylist
            enemyList[i] = e;
            
            // Spy is marked as enemy
            spyList[enemyList[i]].badGuy = true; 
        }

        // Now we can start the process of finding out how
        // many messages we must send

        // Start with checking enemies
        for (int i = 0 ; i < enemyCount; i++) {
            // Get enemy id from enemyList and run markEnemies(enemyID)
            // For each enemy, if they have "parents" we must send a message 
            //privately, and the same for the parents parents and so forth 
            markEnemies(spyList[enemyList[i]]);
        }

        // Now we have visted all spies with an enemy in their chain of children.
        // Now we traverse the spies with no parents
        // These are also the ones what had an enemy as parent previously
        boolean cont = true;
        while (cont) {
            // Flip cont so that we can stop looking
            // if noe more spies fit the criteria
            cont = false;

            // Iterate over all the spies one by one
            for (int i = 0; i < spyCount; i++){
                // If spy not visited
                if(!visited[i]) {  
                    // If spy has no parents
                    if(spyList[i].parents.size() == 0) {
                        
                        // Add +1 to messages
                        msg++;

                        // Flip continue to look for another spy that fits criteria
                        cont = true;

                        // Now we run markChildren on the children to mark all spies that are child, and their childs and so forth
                        markChildren(spyList[i]);
                    }
                }
            }
        }

        // Now we need to check any remaining spies
        // Init the maxLength as -1
        // This is the length of the chain of spies
        int maxLength = 1;
        // The spy with longest chain of connections and the length
        int longest;
        int length;
        
        while (maxLength > 0) {
            // Change maxLength to -1
            maxLength = -1;
            longest = -1;

            // Iterate over all the spies
            for (int i = 0 ; i < spyCount; i++) {
                // If not visited
                if(!visited[i]) {
                    // Init cycle[] boolean to stop when we reach a cycle
                    cycle = new boolean[spyCount];

                    // Get the length for current spy
                    length = findConnections(i);

                    // If the length for spy is higher than current maxLength
                    if (length > maxLength) {

                        // Change maxLength
                        // and indicate spy(i) as the spy with highest length of connections
                        maxLength = length;
                        longest = i;
                    }
                }
            }

            // If the maxLength is higher than the init value
            if (maxLength > -1) {
                // Add +1 to messages and mark the cycle as visited to 
                // prevent from iterating over them again
                msg++;
                markChildren(spyList[longest]);
            }
        }


        // Return the msg count
        io.println(msg);

        // Close io
        io.close();
    }

    static int findConnections(Integer n) {
        // If in cycle we return 0
        if (cycle[n]) {
            return 0;
        }

        // Mark spy in cycle[]
        cycle[n] = true;

        // init the return value as 1
        int value = 1;

        // Iterate over curr spy children
        for (Integer s : spyList[n].children) {
            if (s == -1) {
                continue;
            }
            // Add "length" of child connection chain to value
            value += findConnections(spyList[s].num);
        }

        // Return the value
        return value;
    }

    // Runs firstly on an enemy to start of, all parents of parents and so forth therefore get a message
    // that is private
    static void markEnemies(Spy n) {
        
        // Is the current spy an enemy?
        if (!n.badGuy) {
            // If not have we visited it before?
            if(!visited[n.num]) {
                // Add +1 to messages
                msg++;
            }
        }

        // Mark as visited
        visited[n.num] = true;

        // Check children of spy ie connections
        for (Integer i : n.children) {
            // Remove the enemy as parent for children
            spyList[i].removeParent(n.num);
            
        }
        
        // Iterate over parents of current spy
        for( int i = 0 ; i < n.parents.size(); i++) {
            // If parent previously removed, continue
            if (n.parents.get(i) == -1) { 
                continue;
            }
            
            // Run markEnemies on the rest of current spies parents
            markEnemies(spyList[n.parents.get(i)]);
        }
    }

    static void markChildren(Spy n) {
        // Return if prev visited
        if (visited[n.num]) {
            return;
        }

        // Mark as visited
        visited[n.num] = true;

        // Iterate over children
        for(Integer i : n.children) {
            // Run markChildren on curr spy children
            markChildren(spyList[i]);
        }
    }

    static class Spy {
        public int num;
        public boolean badGuy;
        public List<Integer> parents;
        public List<Integer> children;

        Spy(int n) {
            num = n;
            parents = new ArrayList<Integer>();
            children   = new ArrayList<Integer>();
        }


        // Removes parent from parents list
        void removeParent(int num) {
            for (int i = 0; i < parents.size(); i++) {
                if (parents.get(i) == -1) { 
                    continue;
                }

                // If parent is == the parent we want to remove
                // remove it
                if (spyList[parents.get(i)].num == num) {
                    // Change num of parent in list to -1 to indicate that 
                    // it is removed
                    parents.set(i, -1);
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
