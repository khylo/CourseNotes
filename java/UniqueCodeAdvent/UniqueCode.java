import java.util.HashSet;
import java.util.Set;

public class UniqueCode{
    public static void main(String[] args) {
        long start= System.currentTimeMillis();
        UniqueCode app = new UniqueCode();
        expect(7,app.run("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
        expect(5,app.run("bvwbjplbgvbhsrlpgdmjqwftvncz"));
        expect(6,app.run("nppdvjthqldpwncqszvftbrmjlhg"));
        expect(10,app.run("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
        expect(11,app.run("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
        System.out.println("Time taken = "+(System.currentTimeMillis()-start)+"ms");
    }

    UniqueCode(){
    }

    public int run(String msg){
        for(int i=0;i<msg.length()-5;i++){
            String test = msg.substring(i,i+4); // Do we need this?
            Set<Character> testSet = new HashSet<>();
            for(int j=0;j<4;j++)
                testSet.add(test.charAt(j));
            if(testSet.size()==4)
                return i+4;
        }       
        return -1;
    }

    public static void expect(Integer exp, Integer actual){
        if(exp!=actual){
            System.out.println("FAIL "+actual+ " Not equal "+exp );
            return;
        }
        System.out.println("Pass "+actual+ " equals "+exp );
    }
}