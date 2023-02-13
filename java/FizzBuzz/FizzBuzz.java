import java.util.stream.IntStream;
public class FizzBuzz{
    static int iterations=1;
    static int max = 59999999;
    public static void main(String[] args) {
        FizzBuzz app = new FizzBuzz(max);
        Runnable timeit = () -> {
            app.run()   ;
        };

        Runnable timeit2 = () -> {
            app.run2()   ;
        };

        time("FizzBuzz "+iterations+" times took ",timeit);
        System.gc();
        time("FizzBuzz "+iterations+" times took ",timeit2);
    }

    int range;

    FizzBuzz(int range){
        this.range=range;
    }

    /*
     * Initial implementation. Fails with large list 59999999 < max < 99999999
     * 5999999 takes 2962ms / 2618 using 338510920/228527864 bytes
     */
    public String run(){
        StringBuilder fullResp = new StringBuilder("");
        for(int i=0;i<range;i++){
            StringBuilder ans = new StringBuilder("");
            boolean fb=false;
            if(i%3==0){
                fb=true;
                ans.append("fizz");
            }
            if(i%5==0){
                fb=true;
                ans.append("buzz");
            }
            if(fb)
                fullResp.append(ans.toString());
            else
            fullResp.append(i);

            fullResp.append(" ");
        }
        return fullResp.toString();
    }

    public String run2(){
        StringBuilder ans = new StringBuilder("");
        IntStream.rangeClosed(1, range)
            .mapToObj(i -> i % 3 == 0 ? (i % 5 == 0 ? "FizzBuzz" : "Fizz") : (i % 5 == 0 ? "Buzz" : i))
            .forEach(i->ans.append(i+" "));
        return ans.toString();
    }

    public static void time(String msg, Runnable fn){
        long start = System.currentTimeMillis();
        long startFM = Runtime.getRuntime().freeMemory();
        for(int i=0;i<iterations;i++){
            fn.run();
        }
        long len = System.currentTimeMillis() - start;
        long changeFM = Runtime.getRuntime().freeMemory() - startFM;
        System.out.println(msg + " took "+len+" ms, using "+changeFM+" bytes");
    }


}