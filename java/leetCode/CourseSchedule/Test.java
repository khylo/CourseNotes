import java.util.Arrays;

class Test {
    public static void main(String[] args) {
        Solution s= new Solution();
        int[][] a = {{1,0}};
        boolean ans = call(s,2,a);   
                   
    }

    public static boolean call(Solution s, int num, int[][] arr){
        boolean ans = s.canFinish(num,arr);
        System.out.println("numCourses = "+num+ ", "+arr2Str(arr)+" = "+ans);
        return ans;
    }

    public static String arr2Str(int[][] arr){
        StringBuilder sb = new StringBuilder();
        
        sb.append("[");
        for(int i=0;i<arr.length;i++){
            sb.append("[");
            for(int j=0;j<arr[i].length;j++){
                sb.append(arr[i][j]).append(",");
            }
            sb.append("], ");
        }
        
        return sb.append("]").toString();
        
    }
}