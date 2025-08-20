package ProductOfArrayExceptSelf;
import com.sun.net.httpserver.Authenticator.Result;

/** 
 * Example 1:

Input: nums = [1,2,3,4]
Output: [24,12,8,6]
Example 2:

Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]
 
Naive implementation is ineer loop working out product each time where i != j . But this is O n^2

So trick to to realizse we can work out product for entire line and then divide it by item at position i for Result.class

But 0's are the problem causing divide by zero.

SO this is the solutoin. Making notes of zeros and tweeking calc based on that

Not final optimization is to store results in input array for less memory as after calculating product we don't need it anymore
 */
public class Solution {
    public int[] productExceptSelf(int[] nums) {     
        int total = 1;
        int zeros = 0, zeroLoc=-1;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=0)
                total=total * nums[i];
            else{
                zeros++;
                zeroLoc = i;
            }
        }
        for(int i=0;i<nums.length;i++){
            if(zeros>0){
                if(zeros==1){
                    if(i!=zeroLoc){
                        nums[i]=0;
                    }else
                        nums[i]=total;
                }else{
                    nums[i]=0;
                }
                continue;
            }else{
                if(nums[i]!=0)
                    nums[i]=total/nums[i];
            }
        }
        return nums;
    }
}
