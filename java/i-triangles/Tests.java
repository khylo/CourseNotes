import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Tests {
    public static void main(String[] args) throws Exception{
        Tests test = new Tests();
        test.run();
    }

    public void run() throws Exception{
        rTrim();
        ex1();
        ex2();
    }

    void rTrim(){
        String t = "Hi there    ";
        if("Hi there".equals(Tri.rTrim(t)))
            System.out.println("rTrim pass!!");
        else
            System.out.println("rTrim fail!! "+ Tri.rTrim(t));
    }

    void ex1() throws IOException{
        String test = "ex1.txt";
        File input=new File(test);
        int ans = new Tri(Files.readString(input.toPath())).solve();
        int expected = 1;
        System.out.println("Expected "+expected);
        System.out.println("Actual "+ans);
        if(ans==expected)
            System.out.println(test+" Success!!");
        else
            System.out.println(test+ " Fail!!");
    }

    void ex2() throws IOException{
        String test = "ex2.txt";
        File input=new File(test);
        int ans = new Tri(Files.readString(input.toPath())).solve();
        int expected = 12;
        System.out.println("Expected "+expected);
        System.out.println("Actual "+ans);
        if(ans==expected)
            System.out.println(test+" Success!!");
        else
            System.out.println(test+ " Fail!!");
    }
}
