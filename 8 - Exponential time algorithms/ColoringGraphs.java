import java.util.*;
import java.io.*;

public class ColoringGraphs {

    // Holds information about the color of different nodes
    static int[] nodeColors;

    static int numNodes;

    // Holds the edges in the graph in the form of an adjacency list
    static ArrayList<ArrayList<Integer>> edgeList;

    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        numNodes = Integer.parseInt(br.readLine());
        
        edgeList = new ArrayList<ArrayList<Integer>>();

        nodeColors = new int[numNodes];

        // Now we read each line and add the edges
        for(int i = 0; i < numNodes; i++) {
            ArrayList<Integer> currentNode = new ArrayList<Integer>();
            String line = br.readLine();
            String[] nums = line.split(" ");

            for(int j = 0; j < nums.length; j++) {
                int a = Integer.parseInt(nums[j]);
                currentNode.add(a);
                //System.out.println(Integer.parseInt(nums[j]));
            }

            // Add all the edges to edgeList
            edgeList.add(currentNode);
        }
        
        // Use to check if list is constructed correctly
        //for(int i = 0; i <= edgeList.size() - 1; i++) {
            //System.out.println("Node :" + i);
            //for(int e : edgeList.get(0)) {
                //System.out.print("Connects to: ");
                //System.out.println(e);
            //}
        //}

        // The graph can contain 2 - 11 nodes, so the least
        // amount of colors we will need is always 2
        // Therefore we can "preset" two nodes with an edge with 
        // two different colors

        for(int amount = 2; amount <= numNodes; amount++) {
            // We do this from node 0 and to on of its connected nodes
            nodeColors[0] = 1;

            // Mark one connected node with another color
            nodeColors[edgeList.get(0).get(0)] = 2;

            // Now we try to solve the graph with an increasing amount of max
            // colors until the graph is validly colored

            if(canSolve(1, amount)) {
                System.out.println(amount);
                // Break when we found the solution with the least amoujnt of colors
                break;
            }
        }
    }
    
    public static boolean canSolve(int i, int amount) {
        
        if(i == nodeColors.length) {
            return true;
        }
        if(nodeColors[i] != 0) {
            return canSolve(i + 1, amount);
        }

        // 

        out: for(int c = 1; c <= amount; c++) {
            for(int edge : edgeList.get(i)) {
                if(nodeColors[edge] == c) {
                    continue out;
                }
            }

            nodeColors[i] = c;
            if(canSolve(i + 1, amount)) {
                return true;
            }

            nodeColors[i] = 0;
        }
        return false;
    }
}
