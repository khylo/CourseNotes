package twoSum;

import java.util.HashMap;

/**
 * See https://leetcode.com/problems/two-sum/submissions/895621290/
 * Not this solution beats my original (2 loops with n squared complexity)
 * This is just one loop.
 */
class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> m = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(m.containsKey(target-nums[i]))
                return new int[]{i, m.get(target-nums[i])};
            m.put(nums[i], i)    ;
        }
            
        return new int[]{};
        
    }

}