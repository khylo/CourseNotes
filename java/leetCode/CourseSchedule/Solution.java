import java.util.HashMap;
/**
 * 
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0. So it is possible.

2/
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.

Input: numCourses = 3, prerequisites = [[1,0],[0,1]]
 */
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        var preReqs = new HashMap();
        if(prerequisites.length>=numCourses)
            return false;
        for(int i=0;i<prerequisites.length;i++){
            if(prerequisites[i].length>numCourses)
                return false;
            preReqs.add(prerequisites[i][0], newMap(prerequisites[i]))
        }
        return true;
    }

    private List<In
}