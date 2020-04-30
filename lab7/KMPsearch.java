    // JAVA program for implementation of KMP pattern 
	// searching algorithm 
	  
	class KMPsearch { 
	    void KMPSearch(String pat, String txt) { 
	        int m = pat.length(); 
	        int n = txt.length(); 
	  
	        // create lps[] that will hold the longest 
	        // prefix suffix values for pattern 
	        int lps[] = new int[m]; 
	        int j = 0; // index for pat[] 
	  
	        // Preprocess the pattern (calculate lps[] 
	        // array) 
	        computeLPSArray(pat, m, lps); 
            int i = 0;
            while (n > i){
                if (j == m){
                    System.out.println("pattern found at index" + (i-j));
                }
                if (pat.charAt(j) == txt.charAt(i)){ // increment both variables if the characters at this index are equal to each other
                    j++;
                    i++;
                }
                else if (n > i && pat.charAt(j) != txt.charAt(i)){     // if the values at the index are not equal
                    if (j != 0){
                        j = lps[j-1];   // decrement the index for the pattern
                    }
                    else{
                        i++;     //increment location in the text
                    }
                }
            }	        
	    } 
	  
	    void computeLPSArray(String pat, int m, int lps[]) 
	    { 
	        // length of the previous longest prefix suffix 
	        int len = 0; 
	        int i = 1; 
	        lps[0] = 0; // lps[0] is always 0 
	  
	        // the loop calculates lps[i] for i = 1 to M-1 
	        while (i < m) { 
	            if (pat.charAt(i) == pat.charAt(len)) { 
	                len++; 
	                lps[i] = len; 
	                i++; 
	            } 
	            else // (pat[i] != pat[len]) 
	            { 
	                // This is tricky. Consider the example. 
	                // AAACAAAA and i = 7. The idea is similar 
	                // to search step. 
	                if (len != 0) { 
	                    len = lps[len - 1]; 
	  
	                    // Also, note that we do not increment 
	                    // i here 
	                } 
	                else // if (len == 0) 
	                { 
	                    lps[i] = len; 
	                    i++; 
	                } 
	            } 
	        } 
	    } 
	  
	    // Driver program to test above function 
	    public static void main(String args[]) 
	    { 
	        String txt = "ABABDABACDABABCABAB"; 
	        String pat = "ABABCABAB"; 
	        new KMPsearch().KMPSearch(pat, txt); 
	    } 
	} 