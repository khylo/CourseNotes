/**
Runtime: 2 ms, faster than 99.89% of Java online submissions for Median of Two Sorted Arrays.
Memory Usage: 39.7 MB, less than 99.96% of Java online submissions for Median of Two Sorted Arrays.
 */
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int num1Index=0;
        int num2Index=0;
        int totalLength = (nums1.length+nums2.length); 
        int halfWay=0;
        int halfWayValue=0;

        if(totalLength%2==0){ //even
            halfWay = totalLength/2;
        }else{
            halfWay = (1+totalLength)/2;
        }
        int count = 0;
            
        while(count<halfWay){ // Loop to halfway point
            if( num1Index==nums1.length){
                halfWayValue = nums2[num2Index++];
            }else if( num2Index==nums2.length){
                halfWayValue = nums1[num1Index++];
            }else if(nums1[num1Index] <= nums2[num2Index] ){  
                halfWayValue = nums1[num1Index++]; 
            }else {
                halfWayValue = nums2[num2Index++];
            } 
            count++;
        }
        if(totalLength%2==0){ //even .. Need to get next value and average
            int halfWayValue2 = 0;      
            if( num1Index==nums1.length){
                halfWayValue2 = nums2[num2Index]; // In theory don't need inc here now
            }else if( num2Index==nums2.length){
                halfWayValue2 = nums1[num1Index];
            }else if(nums1[num1Index] <= nums2[num2Index] ){  
                halfWayValue2 = nums1[num1Index]; 
            }else {
                halfWayValue2 = nums2[num2Index];
            } 
            return getMidPoint(halfWayValue, halfWayValue2);
        }else{
            return (double)halfWayValue;
        }
    }
    
    public double   getMidPoint(int a, int b){
        return (double)(((double)a+(double)b)/2);
    }
}