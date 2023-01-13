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

e.g. 
5 courses
5 depends on 4
4 depends on 3
3 depends on 2
2 depends on 1
3 depends on 1
4 depends on 1

 */
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        var preReqs = new HashMap();
        if(prerequisites.length>=numCourses)
            return false;
        for(int i=0;i<prerequisites.length;i++){
            if(prerequisites[i].length>numCourses) //  NOt sure if data is presented this way e.g. 5,4,3,2,1. or in tuples 
                return false;           
            if(preReqs.containsKey(prerequisites[i][1])){ // Check if dependee has dependency in Map

            }
            // Add for future circular check
            preReqs.add(prerequisites[i][0], newMap(prerequisites[i]))
        }
        return true;
    }
}