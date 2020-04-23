import Trie.TrieNode;

public class Trie { 
      
    // Alphabet size (# of symbols) we pick 26 for English alphabet
    static final int ALPHABET_SIZE = 26; 
      
    // class for Trie node 
    static class TrieNode { 
        TrieNode[] children = new TrieNode[ALPHABET_SIZE]; 
       
        // isEndOfWord is true if the node represents end of a word i.e. leaf node
        boolean isEndOfWord; 
          
        TrieNode(){ 
            isEndOfWord = false; 
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                children[i] = null; 
            }
        } 
    }
       
    static TrieNode root;  
    // If no key present, inserts into trie 
    // If the key is prefix of Trie node,  
    //  marks leaf node 
    static void insert(String key) {        
        TrieNode curr = root; 
        int i = 0;
        while (i < key.length()){                   // iterate through the whole key
            int loc = key.charAt(i) - 'a';        // letter 'a' - 'a' = 0, so 'b' = 1, etc. --> gets the correct index to check if it exists
            if (curr.children[loc] == null){        // check if the node does not exist --> if so, create new node
                curr.children[loc] = new TrieNode();   
            }
            curr = curr.children[loc];              //points curr to current letter in Trie
            i++;        // increment to next letter
        } 
        curr.isEndOfWord = true;    // mark last letter in the key as a leaf
    } 
       
    // Returns true if the key is present in the trie, else false 
    static boolean search(String key) 
    { 
        TrieNode curr = root; 
        int i = 0;
        while (i < key.length()){
            int loc = key.charAt(i) - 'a';      // getting the location of the current character in the array
            if (curr.children[loc] == null){        // if letter not found, end search & return false
                return false;
            }
            curr = curr.children[loc];              // setting node to last found letter node
            i++;              // increment to the next letter
        } 
        return true;
    } 
       
    // Driver 
    public static void main(String args[]) { 
        // Input keys (use only 'a' through 'z' and lower case) 
        String keys[] = {"bank", "book", "bar", "bring", "film","filter", "simple", "silt", "silver"};
        String output[] = {"Not present in trie", "Present in trie"}; 
             
        root = new TrieNode(); 
       
        // Construct trie 
        int i; 
        for (i = 0; i < keys.length ; i++) 
            insert(keys[i]); 
       
        // Search for different keys 
        System.out.println(search("he"));
        System.out.println(search("filter"));
        System.out.println(search("thaw"));
        System.out.println(search("book"));   
    } 
    //end of class
} 