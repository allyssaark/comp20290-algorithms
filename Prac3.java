public class Prac3{
    public static int fibo(int num){
        if (num <= 1){
            return num;
        }
        else{
            return fibo(num-2) + fibo(num-1);
        }
    }

    public static int fibonacciIterative(int n){
        if (n<=1)
            return 1; 
      
        int fib = 1;
        int prevFib =  1;
      
        for (int i = 2; i < n; i++) {
            int temp = fib;
            fib = fib + prevFib;
            prevFib = temp;
        }
        return fib;
       }

       public static String[] hanoi(int disk, int source, int dest, int aux){
           if (disk == 1){
               System.out.println("move disk " + disk + " from tower " + source + " to tower " + dest);
               return null;
           }
            hanoi(disk-1, source, aux, dest);
            System.out.println("move disk "+ disk + " from tower "+ source + " to tower " + dest);
            return hanoi(disk-1, aux, dest, source);

       }
      

    public static void main(String[]args){
        System.out.println("hello world");
        
        int n = 7;
        //System.out.println("recursive: " + fibo(n));
        //System.out.println("iterative: "+ fibonacciIterative(n)); 

        int nu = 9; 
        //System.out.println("recursive: " + fibo(nu));       
        //System.out.println("iterative: "+ fibonacciIterative(nu)); 

        int num = 100; 
        //System.out.println("recursive: " + fibo(num));       
        //System.out.println("iterative: "+ fibonacciIterative(num)); 


        System.out.println(hanoi(2,1,3,2));
    }
    
}