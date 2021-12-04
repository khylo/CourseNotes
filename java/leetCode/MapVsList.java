import java.util.Arrays;
import java.util.*;
import java.util.function.Consumer;
/**
to run 
javac *.java  && java MapVsList

Insert 1000000 to HashMap took 538 ms
Insert 1000000 to ArrayList took 56 ms
Insert 1000000 to TreeMap took 977 ms
Contains? 1000000 to HashMap took 171 ms
ArrayList off the charts slow
Contains? 1000000 to TreeMap took 813 ms


 */
class MapVsList {
    public static final int max = 1000000;
    public static void main(String[] args) {
        
        var rand = new Random();
        var map = new HashMap<Double,Double>();
        var list = new ArrayList<Double>();
        var tree = new TreeSet<Double>();
        Runnable  mapBlk = () -> {
            double key = rand.nextDouble();
            map.put(key,key);
        };
        Runnable listBlk = () ->  {
            double key = rand.nextDouble();
            list.add(key);
        };
        Runnable treeBlk = () -> {
            double key = rand.nextDouble();
            tree.add(key);
        };

        time("Insert "+max+" to HashMap",mapBlk);
        time("Insert "+max+" to ArrayList",listBlk);
        time("Insert "+max+" to TreeMap",treeBlk);

        Runnable  readMapBlk = () -> {
            double key = rand.nextDouble();
            map.containsKey(key);
        };
        Runnable readListBlk = () ->  {
            double key = rand.nextDouble();
            //list.stream().filter(a -> Objects.equals(a.value3, key)).findFirst();
            list.contains(key);
        };
        Runnable readTreeBlk = () -> {
            double key = rand.nextDouble();
            tree.contains(key);
        };

        time("Contains? "+max+" to HashMap",readMapBlk);
        time("Contains? "+max+" to TreeMap",readTreeBlk);
        time("Contains? "+max+" to ArrayList",readListBlk);

        

    }

    public static void time(String msg, Runnable fn){
        long start = System.currentTimeMillis();
        for(int i=0;i<max;i++){
            fn.run();
        }
        long len = System.currentTimeMillis() - start;
        System.out.println(msg + " took "+len+" ms");
    }
}