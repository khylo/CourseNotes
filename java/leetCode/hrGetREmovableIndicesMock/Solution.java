package hrGetREmovableIndicesMock;

import java.io.*;
import java.util.*;

class Result {

    /*
     * Complete the 'getRemovableIndices' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. STRING str1
     *  2. STRING str2
     */

    public static List<Integer> getRemovableIndices(String str1, String str2) {
        List<Integer> ret = null;
        char lastChar=str2.charAt(0);
        int lastCharIdx=0;
        int offset = 0;
        for(int i =0;i<str2.length(); i++){
            char c1 = str1.charAt(i+offset);
            char c2 = str2.charAt(i);
            if(c1!=c2){
                // Have a diff
                if(offset>0)
                    return List.of(-1);
                offset = 1;
                if(c1==lastChar)
                    ret = getAns(i, lastCharIdx);
                else
                    ret = getAns(i,i);
            }
            if(c2!=lastChar){
                lastChar = c2;
                lastCharIdx = i;
            }
        }
        if(offset==0){
            // No diff found , check if last char in str1 is diff to lastChar
            char c1 = str1.charAt(str2.length());
            if(c1==lastChar){
                return getAns(str2.length(), lastCharIdx);
            }
            return getAns(str2.length(),str2.length());
        }
        if(ret!=null)
            return ret;
        return List.of(-1);
    }

    private static List<Integer> getAns(int i, int lastCharIdx){
        List<Integer> ret = new ArrayList<>(1+i-lastCharIdx);
        for(int j=lastCharIdx;j<=i;j++){
            ret.add(j);
        }
        return ret;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        String str1,str2;
        str1="abcde";
        str2="abcd";
        System.out.println(String.format("Result of %s / %s = %s",str1,str2,l2s(Result.getRemovableIndices(str1, str2)) ));
        str1="aaaa";
        str2="aaa";
        System.out.println(String.format("Result of %s / %s = %s",str1,str2,l2s(Result.getRemovableIndices(str1, str2)) ));
        str1="aaaaaaaabaaaaaaaaaaa";
        str2="aaaaaaaaaaaaaaaaaaa";
        System.out.println(String.format("Result of %s / %s = %s",str1,str2,l2s(Result.getRemovableIndices(str1, str2)) ));
        str1="abcde";
        str2="abed";
        System.out.println(String.format("Result of %s / %s = %s",str1,str2,l2s(Result.getRemovableIndices(str1, str2)) ));
        str1="aabcd";
        str2="abcd";
        System.out.println(String.format("Result of %s / %s = %s",str1,str2,l2s(Result.getRemovableIndices(str1, str2)) ));


    }

    public static String l2s(List<Integer> l){
        StringBuilder sb = new StringBuilder();
        for(int i:l)
            sb.append(i+",");
        return sb.toString();
    }
    public static void main2(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String str1 = bufferedReader.readLine();

        String str2 = bufferedReader.readLine();

        List<Integer> result = Result.getRemovableIndices(str1, str2);

        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));

            if (i != result.size() - 1) {
                System.out.print("\n");
            }
        }

        System.out.println();

        bufferedReader.close();
    }
}
