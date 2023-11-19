/**
 * Runtime 1ms (After deeting printlns) beats 99.94#5
 * Memory 53.57 MB beats 90.99%
 */
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int total=0;
        int c=0, start=0;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<nums.length;i++){
            total+=nums[i];
            c++;
            System.out.print(" + "+nums[i]);
            if(total>=target){
                System.out.println(" = "+total);
                if(c<=min)
                    min=c;
                // Delte from start to see if we can minimize
                for(int j=start;j<i ;j++){
                    if(total-nums[j]>=target){
                        c--;
                        start++;
                        total-=nums[j];
                        System.out.println(" - "+nums[j]+" = "+total);                    
                        if(c<min)
                            min=c;
                    }else
                        break;
                }
            }
        }
        System.out.println();
        if(min<Integer.MAX_VALUE)
            return min;
        return 0;
    }
}