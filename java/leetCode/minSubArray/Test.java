import java.util.Arrays;
import java.util.stream.Stream;

class Test {
    public static void main(String[] args) {
        Solution s= new Solution();
        int target = 7;
        int[] nums = {2,3,1,2,4,3};
        printArray("a",nums);
        int ans = s.minSubArrayLen(target, nums);
        System.out.println("ans (2) = "+ans);    

        target = 4;
        int[] b = {1,4,4};
        printArray("b",b);
        ans = s.minSubArrayLen(target, b);
        System.out.println("ans (1)"+ans);                  
    
        target = 11;
        int[] c = {1,1,1,1,1,1,1};
        printArray("c 11",c);
        ans = s.minSubArrayLen(target, c);
        System.out.println("ans (0) = "+ans);

        target=15;
        int[] d= {1,2,3,4,5};
        printArray("d 15",d);
        ans = s.minSubArrayLen(target, d);
        System.out.println("ans (5) = "+ans);

        target=213;
        int[] e = {12,28,83,4,25,26,25,2,25,25,25,12};
        printArray("e 213",e);
        ans = s.minSubArrayLen(target, e);
        System.out.println("ans (8) = "+ans);

        target=7;
        int[] f = {2,3,1,2,4,3};
        printArray("f "+target,f);
        ans = s.minSubArrayLen(target, f);
        System.out.println("ans (2) = "+ans);
    }

    public static void printArray(int[] a){
        System.out.print("[");
        Arrays.asList(a).stream().forEach(s -> System.out.print(s+","));
        System.out.println("]");
    } 
    public static void printArray(String label, int[] a){        
        System.out.print(label+" = [");
        Integer[] A = new Integer[a.length];
        for(int i=0;i<a.length;i++){
            A[i] = a[i];
        }
        //Arrays.asList(a).stream().forEach(s -> System.out.print(s+","));
        Stream.of(A).forEach(s -> System.out.print(s+","));
        System.out.println("]");
    } 
}