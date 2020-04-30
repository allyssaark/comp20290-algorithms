import java.util.ArrayList;

public class Practical9{

    public static String rle(String str){
        ArrayList<String> letters = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();
        String compressed = "";
        boolean endOfString = false;
        int i = 0;
        while(!endOfString){
            String current = str.substring(i,i+1);
            letters.add(current);
            boolean found = true;
            int num = 0;
            while (found && i < str.length()){
                if (str.substring(i, i+1).equals(current)){
                    i++;
                    num++;
                }
                else{
                    found = false;
                    break;
                }
                if (i == str.length()){
                    endOfString = true;
                }
            }
            count.add(num);
        }

        for (int j = 0; j < letters.size(); j++){
            compressed += letters.get(j) + Integer.toString(count.get(j));
        }
        return compressed;
    }
    public static void main(String[]args){
        String input = "aaaabbbbb";
        String in = "allyssaark";
        System.out.println(rle(input));
        System.out.println(rle(in));
    }
}