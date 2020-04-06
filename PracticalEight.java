import java.util.ArrayList;

public class PracticalEight{
    public static String rle(String str){
        ArrayList<String> letters = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();
        String compressed = "";
        for (int i = 0; i < str.length(); i++){
            String current = str.substring(i,i+1);
            if (letters.contains(current)){
                int j = letters.indexOf(current);
                count.set(j, count.get(j) + 1);        // incrementing count if found
            }
            else{
                letters.add(current);
                count.add(1);
            }
        }
        for (int i = 0; i < letters.size(); i++){
            compressed += letters.get(i) + Integer.toString(count.get(i));
        }
        return compressed;
    }
    public static void main(String[]args){
        String input = "aaaabbbbb";
        String input1 = "aabdeead";
        System.out.println(rle(input));
        System.out.println(rle(input1));
    }
}