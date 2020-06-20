import java.util.*;
import java.io.*;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

public class RoomPainting {
    
    static int[] shopList;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        
        int shop = io.getInt();
        int need = io.getInt();
        
        shopList = new int[shop];


        for(int i = 0; i < shop; i++)  {  
            int num = io.getInt();
            shopList[i] = num;            
        }

        // Sort the list using merge
        // Decided to use merge since the list can have up to 100 000 elements
        sort(shopList, 0, shopList.length - 1);
        
        // Init the waste micro litres as 0
        long microL = 0;

        // Iterate through all ef the needed can sizes
        for (int i = 0; i < need; i++) { 
            int currNeed = io.getInt();
            
            // Add the waste to microL
            // findNeed() returns smallest can that he can buy for said paint
            microL += findNeed(currNeed) - currNeed;
        }
        io.println(microL);
        io.close();
    }

    // Using binary search to effectively find the can of paint to buy
    private static int findNeed(int currN)  {  
        int lo = 0, hi = shopList.length-1;  
    
          
        while (lo < hi) {  
            int mid = (lo + hi) / 2;  
     
            if (shopList[mid] < currN) {  
                lo = mid + 1;  
            } else {   
                hi = mid;  
            }  
        }  
        return shopList[lo];  
    }  
        
    

    // Code for merge sort
    static void merge(int arr[], int l, int m, int r) 
    { 
        // Sizes of the two subarrays
        int size1 = m - l + 1; 
        int size2 = r - m; 
  
        // Temporary arrays
        int left[] = new int [size1]; 
        int right[] = new int [size2]; 
  
        // Copy over to temps
        for (int i=0; i<size1; ++i) 
            left[i] = arr[l + i]; 
        for (int j=0; j<size2; ++j) 
            right[j] = arr[m + 1+ j]; 
  
  
        // Initial indexes of first and second subarrays 
        int i = 0, j = 0; 
  
        // Initial index of merged subarry array 
        int k = l; 
        while (i < size1 && j < size2) 
        { 
            if (left[i] <= right[j]) 
            { 
                arr[k] = left[i]; 
                i++; 
            } 
            else
            { 
                arr[k] = right[j]; 
                j++; 
            } 
            k++; 
        } 
  
        // Copy rest of arrays
        while (i < size1) 
        { 
            arr[k] = left[i]; 
            i++; 
            k++; 
        } 
        
        // For the other array
        while (j < size2) 
        { 
            arr[k] = right[j]; 
            j++; 
            k++; 
        } 
    } 
  
    // Sort function that uses merge
    static void sort(int arr[], int l, int r) 
    { 
        if (l < r) 
        { 
            // Find middle 
            int m = (l+r)/2; 
  
            // Sort both halves
            sort(arr, l, m); 
            sort(arr , m+1, r); 
  
            // Merge both halves after they have been sorted 
            merge(arr, l, m, r); 
        } 
    } 

}

// Kattio
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


