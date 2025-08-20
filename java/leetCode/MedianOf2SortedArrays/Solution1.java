package MedianOf2SortedArrays;
//import java.util.*;
/**
 * Runtime: 2 ms, faster than 99.89% of Java online submissions for Median of Two Sorted Arrays.
 * Memory Usage: 40.5 MB, less than 45.79% of Java online submissions for Median of Two Sorted Arrays.
 * 
 * In hindsight we don't need to store combinedArray at all.. See Solution
 */
class Solution1 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int num1Index=0;
        int num2Index=0;
        int totalLength = (nums1.length+nums2.length); 
        int halfWay=0;

        if(totalLength%2==0){ //even
            halfWay = 1+(totalLength/2);
        }else{
            halfWay = (1+totalLength)/2;
        }
        int[] combinedArray= new int[halfWay];
        int count = 0;
            
        while(count<halfWay){ // Loop to halfway point
            if( num1Index==nums1.length){
                combinedArray[count++] = nums2[num2Index++];
            }else if( num2Index==nums2.length){
                combinedArray[count++] = nums1[num1Index++];
            }else if(nums1[num1Index] <= nums2[num2Index] ){   
                combinedArray[count++] = nums1[num1Index++];
            }else {
                combinedArray[count++] = nums2[num2Index++];
            } 
            //printArray(combinedArray);
        }
        count--; // reverse last increment
        //System.out.println("Looped to half "+count);
        if(totalLength%2==0){ //even
            return getMidPoint(combinedArray[count], combinedArray[count-1]);
        }else{
            return (double)combinedArray[count];
        }
    }
    
    public double   getMidPoint(int a, int b){
        return (double)(((double)a+(double)b)/2);
    }

    // private void printArray(int[] a){
    //     Arrays.stream(a).forEach(System.out::println);
    // }
}
