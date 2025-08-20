package ProductOfArrayExceptSelf;
import java.util.Arrays;
import java.util.stream.Stream;

class Test {
    public static void main(String[] args) {
        Solution s= new Solution();
        int[] a = {1,2,3,4};
        printArray("a",a);
        int[] ans = s.productExceptSelf(a);
        printArray("ans",ans);    

        int[] b = {-1,1,0,-3,3};
        printArray("b",b);
        ans = s.productExceptSelf(b);
        printArray("ans",ans);                  
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