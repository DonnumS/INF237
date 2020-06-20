import java.util.*;
import java.io.*;

/*
Instead of creating palindromes 1-by-1 we construct the nth, much faster.

The binary palindromes can be assiggned to groups. 
Group 0 = 1 length
Group 1 = 2 length and 3 length
Group 2 = 4 length and 5 length
etc 
Lenght of palindrome is groups * 2 and group * 2 + 1


Can find group by counting up elements in each group until group of number is reached
Each group has 3 * 2^(group number - 1):
Ex: group 1:
3 * 2^0 = 3, so three palindromes located in group 1;
        these are: 11, 101, 111, 

Ex group 2:
3* 2 ^1 = 6
1001, 1111, 10001, 10101, 11011, 11111 
and so on.

Find value of binarystring located between left 1 and middle
1(0)01
1(1)11
1(0)001 
1(0)101
1(1)011
1(1)111

Create left side and reverse, then add together
Then turn this binary string into its decimal value
*/

public class Base2Palindrome {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        
        int n = io.getInt();

        // Find the nth binary string
        String ans = solve(n);
        //System.out.println(ans);
        
        // Turn nth binary string into long
        long value = Long.parseLong(ans, 2);
        
        // Print value of binary string
        io.println(value);
        io.flush();
        
        
    }

    public static String solve(int n) {

        // Base case n = 1:
        if(n == 1) {
            return "1";
        }
        
        // A few variables we need to construct nth palindrome

        int group = 0; // What group does the palindrome belong to?
        // This is later used to find length of palindrome, since
        // lenght is (2*group) or (2*group) + 1

        int nthGroup; // Used to find out what position it has in its group
        // ie. group 1 has 3 elemnts, so is it 0th, 1st or 2nd?

        int midBinary; // The binary string can be split in half
        // If we look at left side of the binary, the binary in between the
        // outermost 1 and the middle 0,1,or norhtin, has this value

        int midVal; // What is the value of bit in middle of binary string.
        // 0, 1 or -1 if there is no middle

        int inGroup = 0; // Number of elemements in current binary palindrome group
        
        int counter = 1; // Counts number of elements

        // Find what group our number belongs to
        while(counter < n) {

            // Increment group counter
            group++;

            // store number of elemnts in current group
            inGroup = counter;

            // Then add the number of elements in this group to the
            // counter for total elements;
            counter += 3 * (int)(Math.pow(2, group - 1));


        }

        // Now we can find the position in correct group, starting from 0th:
        nthGroup = n - inGroup - 1;

        // Next we find what the middle bit is supposed to be, either 0, 1 or nothing
        if((nthGroup + 1) <= (int)(Math.pow(2, group - 1))) {
            // The palindrome does not have a middle bit, ie even length
            // so we set midVal = -1;
            midVal = -1;

            // The midBinary value is now:
            midBinary = nthGroup;
        }
        else {
            if((((nthGroup) - (int)Math.pow(2, group - 1))) % 2==0) {
                // Middle of binary is set
                midVal = 0;
            } else {
                // Or else it is clear
                midVal = 1;
            }

            // Get value of midBinary
            midBinary = ((nthGroup) - (int)(Math.pow(2, group - 1))) / 2;
            
            
        }

        // Now we make the appropriate binary palindrome
        return getBinaryString(group, midBinary, midVal);
    }

    public static String getBinaryString(int group, int midBinary, int midVal) {
        String binary;
        String leftBin;
        String rightBin;
        String left;
        
        // Now we construct the string
        if(midVal == -1) {

            // Get length
            int length = (2 * group);
            
            // Get length of left side
            int leftLength = (length / 2);
            left = "";
            
            // Get left side binary if length is over 3,
            if(length > 3) {
                left = Integer.toBinaryString(midBinary); 
            } 
            
            // Add zeros to get rigth length
            while (left.length() < leftLength - 1) {
                left = "0" + left;
            }
            
            // Add 1 to leftmost side
            leftBin = "1" + left;

            // Get left reversed
            rightBin = new StringBuilder(leftBin).reverse().toString();

            // Add together
            binary = leftBin + rightBin;

        } else {
            // get length
            int length = (2 * group) + 1;
            
            // Get legnth of left side
            int leftLength = ((length - 1) / 2);

            // Get the mid bit
            String mid = Integer.toString(midVal);
            left = "";
            // If legnth over 3, get midBinary string
            if(length > 3) {
                left = Integer.toBinaryString(midBinary);
            }
            
            // Add zeros if necessary
            while(left.length() < leftLength - 1) {
                left = "0" + left;
            }

            // Add 1 to leftmost side
            leftBin = "1" + left;

            // Get right with left reversed
            rightBin = new StringBuilder(leftBin).reverse().toString();

            // Add all three parts together
            binary = leftBin + mid + rightBin;
        }

        // Return the binary
        return binary;
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