package atoi;
/**
to run 
javac *.java  && java Test
 */
class Test {
    public static void main(String[] args) {
        //Solution s= new Solution();
        SolutionInf s= new SolutionAtoI();
        String t = "42";
        int ans = call(s,t);
        assert ans ==42;

        ans = call(s,"   -42");
        assert ans ==-42;

        ans = call(s,"1337c0d3");
        assert ans ==1337;

        ans = call(s,"0-1");
        assert ans ==0;

        ans = call(s,"words and 987");
        assert ans == 0;

        ans = call(s, "-91283472332");
        assert ans ==   -2147483648;

        ans = call(s,"");
        assert ans == 0;

        ans = call(s,"-");
        assert ans == 0;

        ans = call(s,"  0000000000012345678");
        assert ans == 12345678;

        ans = call(s,"     +004500");
        assert ans == 4500;

        ans = call(s,"     -042");
        assert ans == 42;

        ans = call(s,"2147483646");
        assert ans == 2147483646;
        ;

    }

    public static int call(SolutionInf s, String num){
        var ans = s.myAtoi (num);
        System.out.println("atoi = "+num+ " = "+ans);
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