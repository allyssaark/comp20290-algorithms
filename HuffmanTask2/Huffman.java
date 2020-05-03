/******************************************************************************
 *  Compilation:  javac Huffman.java
 *  Execution:    java Huffman <method> inputFileName outputFileName
 *  Dependencies: BinaryIn.java; BinaryOut.java
 * 
 * 
 *  Compress or expand a binary input stream using the Huffman algorithm.
 *
 * Add instructions and documentation related to your Huffman algorithm here...
 * javac Huffman.java
 * java Huffman compress text.txt homeCO.txt
 * the time taken: 6
 * java Huffman decompress homeCO.txt homeDO.txt
 * the time taken: 3
 * input file length: 35
 * output file length: 27
 *
 ******************************************************************************/


/**
 *  Add in your information about each method etc. here
 * compress() 
 * 1. first reads the provided text file and converts it to an array of characters
 * 2. creates a list that iterates through the provided text and increments an element if found
 * 3. calls buildTrie(), which iterates through the ASCII length. Compares every character to the 
 * list storing the frequency of each character, if character is marked as used (not 0) it inserts 
 * a new node into the priority queue. After, it iterates through the priority queue and continues to
 * delete the smallest nodes (after allocating them as 'left' or 'right'). Creates a new parent node,
 * where it points to 'left' and 'right' as its children. Finally, it inserts this new node into the 
 * priority queue, returning the final tree when there is only one left.
 * 4. calls buildCode(), which recursively traverses through the nodes until it finds a node marked as 
 * a leaf and adds it to the codeTable.
 * 5. calls writeTrie(), which writes the tree to the stream. This is a necessary step because without
 * the tree, it is very difficult to accurately decipher the given binary numbers.
 * 6. calls buildCode(), which traverses through the input and looks up every character in the codeTable.
 * Takes the code pertaining to the current character and iterates through it, writing every character 
 * to the stream.
 * 
 * decompress()
 * 1. reads through the tree provided in the input file by iterating through until hitting every leaf
 * 2. gets the length of the text file, which is also written in the input file (through previously running
 * original message through compress)
 * 3. Using the Huffman tree, it iterates through all the bytes until bytes equals the text length. Every loop,
 * it restarts the node to the root and will loop through the bits (moving to the left or right child node) based on
 * the bit information. After hitting the leaf node, it writes the character at the leaf node into the output file.
 * 
 *
 *  @author Allyssa Ark
 */

import java.io.*;

public class Huffman {

    // alphabet size of extended ASCII
    private static final int R = 256;

    // Do not instantiate.
    private Huffman() { }

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses them
     * using Huffman codes with an 8-bit alphabet; and writes the results
     * to standard output.
     */
    //public static void compress() {

    public static void compress(String inputFile, String outputFile) {
        BinaryOut output = new BinaryOut(outputFile);           // initializing
        BinaryIn in = new BinaryIn(inputFile);
        // read the input
        String text = in.readString();

        //String text = BinaryStdIn.readString();
        char[] input = text.toCharArray();

        // tabulate frequency counts
        int [] frequency = new int[R];      // creating new list, size of extended ASCII
        for (int i = 0; i < input.length; i++){     // iterate through text file
            int loc = (int) input[i];           // converting character to ascii value
            frequency[loc] = frequency[loc]+1;      // incrementing location by 1
        }

        // build Huffman trie
        Node root = buildTrie(frequency);

        // build code table
        String [] codeTable = new String[R];
        buildCode(codeTable, root,"");

        // print trie for decoder
        writeTrie(root, output);

        // print number of bytes in original uncompressed message
        output.write(text.length());

        // use Huffman code to encode input
        int j = 0;
        while (j < input.length){
            char currC = input[j];              // get the current character
            String bits = codeTable[currC];     // look up character in code table to get code
            int k = 0;
            while (k < bits.length()){          // iterates through the bits representing its location in the tree, writes into output file
                if (bits.charAt(k) == '1'){
                    output.write(true);
                }
                else{
                    output.write(false);
                }
                k++;
            }
            j++;
        }
        output.flush();


    }


