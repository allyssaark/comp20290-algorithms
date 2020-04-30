
//import java.lang.System.currentTimeMillis();
public class Prac1{
    public static int russianMultiply(int n, int m){
        int accum = 0;
        while (n > 0){
            if (n % 2 != 0){
                accum = accum + m;
                
            }
            n = n/2;
            m = m * 2;
        }
        return accum;
    }
    
    public static void main(String[] args){
        int run1 = russianMultiply(5,20);
        int run2 = russianMultiply(17,4);
        //System.out.println(run1);
        //System.out.println(run2);

        final long startTime = System.currentTimeMillis();

        final long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("the time taken " + elapsedTime);
    }

}


