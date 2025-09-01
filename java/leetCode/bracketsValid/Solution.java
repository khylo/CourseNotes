package bracketsValid;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 *
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Every close bracket has a corresponding open bracket of the same type.
 *
 *
 * Example 1:
 *
 * Input: s = "()"
 *
 * Output: true
 *
 * Example 2:
 *
 * Input: s = "()[]{}"
 *
 * Output: true
 *
 * Example 3:
 *
 * Input: s = "(]"
 *
 * Output: false
 *
 * Example 4:
 *
 * Input: s = "([])"
 *
 * Output: true
 *
 * Example 5:
 *
 * Input: s = "([)]"
 *
 * Output: false
 *
 *
 */
//import java.util.Stack;
public class Solution {

    public boolean isValid(String s) {
        ArrayList<Character> stack = new ArrayList<>(); // 0 = ( , 1 = { , 2 =[

        for(int i=0;i<s.length();i++){
            Character c = s.charAt(i);
            Character r = null;
            if(c=='(' || c=='{' || c=='['){
                stack.add(c);
                continue;
            }

            if(stack.size()==0)
                return false;
            r = stack.remove(stack.size()-1);
            if(r=='(') {
                if (c != ')')
                    return false;
            }else  if(r=='{') {
                if (c != '}')
                    return false;
            }else  if(r=='[') {
                if (c != ']')
                    return false;
            }
        }
        return stack.size()==0;
    }

    public boolean isValidif(String s) {
        Stack<Character> stack = new Stack<>(); // 0 = ( , 1 = { , 2 =[

        for(int i=0;i<s.length();i++){
            Character c = s.charAt(i);
            Character r = null;
            if(c=='(' || c=='{' || c=='['){
                    stack.push(c);
                    continue;
            }

            if(stack.size()==0)
                return false;
            r = stack.pop();
            if(r=='(') {
                if (c != ')')
                    return false;
            }else  if(r=='{') {
                if (c != '}')
                    return false;
            }else  if(r=='[') {
                if (c != ']')
                    return false;
            }
        }
        return stack.size()==0;
    }

    public boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>(); // 0 = ( , 1 = { , 2 =[

        for(int i=0;i<s.length();i++){
            Character c = s.charAt(i);
            Character r = null;
            switch(c) {
                case '(':
                case '{':
                case '[':
                    stack.push(c);
                    break;
                case ')':
                case '}':
                case ']':
                    if(stack.size()==0)
                        return false;
                    if(c==')')
                        r='(';
                    if(c=='}')
                        r='{';
                    if(c==']')
                        r='[';
                    if(stack.peek()!=r)
                        return false;
                    stack.pop();
                    break;
            }
        }
        return stack.size()==0;
    }

    private final boolean close(int[] count, int index){
        if(count[index]==0) return false; // Invalid closing before opening
        count[index]--;
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println("Expected true = "+s.isValid("()"));
        System.out.println("Expected true = "+s.isValid("()[]{}"));
        System.out.println("Expected false = "+s.isValid("(]"));
        System.out.println("Expected true = "+s.isValid("([])"));
        System.out.println("Expected false = "+s.isValid("([)]"));
        System.out.println("Expected false = "+s.isValid(")("));
        System.out.println("Expected false = "+s.isValid("({{{{}}}))"));
    }
}

/**
 * Doesn't work. .Misaligned opening and closing passes e.g. ({)}
 */
 class OldSolution {
    public boolean isValid(String s) {
        int[] count = new int[]{0, 0, 0}; // 0 = ( , 1 = { , 2 =[

        for (int i = 0; i < s.length(); i++) {
            switch (s.substring(i, i + 1)) {
                case "(":
                    count[0]++;
                    break;
                case ")":
                    if (!close(count, 0)) {
                        return false;
                    }
                    break;
                case "{":
                    count[1]++;
                    break;
                case "}":
                    if (!close(count, 1)) {
                        return false;
                    }
                    break;
                case "[":
                    count[2]++;
                    break;
                case "]":
                    if (!close(count, 2)) {
                        return false;
                    }
                    break;
            }
        }
        return count[0] == 0 && count[1] == 0 && count[2] == 0;
    }

    private final boolean close(int[] count, int index) {
        if (count[index] == 0) return false; // Invalid closing before opening
        count[index]--;
        return true;
    }
}
