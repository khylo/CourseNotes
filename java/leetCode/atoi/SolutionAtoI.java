package atoi;
import java.math.MathContext;
/**
 *
 Whitespace: Ignore any leading whitespace (" ").
 Signedness: Determine the sign by checking if the next character is '-' or '+', assuming positivity if neither present.
 Conversion: Read the integer by skipping leading zeros until a non-digit character is encountered or the end of the string is reached. If no digits were read, then the result is 0.
 Rounding: If the integer is out of the 32-bit signed integer range [-231, 231 - 1], then round the integer to remain in the range. Specifically, integers less than -231 should be rounded to -231, and integers greater than 231 - 1 should be rounded to 231 - 1.
 Return the integer as the final result.



 */
class SolutionAtoI implements SolutionInf{
    public int myAtoi(String s) {
        int c = 0;
        // Signedness: Determine the sign by checking if the next character is '-' or '+', assuming positivity if neither present.
        boolean positive = true;
        int ans = 0;
        // Trim leading whitespace
        while(c<s.length() && s.charAt(c)==' ') {
            c++;
        }
        // Check sign
        if(c<s.length() && s.charAt(c)=='-') {
            c++;
            positive = false;
        }else if(c<s.length() && s.charAt(c)=='+') {
            c++;
        }
        // Skip zeros
        while(c<s.length() && s.charAt(c)=='0') {
            c++;
        }
        // parse numbers
        while(c<s.length()) {
            if (s.charAt(c) - '0' < 0 || s.charAt(c) - '0' > 9) { // NaN
                return ans;
            } else {
                // Number
                int add = s.charAt(c) - '0';
                // Check for overflow
                /*if (positive && ((Integer.MAX_VALUE / 10) - add) < ans) {
                    return getAns(Integer.MAX_VALUE, positive);
                }
                if (!positive && (-(Integer.MIN_VALUE / 10) - add) < ans) {
                    return getAns(Integer.MIN_VALUE, positive);
                }*/
                try {
                    ans = inc(ans, add, positive);
                } catch (Exception e){
                    return positive?Integer.MAX_VALUE:Integer.MIN_VALUE;
                }
                c++;
            }
        }
        return ans;
    }

    private int inc(int ans, int add, boolean positive){
        return positive? Math.addExact(Math.multiplyExact(ans, 10) , add) : Math.addExact(Math.multiplyExact(ans, 10), -add);
    }

}