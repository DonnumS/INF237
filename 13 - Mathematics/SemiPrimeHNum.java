import java.util.*;
import java.io.*;


public class SemiPrimeHNum {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        boolean[] isPrime = new boolean[1000002];
        Arrays.fill(isPrime, true);

        List<Integer> HSemiPrimes = new ArrayList<>();
        // 1 is not a prime but a unit; -> mark as false
        isPrime[1] = false;

        // Disregard 1, and start from 5
        // Mark all multiples as false
        // Loop from 5 until 1000 since 1 000 * 1 000 = 1 000 000
        for (int i = 5; i <= 1001; i += 4) {
            if (isPrime[i]) {
                for (int j = i * i; j <= 1000001; j += 4*i)
                    isPrime[j] = false;
            }
        }
        
        // Loop through all i's up to 1001
        for (int i = 1; i <= 1001; i += 4) {
            // If i is a hPrime
            if (isPrime[i])
                // Loop through all H numbers j that multiplied with i is smaller
                // than the max
                for(int j = i;i * j <= 1000001; j+=4) {

                    // If j is also a hPrime
                    if(isPrime[j]) {
                        
                        // Add the semiprime
                        HSemiPrimes.add(i*j);
                    }
                }
        }

        

        Collections.sort(HSemiPrimes);
        // Now we remove duplicates
        List<Integer> noDupes = new ArrayList<Integer>(new LinkedHashSet<>(HSemiPrimes));
        
        
        while(true) {
            int n = io.getInt();
            if(n == 0) {
                break;
            }
            
            // Find total with binary search
            int total = search(n, noDupes);
            
            io.println(n + " " + total);
            io.flush();
        }
        io.close();
    }

    
    // Use binary search to find number of hSemiPrimes
    public static int search(int val, List<Integer> arr) {
        int left = 0; 
        int right = arr.size() - 1; 
  
        int count = 0; 
        
        // Serach until left is larger than right
        while (left <= right)  {
            
            // Get mid
            int mid = (right + left) / 2; 
  
            // Check if middle element is 
            // less than or equal to value 
            if (arr.get(mid) <= val){
                // mid + 1 elements or more are less than target
                count = mid + 1; 
                left = mid + 1; 
            } 
            // If val is smaller, ignore right half 
            else
                right = mid - 1; 
        } 
        return count; 
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

