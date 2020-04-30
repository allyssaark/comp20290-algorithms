/******************************************************************************
 *  Compilation:  javac ThreeSumA.java
 *  Execution:    java ThreeSum input.txt
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 ******************************************************************************/
import java.util.Arrays;
import java.lang.*;
import java.io.*;

public class ThreeSumA {

    // Do not instantiate.
    private ThreeSumA() { }



    /**
     * Returns the number of triples (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}.
     *
     * @param  a the array of integers
     * @return the number of triples (i, j, k) with {@code i < j < k}
     *         such that {@code a[i] + a[j] + a[k] == 0}
     */
    public static int count(int[] a) {
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    } 

    /**
     * Reads in a sequence of integers from a file, specified as a command-line argument;
     * counts the number of triples sum to exactly zero; prints out the time to perform
     * the computation.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args)  { 
        //In in = new In(args[0]);
        //int[] a = in.readAllInts();
        //int count = count(a);

        //String inFile = "8ints.txt";
        //String inFile = "1Kints.txt";
        //String inFile = "2Kints.txt";
        //String inFile = "4Kints.txt";
        //String inFile = "8Kints.txt";
        //String inFile = "16Kints.txt";
        String inFile = "32Kints.txt";

        try{
            FileReader reader=new FileReader(inFile);
            BufferedReader br = new BufferedReader(reader);
            String Line= br.readLine();
            int arrs[]=new int[32000];       // change size depending on how many lines are in eact text file
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


