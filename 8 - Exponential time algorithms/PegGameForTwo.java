import java.util.*;
import java.io.*;

public class PegGameForTwo {

    public static boolean DEBUG = false;
    
    // The board itself
    public static int[][] board;

    // Used to move on the board
    public static int[] xDir = {0, 0, 1, 1, -1, -1};
    public static int[] yDir = {1, -1, 0, 1, -1, 0};

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        // Construct board
        board = new int[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j <= i; j++) {
                board[i][j] = io.getInt();
            }
        }

        if(DEBUG) {
            // Sjekk om board bygges riktig
            System.out.println(board[0][0]);
            System.out.println(board[4][4]);   
        }

        
        // Finn optimalt spill
        System.out.println(solve());
        
    }

    public static int solve() {
        // Init ans som min value
        int ans = Integer.MIN_VALUE; 
        
        // Iterer over hele brettet ved brute force
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j <= i; j++) {
                if(board[i][j] == 0) {
                // Sjekk alle retninger fra node
                    for(int dir = 0; dir <= 5; dir++) {
                    
                        // Finn de to neste "cellene" for trekket
                        int iOne = i + xDir[dir];
                        int jOne = j + yDir[dir];

                        int iTwo = iOne + xDir[dir];
                        int jTwo = jOne + yDir[dir];

                        // Er trekket valid?
                        if(canMove(iOne, jOne) && canMove(iTwo, jTwo) && board[iOne][jOne] != 0 && board[iTwo][jTwo] != 0) {
                            // jumpedOver er cellen eller peggen som skal fjernes
                            int jumpedOver = board[iOne][jOne];
                            board[iOne][jOne] = 0;
                            board[i][j] = board[iTwo][jTwo];
                            board[iTwo][jTwo] = 0;

                            // Finn optimalt trekk ved hjelp av rekusivitet
                            ans = Math.max(ans, jumpedOver * board[i][j] - solve());

                            // Reset board
                            board[iOne][jOne] = jumpedOver;
                            board[iTwo][jTwo] = board[i][j];
                            board[i][j] = 0;
                        }
                    }
                }
            }

           
        }

        // Returner ans
        if(ans == Integer.MIN_VALUE) {
            return 0;
        }
        return ans;
    }

    // Sjekker om et trekk holder seg innenfor spillebrettet
    public static boolean canMove(int i, int j) {
        return (i > -1 && i < 5 && j > -1 && j <= i);
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