    /**
     * Reads a sequence of bits that represents a Huffman-compressed message from
     * standard input; expands them; and writes the results to standard output.
     */
    public static void decompress(String inputFile, String outputFile) {
        BinaryOut output = new BinaryOut(outputFile);           // initializing
        BinaryIn in = new BinaryIn(inputFile);

        // read in Huffman trie from input stream
        Node root = readTrie(in);

        // number of bytes to write
        int textLength = in.readInt();

        // decode using the Huffman trie
        int currentByte = 0;
        while (currentByte < textLength){        // loop through all bytes
            Node curr = root;
            while (!curr.isLeaf()){         // loops through binary numbers until leaf is found
                boolean bit = in.readBoolean();
                if (bit == false){          // if false, fork left
                    curr = curr.left;       
                }
                else{
                    curr = curr.right;
                }
            }
            output.write(curr.ch, 8);     // writing the deciphered character into the stream
            currentByte++;                      // move to the next byte
        }
        output.flush();
    }

    // build the Huffman trie given frequencies
    private static Node buildTrie(int[] freq) {

        // initialze priority queue with singleton trees
        MinPQ<Node> pq = new MinPQ<Node>();
        for (char i = 0; i < R; i++)
            if (freq[i] > 0)
                pq.insert(new Node(i, freq[i], null, null));

        // special case in case there is only one character with a nonzero frequency
        if (pq.size() == 1) {
            if (freq['\0'] == 0) pq.insert(new Node('\0', 0, null, null));
            else                 pq.insert(new Node('\1', 0, null, null));
        }

        // merge two smallest trees
        while (pq.size() > 1) {             // deletes the smaller two trees after assigning them as left or right node, then creates a new node that has both left and right as childs
            Node left  = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }


    // write bitstring-encoded trie to standard output
    private static void writeTrie(Node x, BinaryOut output) {
        if (x.isLeaf()) {
            output.write(true);
            output.write(x.ch, 8);
            return;
        }
        output.write(false);
        writeTrie(x.left, output);
        writeTrie(x.right, output);
    }

    // make a lookup table from symbols and their encodings
    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left,  s + '0');
            buildCode(st, x.right, s + '1');
        }
        else {
            st[x.ch] = s;
        }
    }



    private static Node readTrie(BinaryIn in) {
        boolean isLeaf = in.readBoolean();
        if (isLeaf) {
            return new Node(in.readChar(), -1, null, null);
        }
        else {
            return new Node('\0', -1, readTrie(in), readTrie(in));
        }
    }

    /**
     * Sample client that calls {@code compress()} if the command-line
     * argument is "compress" an {@code decompress()} if it is "decompress".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1){
            System.out.println("Please include all necessary criteria to run the program. (1) method (2) input filename (3) output filename");
            System.exit(0);
        }
        else if (args.length == 1){     // to check the length of a file
            String input = args[0];
            File f1 = new File(input);
            System.out.println(f1.length());
        }
        else{
            String input = args[1];
            String output = args[2];
            File f1 = new File(input);
            File f2 = new File(output);
            if (f1.exists() && f2.exists()){            // tries to compress or decompress if both files exist
                if (args[0].equals("compress")){
                    final long startTime = System.currentTimeMillis();
                    compress(input,output);
                    final long elapsedTime = System.currentTimeMillis() - startTime;
                    System.out.println("the time taken: " + elapsedTime);           // returns the time taken to compress

                }
                else if (args[0].equals("decompress")){
                    final long startTime = System.currentTimeMillis();
                    decompress(input, output);
                    final long elapsedTime = System.currentTimeMillis() - startTime;
                    System.out.println("the time taken: " + elapsedTime);
                    System.out.println("input file length: "+f1.length());
                    System.out.println("output file length: "+f2.length());
                }
                else{
                    //System.out.println(args[0]);
                    System.out.println("Sorry, you did not provide a valid method. Please choose 'compress' or 'decompress' and enter it as args[0].");
                }
            }
            else{
                System.out.println("please use valid files.");
                System.exit(0);
            }     


        }

    }

}
