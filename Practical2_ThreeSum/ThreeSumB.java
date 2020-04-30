/******************************************************************************
 *  Compilation:  javac ThreeSumB.java
 *  Execution:    java ThreeSumB input.txt
 *
 *  Reads n integers
 *  and counts the number of triples that sum to exactly 0.
 *
 *  Limitations
 *  -----------
 *     - we ignore integer overflow
 *     - doesn't handle case when input has duplicates
 *     
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 ******************************************************************************/

import java.util.Arrays;
import java.lang.*;
import java.io.*;

public class ThreeSumB {

    // Do not instantiate.
    private ThreeSumB() { }

    // returns true if the sorted array a[] contains any duplicated integers
    private static boolean containsDuplicates(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i] == a[i-1]) return true;
        return false;
    }

  
    /**
     * Returns the number of triples (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}.
     *
     * @param a the array of integers
     * @return the number of triples (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}
     */
    public static int count(int[] a) {
        int n = a.length;
        Arrays.sort(a);
        if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int k = Arrays.binarySearch(a, -(a[i] + a[j]));
                if (k > j) count++;
            }
        }
        return count;
    } 

    /**
     * Reads in a sequence of distinct integers from a file, specified as a command-line argument;
     * counts the number of triples sum to exactly zero; prints out the time to perform
     * the computation.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args)  { 
        //In in = new In(args[0]);
        //int[] a = in.readAllInts();
        //int count = count(a);
        //System.out.println("count =" + count);

        String inFile = "8ints.txt";
        //String inFile = "1Kints.txt";
        //String inFile = "2Kints.txt";
        //String inFile = "4Kints.txt";
        //String inFile = "8Kints.txt";
        //String inFile = "16Kints.txt";
        //String inFile = "32Kints.txt";
        try{
            FileReader reader=new FileReader(inFile);
            BufferedReader br = new BufferedReader(reader);
            String Line= br.readLine();
            int arrs[]=new int[9];       // change size depending on how many lines are in eact text file
            int index=0;
            while(Line!=null){
                
                arrs[index++]=Integer.parseInt(Line.trim());
                Line = br.readLine();
            }
            //System.out.println(Arrays.toString(arrs));
            final long startTime = System.currentTimeMillis();
            count(arrs);

            final long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("the time taken " + elapsedTime);
            }
        catch(Exception e){
            e.printStackTrace();
        }
    } 
} 

