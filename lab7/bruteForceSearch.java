// Java program for Naive Pattern Searching 

public class bruteForceSearch {
	  
	public static int search(String txt, String pat) { 
        //insert your code here
        int n = txt.length();
        int m = pat.length();
        for (int pos = 0; pos < n-m; pos++){
            if (txt.substring(pos, pos+m).equals(pat)){
                return pos;
            }
        }
	    return -1;	
	} 
	  
	public static void main(String[] args) { 
	        //alter to take text file in..
	        String txt = "ABABDABACDABABCABAB"; 
		    String pat = "ABABCABAB"; 
            System.out.println(search(txt, pat)); 
            
            String t = "hello my name is allyssa";
            String p = "all";
            System.out.println(search(t,p));
	} 
} 