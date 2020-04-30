import java.util.Random;
import java.util.*;
import java.lang.Math;
import java.lang.*;
import java.util.Arrays;

public class Prac4{
    public static int[] selSort(int[] arr){         // selection sort!
        int min_index = 0;
        int curr = -1;

        for (int i = 0; i < arr.length - 1; i++){
            min_index = i;
            curr = arr[i];
            for (int j = i + 1; j < arr.length; j++){      // iterates from min_index to end
                if (arr[min_index] > arr[j]){
                    min_index = j;                          // if value found less than min, swap
                }
                
            }
            if (arr[min_index] != curr){
                arr[i] = arr[min_index];
                arr[min_index] = curr;
            }
        }
        return arr;
    }

    public static int[] inSort(int [] arr){
        for (int i = 1; i < arr.length; i++){
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key){
                arr[j+1] = arr[j];
                j = j - 1;
                arr[j+1] = key;
            }
        }
        return arr;
    }

    public static int[] stalinSort(int [] arr){
        int [] kept = new int[arr.length];
        int loc = 1;
        kept[0] = arr[0];       // first element always kept
        for (int i = 1; i < arr.length; i++){
            if (arr[i] >= kept[loc-1]){
                kept[loc] = arr[i];
                loc++;
            }
        }
        int [] fin = new int[loc];
        for (int j = 0; j < loc; j++){
            fin[j] = kept[j];
        }
        return fin;
    }

    public static int[] genInputs(int n){
        int[] arr = new int[n]; // initializing an array
        for (int i = 0; i < n; i++){
            Random random = new Random();
            arr[i] = random.nextInt() % 100;    // numbers less than 100
        }
        return arr;
    }

    public static void printArrays(int[] arr){
        System.out.println(Arrays.toString(arr));
    }

    // PRACTICAL 5
    public static int[] mergeSort(int[] a){
        int n = a.length;
        // base case
        if (n == 1){
            return a;
        }

        int[] left = Arrays.copyOfRange(a, 0, n/2);
        int[] right = Arrays.copyOfRange(a, n/2,n);
        // create left and right sub-arrays
        
        left = mergeSort(left);
        right = mergeSort(right);
        int [] mergedArray = merge(left, right);
        return mergedArray;
    }
    public static int[] merge(int[]a, int[]b){
        Arrays.sort(a);
        Arrays.sort(b);
        int n = a.length + b.length;
        int aLoc = 0;
        int bLoc = 0;
        int loc = 0;
        int [] merged = new int[n];

        //repeat while both arrays have elements in them
        while (a.length > aLoc && b.length > bLoc){
            if (a[aLoc] <= b[bLoc]){
                merged[loc] = a[aLoc];
                aLoc++;
            }
            else{
                merged[loc] = b[bLoc];
                bLoc++;
            }
            loc++;
        }
        if (aLoc < a.length){
            for (int i = aLoc; i < a.length; i++){
                merged[loc] = a[i];
                loc++;
            }
        }
        else if (bLoc < b.length){
            for (int i = bLoc; i < b.length; i++){
                merged[loc] = b[i];
                loc++;
            }
        }
        return merged;
    }

    //second version of MergeSort with improvements
    public static int[] mergeSortEnhanced(int[] a, int cutoff){        // cutoff-> all sublists that are shorter than cutoff use insertion sort
        int n = a.length;
        // base case
        if (n == 1){
            return a;
        }
        int[] left = Arrays.copyOfRange(a, 0, n/2);
        int[] right = Arrays.copyOfRange(a, n/2,n);
        // create left and right sub-arrays
        left = mergeSort(left);
        right = mergeSort(right);

        // improvement 1 -> using inSort for small arrays
        if (left.length < cutoff){
            left = inSort(left);
        }
        if (right.length < cutoff){
            right = inSort(right);
        }

        // improvement 2
        int [] mergedArray = new int [left.length+right.length];
        if (testArray(left, right) == true){    // implementation of improvement 2
            mergedArray = merging(left, right);
        }
        else{
            mergedArray = merge(left, right);
        }
        return mergedArray;
    }

    // improvement 2 helper functions --> test whether the array is already in order. true if in order
    public static boolean testArray(int[] a, int[] b){
        if (a[a.length-1] <= b[0]){
            return true;
        }
        return false;
    }
    public static int[] merging(int[] left, int[] right){
        int n = left.length + right.length;
        int [] mergedArray = new int[n];
        int i = 0;
        int leftSize = left.length;
        while (i < n){
            if (i < leftSize){
                mergedArray[i] = left[i];
                i++;
            }
            else{
                mergedArray[i] = right[i-leftSize];
                i++;
            }
        }
        return mergedArray;
    }

    // PRACTICAL 6
    public static int[] quickSort(int[] arr, int low, int high){
        if (low < high){
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
        return arr;
    }

    public static int partition(int[] arr, int low, int high){
        int temp = 0;
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++){
            if (arr[j] < pivot){
                i++;
                temp = arr[i];  // swap
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        return (i+1);    
    }

    public static int[] enhancedQuickSort(int[] arr, int low, int high, int cutoff){
        arr = shuffle(arr); // improvement 2
        printArrays(arr);
        if (high <= low + cutoff){  // improvement 1, implement insertionSort
            return inSort(arr);
        }
        else{
            int middle = (low + high) / 2;
            if (arr[middle]-arr[low] < 0){
                int temp = arr[low];
                arr[low] = arr[middle];
                arr[middle] = temp;
            }
            if (arr[high]-arr[low] < 0){
                int temp = arr[low];
                arr[low] = arr[high];
                arr[high] = temp;
            }
            if (arr[high]- arr[middle] < 0){
                int temp = arr[middle];
                arr[middle] = arr[high];
                arr[high] = temp;
            }
            int temp = arr[middle];
            arr[middle] = arr[high-1];
            arr[high-1] = temp;
            // partition
            temp = 0;
            int pivot = arr[high];
            int i = low - 1;
            for (int j = low; j <= high - 1; j++){
                if (arr[j] < pivot){
                    i++;
                    temp = arr[i];  // swap
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            temp = arr[i+1];
            arr[i+1] = arr[high];
            arr[high] = temp;
            
            quickSort(arr,low,i-1);
            quickSort(arr, i+1, high);
        }
        return arr;

    }


    public static int[] shuffle(int[] arr){
        int size = arr.length;
        ArrayList<Integer> used = new ArrayList<Integer>();
        int [] shuffled = new int[size];
        Random r = new Random();
        for (int i = 0; i < size; i++){
            int curr = r.nextInt(size);
            while (used.contains(curr)){
                curr = r.nextInt(size);
            }
            used.add(curr);
            shuffled[i] = arr[curr];
        }
        return shuffled;
    }

    public static void main(String[] args){
        int[] arr = {2,17,-5,14,3};
        int[] ar = {1,5,3,9,6};
        int[] arSorted = selSort(ar);
        printArrays(arSorted);
  
        int[] a = {100, 20, 80, 50, 70};
        int[] an = inSort(a);
        //printArrays(an);

        int[] array = genInputs(5);
        //printArrays(array); 
        //printArrays(stalinSort(array));   

        //System.out.println("trying merge");
        //printArrays(merge(ar, arr));


        //System.out.println("trying mergeSort");
        //printArrays(mergeSort(ar));

        //printArrays(merging(arSorted, an));

        //printArrays(mergeSortEnhanced(ar, 4));
        
        printArrays(quickSort(ar, 1,4));
        printArrays(enhancedQuickSort(ar, 0,2,3));


        final long startTime = System.currentTimeMillis();

        final long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("the time taken " + elapsedTime);


    }
}

