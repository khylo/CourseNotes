package hr2dArrayHourglass;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;



public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<List<Integer>> arr = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            String[] arrRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> arrRowItems = new ArrayList<>();

            for (int j = 0; j < 6; j++) {
                int arrItem = Integer.parseInt(arrRowTempItems[j]);
                arrRowItems.add(arrItem);
            }

            arr.add(arrRowItems);
        }

        bufferedReader.close();
        System.out.println(getMaxHourGlass(arr));
    }

    public static int getMaxHourGlass( List<List<Integer>> arr){
        int maxSum=Integer.MIN_VALUE;
        int maxRows =6;
        int maxCols =6;
        int hourGlassRowSize=3;
        int hourGlassColSize=3;
        for(int i=0;i<=maxCols-hourGlassColSize;i++){
            for(int j=0;j<=maxRows-hourGlassRowSize;j++){
                // If different size hourglasses then loop through hourGlassColSize/ hourGlassRowSize
                List<Integer> row1=arr.get(i);
                List<Integer> row2=arr.get(i+1);
                List<Integer> row3=arr.get(i+2);

                int sum = row1.get(j)+row1.get(j+1)+row1.get(j+2); // row1 sum
                sum+= row2.get(j+1); // plus row2
                sum += row3.get(j)+row3.get(j+1)+row3.get(j+2); // row1 sum
                if(sum>maxSum)
                    maxSum = sum;
            }
        }
        return maxSum;
    }
}
