import java.util.Arrays;
/**
 * We are not allowed re-sort the array. We want a subarray
 */
class SolutionWrong {
    public int minSubArrayLen(int target, int[] nums) {
        Arrays.sort(nums);
        int total=0;
        int c=0;
        for(int i=nums.length-1;i>=0;i--){
            total+=nums[i];
            c++;
            System.out.print(""+nums[i]+" + ");
            if(total>=target){
                System.out.println(" = "+total);
                return c;
            }
        }
        System.out.println();
        return 0;
    }
}