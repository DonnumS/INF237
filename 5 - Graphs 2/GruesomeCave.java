import java.util.*;
import java.io.*;

public class GruesomeCave {
    
    public static int L;
    public static int W;
    public static char[][] maze;
    public static int[][] risk;
    public static int[] rDir = {1,-1, 0, 0};
    public static int[] cDir = {0, 0, 1, -1};
    public static int[][] numNeighbours;
    public static int totalRisk;
    public static int startX;
    public static int startY;
    public static int endX;
    public static int endY;

    public static void main(String[] args) throws IOException{

        // Read the input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String limits = br.readLine();
        String[] nums = limits.split(" ");

        // Length and width of maze
        L = Integer.parseInt(nums[0]);
        W = Integer.parseInt(nums[1]);

        // char map of maze and risk map of maze
        maze = new char[L][W];
        risk = new int[L][W];

        // used to store neighbours that are ' '
        numNeighbours = new int[L][W];

        // Init the start and end coords to 0
        startX = 0;
        startY = 0;
        endX = 0; 
        endY = 0;

        // The total number of moves the grue can make if we assume 
        // it can be at any tile
        // Init at 0
        totalRisk = 0;

        // Construct the maze
        for(int x = 0; x < L; x++) {
            // Get the line/row
            String row = br.readLine();
            for(int y = 0; y < W; y++) {
                // Set (x, y) in maze as char at y in row x
                maze[x][y] = row.charAt(y);

                // Init the risk for tile as max value
                risk[x][y] = Integer.MAX_VALUE;

                // Now we check if current tile is 'E' or 'D'
                // First we check for entrance
                if(maze[x][y] == 'E') {
                    startX = x;
                    startY = y;
                }

                // Then the end
                if(maze[x][y] == 'D') {
                    endX = x;
                    endY = y;
                }
            }
        }


        // Now we need to find the total of scenarios
        // for the grue as well as neighbours
        // for each tile
        findNeighbours();
        
        // Risk at 'E' is init as 0
        risk[startX][startY] = 0;

        bfs();
        
        

        // Now we calculate the probability for the best route
        // At risk[endX][endY] we have stored the smallest possible risk
        // for a route getting there
        // And the totalRisk is the total nuber of possibilities 
        // for where the grue can be
        // Therefore the probability of meeting grue is
        // risk of route / total amount
        double bestScenario = (double) risk[endX][endY] / totalRisk;

        // Print result;
        System.out.println(bestScenario);

    }

    public static void findNeighbours() {
        // Loop through all cells
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < W; j++) {

                // If cell == ' ', we and the grue can move here
                if (maze[i][j] == ' ') {
                    // We then check the number of moves that can
                    // be done to end up at this tile, ie the number of neighbours
                    // Init possible moves to end up as 0
                    int tileMoves = 0;

                    // Check all 4 directions if tile == ' '
                    for (int k = 0; k < 4; k++) {
                        // Get new x and y coords
                        int nX = i + rDir[k];
                        int nY = j + cDir[k];

                        // Are these cords valid?
                        if (nX >= 0 && nX < L && nY >= 0 && nY < W && maze[nX][nY] == ' ') {
                            // Add 1 to number of possible ways to get to this tile
                            tileMoves++;
                        }
                    }

                    // Neighbours == number of tiles around
                    numNeighbours[i][j] = tileMoves;

                    // Add number of moves to get here to the total moves that 
                    // grue can make
                    totalRisk += tileMoves;
                }
            }
        }
    }

    static void bfs() {
        // Create PriorityQueue
        PriorityQueue<Node> q = new PriorityQueue<Node>();
        // Add start as a new Node in q
        q.add(new Node(startX, startY, 0));
        while(!q.isEmpty()) {
            // Get node
            Node n = q.poll();

            // Check if risk at node is larger than riskTable
            if(n.risk > risk[n.x][n.y]) {
                continue;
            }

            // Stop when ew reach end
            if(n.x == endX && n.y == endY) {
                break;
            }

            // Check all 4 directions
            for(int k = 0; k < 4; k++) {
                // Get new x and y coords
                int nX = n.x + rDir[k];
                int nY = n.y + cDir[k];
                // New x and y valid?
                if(nX >= 0 && nX < L && nY >= 0 && nY < W) {
                    // maze[new x][new y] valid to move to, and
                    // risk of moving is less than risk previously recorded for tile?
                    if (maze[nX][nY] != '#' && n.risk + numNeighbours[nX][nY] < risk[nX][nY]) {
                        // Change risk of tile to the new and smaller number
                        risk[nX][nY] = n.risk + numNeighbours[nX][nY];
                        // Add new tile as node in q
                        q.add(new Node(nX, nY, risk[nX][nY]));
                    }
                }
            }
        }
    }
    
    public static class Node implements Comparable<Node> {
        // Holds information about x and y coords
        // for tile and the risk of meeting grue
        int x, y, risk;


        // Node constructor
        public Node(int x, int y, int risk) {
            this.x = x;
            this.y = y;
            this.risk = risk;
        }

        // compareTo method for the Priority Queue, uses risk to compare
        public int compareTo(Node n)
        {
            return Integer.compare(risk,n.risk);
        }
    }

}

 
    